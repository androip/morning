package morning.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.NodeInstance;
import morning.entity.ProcessInstance;
import morning.event.EVENT_TYPE;
import morning.event.Event;
import morning.repo.NodeInstanceDao;
import morning.repo.ProcessInstanceDao;
import morning.service.event.ProcessEventSupport;
import morning.service.event.ProcessStartEvent;
import morning.service.event.exception.EventException;
import morning.service.exception.MorningException;
import morning.service.util.TimeUtil;
import morning.vo.STATUS;

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
	private ProcessEventSupport eventSupport;
	
	public String startProcess(String processTemplateId,String userId) {
		//调用Meta api 查询流程模版
		ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(processTemplateId);
		//实例化一个流程实例 
		ProcessInstance procIns = createProcessIns(processTmpDto.getProcessTemplateId(),
				processTmpDto.getProcessName(),
				TimeUtil.getSystemTime(),
				userId);
		//创建Start节点，并初始化状态为Running
		NodeInstance startNodeIns = procIns.createNodeInstance(processTmpDto.startNodeTmpId(),
				processTmpDto.getNodeType(processTmpDto.startNodeTmpId()),STATUS.RUNNING.getValue());
		//持久化流程实例和START节点实例 
		processInstanceDao.save(procIns);
		nodeInstanceDao.save(startNodeIns);
		
		// 创建事件
		Event event = new ProcessStartEvent(TimeUtil.getSystemTime(),userId);
		eventSupport.initEvent(event,processTmpDto.getProcessTemplateId(),
				procIns.getProcessInsId(),
				startNodeIns.getNodeInsId(),
				startNodeIns.getNodeTemplateId(),
				EVENT_TYPE.proc_start
				);
		try {
			eventSupport.dispatchEvent(event);
		} catch (EventException e) {
			e.printStackTrace();
		} catch (MorningException e) {
			e.printStackTrace();
		}
		//TODO 发送事件（需持久化）->待办事项
		return procIns.getProcessInsId();
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

	
	
}
