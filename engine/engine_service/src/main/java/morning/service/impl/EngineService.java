package morning.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.edge.EdgeDto;
import morining.dto.proc.node.ConditionDto;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.proc.node.form.FormPropertyDto;
import morining.dto.rule.FieldTransformRuleDto;
import morining.dto.rule.FormTransformRuleDto;
import morining.event.EVENT_TYPE;
import morining.event.Event;
import morining.event.ProcessEventSupport;
import morining.exception.EventException;
import morining.exception.MorningException;
import morining.vo.NODE_TYPE;
import morning.dto.NodeInstanceDto;
import morning.dto.TaskOverviewDto;
import morning.entity.TaskOverview;
import morning.entity.process.EdgeIns;
import morning.entity.process.FormInstance;
import morning.entity.process.NodeInstance;
import morning.entity.process.ProcessInstance;
import morning.exception.DBException;
import morning.repo.EdgeInstanceDao;
import morning.repo.FormInstanceDao;
import morning.repo.NodeInstanceDao;
import morning.repo.ProcessInstanceDao;
import morning.repo.TaskOverviewDao;
import morning.service.event.ProcessStartEvent;
import morning.service.event.TaskSubmitEvent;
import morning.service.event.listener.ProcessStartListener;
import morning.service.event.listener.TaskSubmitListener;
import morning.service.util.FormulaComputor;
import morning.service.util.TimeUtil;
import morning.util.IdGenerator;
import morning.vo.FormFieldInstance;
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
	private ProcessStartListener processStartListener;
	@Autowired
	private TaskSubmitListener taskSubmitListener;
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	@Autowired
	private FormInstanceDao formInstanceDao;
	@Autowired
	private ProcessEventSupport eventSupport;


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
		eventSupport.initListener(processStartListener);
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
		extractToNodeIDs(edgeList,toNodeTids);
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
	private void extractToNodeIDs(List<EdgeIns> edgeList, List<String> toNodeTids) {
		for(EdgeIns edge:edgeList) {
			toNodeTids.add(edge.getToNodeTId());
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

	/**
	 * 根据节点实例，创建其对应的出度实例
	 * @param processTmpDto
	 * @param nodeIns
	 * @return
	 */
	private List<EdgeIns> createDirectedArcByNodeIns(ProcessTemplateDTO processTmpDto, NodeInstance nodeIns, NodeInstanceDto nodeInstanceDto) {
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
		if(nodeIns.getNodeType().equals(NODE_TYPE.Gateway.toString())) {
			ConditionDto condition = processTmpDto.getGatewayNodeTemplateById(nodeIns.getNodeInsId()).getCondition();
			String condkey = condition.getFiledKey();
			Object insFVal = nodeInstanceDto.getFieldValueByFKey(condkey);
			List<String> toNodeTids = condition.getTargets().get(insFVal);
			inslist.forEach(ins->{
				if(!toNodeTids.contains(ins.getToNodeTId())) {
					inslist.remove(ins);
				}
			});
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
		ProcessTemplateDTO processTmpDto = processMetaService.getProcessTemplate(procIns.getProcessTemplateId());
		logger.info("提交表单，状态：{}",nodeInstanceDto.getNodeStatus());
		String curNodeTId = nodeInstanceDto.getNodeTemplateId();
		NodeInstance curNodeIns = null;
		List<String> toNodeTids = new ArrayList<String>();
		if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Ready.getCode()) {
			//说明节点实例尚未创建，创建节点实例
			curNodeIns = procIns.createNodeInstance(curNodeTId, 
					nodeInstanceDto.getNodeType(), 
					TASK_STATUS.End.getCode(), 
					nodeInstanceDto.getNodeName(),
					userId);
			//创建并保存表单实例
			List<FormInstance> formInsList = curNodeIns.createFormInstance(nodeInstanceDto.getFormInsDtoList());
			//字段值对象关联表单实例ID
			nodeInstanceDto.getFormInsDtoList().forEach(formDto->{
				formDto.setFormFieldInstance();
			});
			//创建有向弧
			List<EdgeIns> edgeInsList = createDirectedArcByNodeIns(processTmpDto,curNodeIns,nodeInstanceDto);
			// 从有向弧取出所有的目标节点模版ID
			extractToNodeIDs(edgeInsList,toNodeTids);
			{
				nodeInstanceDao.save(curNodeIns);
				formInstanceDao.saveAll(formInsList);
				formInstanceDao.saveField(nodeInstanceDto.getFormInsDtoList());
				edgeInstanceDao.save(edgeInsList);
			}
		}else if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Runing.getCode()){
			//取节点实例和表单字段
			curNodeIns = nodeInstanceDao.getById(nodeInstanceDto.getNodeInsId());
			curNodeIns.setNodeStatus(TASK_STATUS.End.getCode());
			//创建有向弧
			List<EdgeIns> edgeInsList = createDirectedArcByNodeIns(processTmpDto,curNodeIns,nodeInstanceDto);
			// 从有向弧取出所有的目标节点模版ID和节点实例ID
			extractToNodeIDs(edgeInsList,toNodeTids);
			{	
				nodeInstanceDao.updateNodeStatus(curNodeIns);
				formInstanceDao.updateFormFieldVal(nodeInstanceDto.getFormInsDtoList());
				edgeInstanceDao.save(edgeInsList);
			}
		}
		// 当一个的聚合持久化之后发送事件通知下一个任务节点
		eventSupport.initListener(taskSubmitListener);
		TaskSubmitEvent event = new TaskSubmitEvent("",toNodeTids,EVENT_TYPE.node_end);
		noticeByEvent(event,
				processTmpDto.getProcessTemplateId(),
				procIns.getProcessInsId(),
				curNodeIns.getNodeInsId(),
				curNodeIns.getNodeTemplateId(),
				procIns.getProcessName(),
				curNodeIns.getNodeName(),
				userId);
		
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
	 * 用户提交表单：节点状态为{@code TASK_STATUS.Ready}创建保存节点实例/表单实例，变更状态{@code TASK_STATUS.Running}；</p>
	 * 节点状态为{@code TASK_STATUS.Running}获取实例，更新保存实例
	 * @param nodeInstanceDto
	 * @param userId
	 * @throws DBException 
	 */
	@Transactional(rollbackFor=DBException.class)
	public void saveForm(NodeInstanceDto nodeInstanceDto, String userId) throws DBException {
		ProcessInstance procIns = processInstanceDao.getById(nodeInstanceDto.getProcessInsId());
		NodeInstance nodeIns = null;
		if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Ready.getCode()) {
			//说明节点实例尚未创建，创建节点实例
			nodeIns = procIns.createNodeInstance(nodeInstanceDto.getNodeTemplateId(), 
					nodeInstanceDto.getNodeType(), 
					TASK_STATUS.Runing.getCode(), 
					nodeInstanceDto.getNodeName(),
					userId);
			//创建并保存表单实例
			List<FormInstance> formInsList = nodeIns.createFormInstance(nodeInstanceDto.getFormInsDtoList());
			//获取并保存字段值对象
			nodeInstanceDto.getFormInsDtoList().forEach(formDto->{
				formDto.setFormFieldInstance();
			});
			{
				nodeInstanceDao.save(nodeIns);
				formInstanceDao.saveAll(formInsList);
				formInstanceDao.saveField(nodeInstanceDto.getFormInsDtoList());
			}
		}else if(nodeInstanceDto.getNodeStatus() == TASK_STATUS.Runing.getCode()){
			formInstanceDao.updateFormFieldVal(nodeInstanceDto.getFormInsDtoList());
		}
	}

	/**
	 * 取节点信息（包含表单以及字段值） 
	 */
	public Map<String,List<FormFieldInstance>> getNodeIns(String nodeInsId) throws DBException {
		List<FormInstance> formInsList = formInstanceDao.getFormInsByNodeInsId(nodeInsId);
		Map<String,List<FormFieldInstance>>  formFieldMap = formInstanceDao.getFormFieldInstance(formInsList);
		return formFieldMap;
	}


	/**
	 * 当节点状态为{@code TASK_STATUS.Ready}时，根据单据转换规则计算此节点字段的默认值。</p>
	 * 由于当前节点尚未创建，没有实例。
	 * @param processTid
	 * @param nodeTid
	 * @return Key:表单模版ID，Val:计算过的字段值Map
	 * @throws DBException 
	 */
	public Map<String, Map<String, Object>> getReadyForm(String processTid,String nodeTid,String proceInsId) throws DBException {
		Map<String, Map<String, Object>> formField = new HashMap<String,Map<String,Object>>();
		NodeTemplateDto nodeDto = processMetaService.getNodeTemplateListByNodeTid(processTid,nodeTid);
		List<FormPropertyDto> formProperties = nodeDto.getFormProperties();
		for(FormPropertyDto formdto : formProperties) {
			Map<String, Object> currentFormFieldMap = new HashMap<String,Object>();
			//根据ruleId取rule
			FormTransformRuleDto ruleDto = processMetaService.getFormRuleById(formdto.getRuleId());
			//封装参数。根据rule获取源单的字段和值，并调用FormulaComputor
			List<FieldTransformRuleDto> fieldRules = ruleDto.getFieldRule();
			for(FieldTransformRuleDto fieldrule:fieldRules) {
				Map<String,Object> inputFields = new HashMap<String,Object>();
				String formInsId = getFormInsFromProcessIns(proceInsId,ruleDto.getSrcFormTid());
				List<FormFieldInstance> fields = formInstanceDao.queryFieldByFormInsId(formInsId);
				for(FormFieldInstance field:fields) {
					if(fieldrule.getSrcFkey().contains(field.getFkey())) {
						inputFields.put(field.getFkey(), field.getFval());
					}
				}
				Object result = FormulaComputor.calculate(inputFields, fieldrule.getFormula());
				currentFormFieldMap.put(fieldrule.getDesFkey(), result);
			}
			formField.put(formdto.getFormTemplateId(), currentFormFieldMap);
		}
		return formField;
	}

	private String getFormInsFromProcessIns(String proceInsId, String srcFormTid) throws DBException {
		List<FormInstance> formInsList = formInstanceDao.getFormInsByProInsId(proceInsId);
		for(FormInstance form:formInsList) {
			if(srcFormTid.equals(form.getFormTid())) {
				return form.getFormInstanceId();
			}
		}
		return null;
	}
}











