package morning.service.event.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import morining.api.IProcessMetaService;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.proc.node.form.FormPropertyDto;
import morining.dto.proc.node.form.field.FiedPropertyDto;
import morining.event.EVENT_TYPE;
import morining.event.Event;
import morining.event.EventListener;
import morining.event.EventListenerPipeline;
import morining.event.ProcessEventSupport;
import morining.vo.FIELD_TYPE;
import morining.vo.NODE_TYPE;
import morning.entity.TaskOverview;
import morning.entity.process.ProcessInstance;
import morning.repo.ProcessInstanceDao;
import morning.repo.TaskOverviewDao;
import morning.service.event.TaskSubmitEvent;
import morning.service.event.UpdateMainDataEvent;
import morning.vo.PROC_STATUS;
import morning.vo.TASK_STATUS;

@Component
public class TaskSubmitListener implements EventListener {

	private static Logger logger = LoggerFactory.getLogger(TaskSubmitListener.class);
	
	@Autowired
	private TaskOverviewDao taskOverviewDao;
	@Autowired
	private IProcessMetaService processMetaService;
	private EventListenerPipeline pipeline;
	@Autowired
	private ProcessEventSupport eventSupport;
	@Autowired
	private ProcessInstanceDao processInstanceDao;
	@Autowired
	private UpdateMainDataListener updateMainDataListener;
	
	private static boolean initFlag = false;
	
	@Override
	public void init() {
		if(!initFlag) {
			eventSupport.registerListener(this, EVENT_TYPE.node_end)
			.registerListenerToPipelineHead(this)
			.registerListenerToPipelineAfter(this, updateMainDataListener);
			initFlag = true;
		}
//		logger.info(" after init "+pipeline.toString());
	}
	
	
	@Override
	public void onEvent(Event event) throws Exception {
		logger.debug("On TaskSubmitListener: {}",JSON.toJSONString(event));
		TaskSubmitEvent submitEvent = (TaskSubmitEvent)event;
		List<String> toNodeTIds = submitEvent.getToNodeTids();
		// 创建任务一览记录---下游Task节点
		List<TaskOverview> taskOtaskOVs = new ArrayList<TaskOverview>();
		for(String toTId : toNodeTIds) {
			NodeTemplateDto nodeTemplate = processMetaService.getNodeTemplateListByNodeTid(event.getProcessTId(), toTId);
			if(nodeTemplate.getNodeTemplateType().equals(NODE_TYPE.End.getValue())) {//如果下一个节点是End,说明流程结束
				//结束流程。将流程状态设置为结束
				finishProcess(event.getProcessInstanceId());
				// 过滤出关联类型字段 找出关联表
				Map<String, Set<String>> relatedFieldMap = filterRelatedField(submitEvent.getProcessTId(),submitEvent.getNodeTId());
				// 给主数据系统发送通知 
				UpdateMainDataEvent updateEvent = new UpdateMainDataEvent(relatedFieldMap,event);
				eventSupport.dispatchEventToNextListener(this,updateEvent);
				
				
			}else {
				TaskOverview taskOV = new TaskOverview(event.getUserId(),
						event.getProcessInstanceId(),
						null,						//下一个节点尚未创建
						event.getCreateTime(),
						event.getNodeName(),
						event.getProcessName(),
						TASK_STATUS.Ready.getCode(),//因此状态应为Ready
						toTId,						
						event.getProcessTId());
				taskOtaskOVs.add(taskOV);
			}
		}
		//TODO 更新上一个任务一览状态
		// 把新Start节点消息保存到“任务一览”
		taskOverviewDao.save(taskOtaskOVs);
	}

	/**
	 * 过滤出关联类型字段。</p>
	 * 一般来说，需要持久化的字段通常都会在工作流最后一个节点确定，所以只需要过滤最后一个节点即可 
	 * @param procTid 
	 * @param processTId
	 * @return K 关联表名 : V Fkeys
	 */
	private Map<String, Set<String>> filterRelatedField(String procTid, String curNodeTId) {
		Map<String,Set<String>> relatedFieldMap = new HashMap<String,Set<String>>();
		NodeTemplateDto nodeTmplate = processMetaService.getNodeTemplateListByNodeTid(procTid, curNodeTId);
		List<FormPropertyDto> forms = nodeTmplate.getFormProperties();
			for(FormPropertyDto form:forms) {
				List<FiedPropertyDto> fields = form.getFieldProperties();
				for(FiedPropertyDto field:fields) {
					if(field.getFiledType().equals(FIELD_TYPE.Related.getValue())) {
						String tableName = field.getRelation().getRelationSource();
						if(!relatedFieldMap.containsKey(tableName)) {
							Set<String> relatedKeySet = new HashSet<String>();
							relatedKeySet.add(field.getFiledKey());
							relatedFieldMap.put(tableName, relatedKeySet);
						}else {
							relatedFieldMap.get(tableName).add(field.getFiledKey());
						}
					}
				}
		}
		return relatedFieldMap;
	}


	private void finishProcess(String processInstanceId) {
		ProcessInstance procIns = processInstanceDao.getById(processInstanceId);
		processInstanceDao.updateStatus(procIns.getProcessInsId(),PROC_STATUS.END.getValue());
	}


	@Override
	public boolean isFailOnException() {
		return true;
	}

	@Override
	synchronized public EventListenerPipeline pipeline() {
		if(pipeline == null) {
			pipeline = new EventListenerPipeline();
		}
		return this.pipeline;
	}





}
