package morning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import morning.dto.FormInstancDto;
import morning.vo.FormFieldInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.edge.EdgeDto;
import morining.dto.proc.node.NodeTemplateDto;
import morining.vo.NODE_TYPE;
import morning.dto.NodeInstanceDto;
import morning.dto.TaskOverviewDto;
import morning.entity.TaskOverview;
import morning.entity.process.EdgeIns;
import morning.entity.process.FormInstance;
import morning.entity.process.NodeInstance;
import morning.entity.process.ProcessInstance;
import morning.event.EVENT_TYPE;
import morning.event.Event;
import morning.exception.DBException;
import morning.repo.EdgeInstanceDao;
import morning.repo.FormInstanceDao;
import morning.repo.NodeInstanceDao;
import morning.repo.ProcessInstanceDao;
import morning.repo.TaskOverviewDao;
import morning.service.event.ProcessEventSupport;
import morning.service.event.ProcessStartEvent;
import morning.service.event.TaskSubmitEvent;
import morning.service.event.exception.EventException;
import morning.service.event.listener.ProcessStartListener;
import morning.service.event.listener.TaskSubmitListener;
import morning.service.exception.MorningException;
import morning.service.util.TimeUtil;
import morning.util.IdGenerator;
import morning.vo.PROC_STATUS;
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
	private ProcessStartListener processStartListener;
	@Autowired
	private TaskSubmitListener taskSubmitListener;
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	@Autowired
	private FormInstanceDao formInstanceDao;



	@Transactional(rollbackFor=DBException.class)
	public String startProcess(String processTemplateId,String userId) throws DBException {
		//调用Meta api 查询流程模版
		ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(processTemplateId);
		
		//实例化一个流程实例 
		ProcessInstance procIns = createProcessIns(processTmpDto.getProcessTemplateId(),
				processTmpDto.getProcessName(),
				TimeUtil.getSystemTime(),
				userId);
		//注册监听器
		eventSupport.registerListener(processStartListener,EVENT_TYPE.proc_start);
		//创建Start节点，并初始化状态为Running
		NodeTemplateDto startNodeTmpDto = processTmpDto.extractStartNodeTmp();
		NodeInstance startNodeIns = procIns.createNodeInstance(startNodeTmpDto.getNodeTemplateId(),
																processTmpDto.getNodeType(startNodeTmpDto.getNodeTemplateId()),
																TASK_STATUS.Runing.getCode(),
																startNodeTmpDto.getNodeTemplateName(),
																userId);
		//持久化流程实例和START节点实例 
		processInstanceDao.save(procIns);
		nodeInstanceDao.save(startNodeIns);
		// 创建有向弧实例并持久化
		List<EdgeIns> edgeList = createDirectedArcByNodeIns(processTmpDto,startNodeIns);
		try {
			edgeInstanceDao.save(edgeList);
		} catch (DBException e1) {
			e1.printStackTrace();
		}
		List<String> toNodeTids = new ArrayList<String>();
		List<String> toNodeInsIds = new ArrayList<String>();
		// 从有向弧取出所有的目标节点模版ID和节点实例ID
		extractToNodeIDs(edgeList,toNodeTids,toNodeInsIds);
		//事件通知
		noticeByEvent(new ProcessStartEvent("Start process",
				toNodeTids,
				toNodeInsIds,EVENT_TYPE.proc_start),
				processTmpDto.getProcessTemplateId(),
				procIns.getProcessInsId(),
				startNodeIns.getNodeInsId(),
				startNodeIns.getNodeTemplateId(),
				procIns.getProcessName(),
				startNodeIns.getNodeName(),
				userId);
		return procIns.getProcessInsId();
	}

	/**
	 * 提取有向弧的目标模板节点和实例节点ID
	 * @param edgeList
	 * @param toNodeTids
	 * @param toNodeInsIds
	 */
	private void extractToNodeIDs(List<EdgeIns> edgeList, List<String> toNodeTids, List<String> toNodeInsIds) {
		for(EdgeIns edge:edgeList) {
			toNodeTids.add(edge.getToNodeTId());
			toNodeInsIds.add(edge.getFromNodeInsId());
		}
		
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
		procIns.setProcessInsId(IdGenerator.generatProcessInsId());
		procIns.setCreateTime(systemTime);
		procIns.setProcessTemplateId(processTemplateId);
		procIns.setProcessName(processName);
		procIns.setCreateUserId(userId);
		procIns.setUpdateTime(systemTime);
		procIns.setStatus(PROC_STATUS.READY.getValue());
		return procIns;
	}

	public List<TaskOverviewDto> getTaskOverviewList(String userId) {
		List<TaskOverview> tasks = taskOverviewDao.queryByUserId(userId);
		List<TaskOverviewDto> dtolist = new ArrayList<TaskOverviewDto>();
		for(TaskOverview view : tasks)	{
			TaskOverviewDto dto = new TaskOverviewDto(view.getTaskName(),
					view.getCreateTime(),
					view.getTaskStatus(),
					view.getProcessNodeInsId(),
					view.getNodeTId(),
					view.getProcessInsId(),
					view.getProcesssTId()
					);
			dtolist.add(dto);
		}
		return dtolist;
	}

	/**
	 * 用户提交表单：节点状态为{@code TASK_STATUS.Ready}创建保存节点实例，表单实例，变更状态{@code TASK_STATUS.End}；
	 * 节点状态为{@code TASK_STATUS.Running}获取实例，更新保存实例，变更状态{@code TASK_STATUS.End}
	 * @param nodeInstanceDto
	 * @param userId
	 */
	@Transactional(rollbackFor=DBException.class)
	public void submitForm(NodeInstanceDto nodeInstanceDto, String userId) throws DBException {
		ProcessInstance procIns = processInstanceDao.getById(nodeInstanceDto.getProcessInsId());
		logger.info("提交表单，状态：{}",nodeInstanceDto.getNodeStatus());
		if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Ready.getCode()) {
			//说明节点实例尚未创建，创建节点实例
			NodeInstance nodeIns = procIns.createNodeInstance(nodeInstanceDto.getNodeTemplateId(), 
					NODE_TYPE.Task.toString(), 
					TASK_STATUS.End.getCode(), 
					nodeInstanceDto.getNodeName(),
					userId);
			//创建并保存表单实例
			List<FormInstance> formInsList = nodeIns.createFormInstance(nodeInstanceDto.getFormInsDtoList());
			//获取并保存字段值对象
			nodeInstanceDto.getFormInsDtoList().forEach(formDto->{
				formDto.setFormFieldInstance();
			});
			//创建有向弧
			ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(procIns.getProcessTemplateId());
			List<EdgeIns> edgeInsList = createDirectedArcByNodeIns(processTmpDto, nodeIns);
			List<String> toNodeTids = new ArrayList<String>();
			List<String> toNodeInsIds = new ArrayList<String>();
			// 从有向弧取出所有的目标节点模版ID和节点实例ID
			extractToNodeIDs(edgeInsList,toNodeTids,toNodeInsIds);
			{
				nodeInstanceDao.save(nodeIns);
				formInstanceDao.saveAll(formInsList);
				formInstanceDao.saveField(nodeInstanceDto.getFormInsDtoList());
				edgeInstanceDao.save(edgeInsList);
			}
			//当一个的聚合持久化之后发送事件通知下一个任务节点
			eventSupport.registerListener(taskSubmitListener, EVENT_TYPE.node_end);
			TaskSubmitEvent event = new TaskSubmitEvent("",toNodeTids,toNodeInsIds,EVENT_TYPE.node_end);
			noticeByEvent(event,
					processTmpDto.getProcessTemplateId(),
					procIns.getProcessInsId(),
					nodeIns.getNodeInsId(),
					nodeIns.getNodeTemplateId(),
					procIns.getProcessName(),
					nodeIns.getNodeName(),
					userId);
		}else if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Runing.getCode()){
			//取节点实例和表单字段
			NodeInstance nodeIns = nodeInstanceDao.getById(nodeInstanceDto.getNodeInsId());
			nodeIns.setNodeStatus(TASK_STATUS.End.getCode());
			//创建有向弧
			ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(procIns.getProcessTemplateId());
			List<EdgeIns> edgeInsList = createDirectedArcByNodeIns(processTmpDto, nodeIns);
			List<String> toNodeTids = new ArrayList<String>();
			List<String> toNodeInsIds = new ArrayList<String>();
			// 从有向弧取出所有的目标节点模版ID和节点实例ID
			extractToNodeIDs(edgeInsList,toNodeTids,toNodeInsIds);
			{	
				nodeInstanceDao.updateNodeStatus(nodeIns);
				formInstanceDao.updateFormFieldVal(nodeInstanceDto.getFormInsDtoList());
				edgeInstanceDao.save(edgeInsList);
			}
			// 当一个的聚合持久化之后发送事件通知下一个任务节点
			eventSupport.registerListener(taskSubmitListener, EVENT_TYPE.node_end);
			TaskSubmitEvent event = new TaskSubmitEvent("",toNodeTids,toNodeInsIds,EVENT_TYPE.node_end);
			noticeByEvent(event,
					processTmpDto.getProcessTemplateId(),
					procIns.getProcessInsId(),
					nodeIns.getNodeInsId(),
					nodeIns.getNodeTemplateId(),
					procIns.getProcessName(),
					nodeIns.getNodeName(),
					userId);
		}
		
	}

	private void noticeByEvent(Event event, 
			String processTemplateId, String processInsId, String nodeInsId, String nodeTemplateId, String processName,
			String nodeName, String userId) {
		try {
			logger.debug("init event{}",event.getClass());
			eventSupport.initEvent(event,
					processTemplateId,
					processInsId,
					nodeInsId,
					nodeTemplateId,
					event.getEventType(),
					userId,
					TimeUtil.getSystemTime(),
					processName,
					nodeName
			).dispatchEvent(event,event.getEventType());
		} catch (EventException | MorningException e) {
			e.printStackTrace();
		};
		
	}


	/**
	 * 用户提交表单：节点状态为{@code TASK_STATUS.Ready}创建保存节点实例/表单实例，变更状态{@code TASK_STATUS.Running}；
	 * 节点状态为{@code TASK_STATUS.Running}获取实例，更新保存实例
	 * @param nodeInstanceDto
	 * @param userId
	 * @throws DBException 
	 */
	@Transactional(rollbackFor=DBException.class)
	public void saveForm(NodeInstanceDto nodeInstanceDto, String userId) throws DBException {
		ProcessInstance procIns = processInstanceDao.getById(nodeInstanceDto.getProcessInsId());
		if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Ready.getCode()) {
			//说明节点实例尚未创建，创建节点实例
			NodeInstance nodeIns = procIns.createNodeInstance(nodeInstanceDto.getNodeTemplateId(), 
					NODE_TYPE.Task.toString(), 
					TASK_STATUS.Runing.getCode(), 
					nodeInstanceDto.getNodeName(),
					userId);
			//创建并保存表单实例
			List<FormInstance> formInsList = nodeIns.createFormInstance(nodeInstanceDto.getFormInsDtoList());
			//获取并保存字段值对象
			nodeInstanceDto.getFormInsDtoList().forEach(formDto->{
				formDto.setFormFieldInstance();
			});
			//创建有向弧
			ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(procIns.getProcessTemplateId());
			List<EdgeIns> edgeInsList = createDirectedArcByNodeIns(processTmpDto, nodeIns);
			List<String> toNodeTids = new ArrayList<String>();
			List<String> toNodeInsIds = new ArrayList<String>();
			// 从有向弧取出所有的目标节点模版ID和节点实例ID
			extractToNodeIDs(edgeInsList,toNodeTids,toNodeInsIds);
			{
				nodeInstanceDao.save(nodeIns);
				formInstanceDao.saveAll(formInsList);
				formInstanceDao.saveField(nodeInstanceDto.getFormInsDtoList());
				edgeInstanceDao.save(edgeInsList);
			}
		}else if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Runing.getCode()){
			formInstanceDao.updateFormFieldVal(nodeInstanceDto.getFormInsDtoList());
		}
	}

	public Map<String,List<FormFieldInstance>> getNodeIns(String nodeInsId) throws DBException {
		List<FormInstance> formInsList = formInstanceDao.getFormInsByNodeInsId(nodeInsId);
		Map<String,List<FormFieldInstance>>  formFieldMap = formInstanceDao.getFormFieldInstance(formInsList);
		return formFieldMap;
	}

	
	
}











