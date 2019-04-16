package morning.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.ProcessInstance;
import morning.service.util.TimeUtil;

@Service
public class EngineService {

	private IProcessMetaService processMetaService;
	
	public String startProcess(String processTemplateId,String userId) {
		//调用Meta api 查询流程模版
		ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(processTemplateId);
		//实例化一个流程实例 
		ProcessInstance procIns = createProcessIns(processTmpDto.getProcessTemplateId(),
				processTmpDto.getProcessName(),
				TimeUtil.getSystemTime(),
				userId);
		// TODO 初始化流程实例的状态为Ready
		// TODO 初始化Start节点状态为Running
		return "XXid";
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
		return procIns;
	}

	
	
}
