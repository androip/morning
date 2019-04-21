package morning.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.NodeInstance;
import morning.entity.ProcessInstance;
import morning.repo.NodeInstanceDao;
import morning.repo.ProcessInstanceDao;
import morning.service.util.TimeUtil;
import morning.vo.STATUS;

@Service
public class EngineService {

	@Autowired
	private IProcessMetaService processMetaService;
	@Autowired
	private ProcessInstanceDao processInstanceDao;
	@Autowired
	private NodeInstanceDao nodeInstanceDao;
	
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
