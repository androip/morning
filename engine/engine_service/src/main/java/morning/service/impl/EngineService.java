package morning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.edge.EdgeDto;
import morining.dto.proc.node.NodeTemplateDto;
import morning.dto.TaskOverviewDto;
import morning.entity.TaskOverview;
import morning.entity.process.EdgeIns;
import morning.entity.process.NodeInstance;
import morning.entity.process.ProcessInstance;
import morning.event.EVENT_TYPE;
import morning.event.Event;
import morning.repo.EdgeInstanceDao;
import morning.repo.NodeInstanceDao;
import morning.repo.ProcessInstanceDao;
import morning.repo.TaskOverviewDao;
import morning.service.event.ProcessEventSupport;
import morning.service.event.ProcessStartEvent;
import morning.service.event.exception.EventException;
import morning.service.event.listener.CreateNodeInstanceListener;
import morning.service.exception.MorningException;
import morning.service.util.TimeUtil;
import morning.vo.STATUS;
import morning.vo.TASK_STATUS;

@Service
public class EngineService {
	
	private static Logger logger = LoggerFactory.getLogger(EngineService.class);

	@Autowired
	private IProcessMetaService processMetaService;
	@Autowired
	private ProcessInstanceDao processInstanceDao;
	@Autowired
	private NodeInstanceDao nodeInstanceDao;
	@Autowired
	private EdgeInstanceDao edgeInstanceDao;
	@Autowired
	private ProcessEventSupport eventSupport;
	@Autowired
	private CreateNodeInstanceListener createNodeInstanceListener;
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	
	public String startProcess(String processTemplateId,String userId) {
		//调用Meta api 查询流程模版
		ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(processTemplateId);
		
		//实例化一个流程实例 
		ProcessInstance procIns = createProcessIns(processTmpDto.getProcessTemplateId(),
				processTmpDto.getProcessName(),
				TimeUtil.getSystemTime(),
				userId);
		//注册监听器
		eventSupport.registerListener(createNodeInstanceListener);
		//创建Start节点，并初始化状态为Running
		NodeTemplateDto nodeTmpDto = processTmpDto.extractStartNodeTmp();
		NodeInstance startNodeIns = procIns.createNodeInstance(nodeTmpDto.getNodeTemplateId(),
				processTmpDto.getNodeType(nodeTmpDto.getNodeTemplateId()),STATUS.RUNNING.getValue(),nodeTmpDto.getNodeTemplateName());
		//持久化流程实例和START节点实例 
		processInstanceDao.save(procIns);
		nodeInstanceDao.save(startNodeIns);
		// 创建有向弧实例并持久化
		List<EdgeIns> edgeList = createDirectedArcByNodeIns(processTmpDto,startNodeIns);
		edgeInstanceDao.save(edgeList);
		// 创建事件
		Event event = new ProcessStartEvent("Start process");
		eventSupport.initEvent(event,processTmpDto.getProcessTemplateId(),
				procIns.getProcessInsId(),
				startNodeIns.getNodeInsId(),
				startNodeIns.getNodeTemplateId(),
				EVENT_TYPE.proc_start,
				userId,
				TimeUtil.getSystemTime(),
				procIns.getProcessName(),
				startNodeIns.getNodeName()
				);
		try {
			// 发送事件（需持久化）->待办事项
			eventSupport.dispatchEvent(event);
		} catch (EventException e) {
			e.printStackTrace();
		} catch (MorningException e) {
			e.printStackTrace();
		}
		
		return procIns.getProcessInsId();
	}

	/**
	 * 根据节点实例，创建其对应的出度实例
	 * @param processTmpDto
	 * @param nodeIns
	 * @return
	 */
	private List<EdgeIns> createDirectedArcByNodeIns(ProcessTemplateDTO processTmpDto, NodeInstance nodeIns) {
		String nodeTid = nodeIns.getNodeTemplateId();
		List<EdgeDto> edgeDtolist = processTmpDto.getEdgeDtoByFrom(nodeTid);
		List<EdgeIns> inslist = new ArrayList<EdgeIns>();
		for(EdgeDto dto:edgeDtolist) {
			EdgeIns ins = new EdgeIns();
			ins.setFromNodeInsId(nodeIns.getNodeInsId());
			ins.setProcessInsId(nodeIns.getProcessInsId());
			ins.setProcessTId(processTmpDto.getProcessTemplateId());
			ins.setFromNodeTId(nodeTid);
			ins.setToNodeTId(dto.getTo());
			inslist.add(ins);
		}
		return inslist;
	}


	private ProcessInstance createProcessIns(String processTemplateId, String processName, String systemTime,
			String userId) {
		ProcessInstance procIns = new ProcessInstance();
		procIns.setProcessInsId(UUID.randomUUID().toString());
		procIns.setCreateTime(systemTime);
		procIns.setProcessTemplateId(processTemplateId);
		procIns.setProcessName(processName);
		procIns.setCreateUserId(userId);
		procIns.setUpdateTime(systemTime);
		procIns.setStatus(STATUS.READY.getValue());
		return procIns;
	}

	public List<TaskOverviewDto> getTaskOverviewList(String userId) {
		List<TaskOverview> tasks = taskOverviewDao.queryByUserId(userId);
		List<TaskOverviewDto> dtolist = new ArrayList<TaskOverviewDto>();
		for(TaskOverview view : tasks)	{
			TaskOverviewDto dto = new TaskOverviewDto(view.getTaskName(),
					view.getCreateTime(),
					TASK_STATUS.getBycode(view.getTaskStatus()).toString());
			dtolist.add(dto);
		}
		return dtolist;
	}

	
	
}
