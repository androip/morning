package morning.service.event.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import morining.event.Event;
import morining.event.EventListener;
import morining.event.EventListenerPipeline;
import morning.repo.FormInstanceDao;
import morning.service.event.UpdateMainDataEvent;
import morning.vo.FormFieldInstance;

@Component
public class UpdateMainDataListener implements EventListener{

	private static Logger logger = LoggerFactory.getLogger(TaskSubmitListener.class);
	@Autowired
	private FormInstanceDao formInstanceDao;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void onEvent(Event event) throws Exception {
		UpdateMainDataEvent updateEvent = (UpdateMainDataEvent)event;
		Map<String, Set<String>> relatedFieldMap = updateEvent.getRelatedFieldMap();
		String nodeInsId = updateEvent.getNodeInstanceId();
		logger.info("formInstanceDao :"+ formInstanceDao);
		List<FormFieldInstance> fields = formInstanceDao.getFieldInsByNodeInsId(nodeInsId);
		Map<String,Map<String,Object>> updateField = generateUpdateField(relatedFieldMap,fields);
		// 通过AMQP发送消息updateField给主数据
		rabbitTemplate.convertAndSend("updateMD", updateField);
		System.out.println(" [x] Sent '" + updateField.toString() + "'");
		
	}

	/**
	 * 根据relatedFieldMap 生成字段键值对
	 * @param relatedFieldMap
	 * @param fields
	 * @return k 表名 ：V 字段键值对
	 */
	private Map<String, Map<String, Object>> generateUpdateField(Map<String, Set<String>> relatedFieldMap,
			List<FormFieldInstance> fields) {
		Map<String, Map<String, Object>> updateField = new HashMap<String,Map<String,Object>>();
		for(Map.Entry<String, Set<String>> entry:relatedFieldMap.entrySet()) {
			String tableName = entry.getKey();
			Set<String> fkeys = entry.getValue();
			Map<String,Object> fieldMap = new HashMap<String,Object>();
			for(FormFieldInstance field:fields) {
				if(fkeys.contains(field.getFkey())) {
					fieldMap.put(field.getFkey(), field.getFval());
				}
			}
			updateField.put(tableName, fieldMap);
		}
		return updateField;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFailOnException() {
		return true;
	}

	@Override
	public EventListenerPipeline pipeline() {
		// TODO Auto-generated method stub
		return null;
	}

}
