package morning.service.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import morining.event.Event;
import morining.event.EventListener;
import morining.exception.MetaServiceException;
import morning.entity.process.ProcessTemplate;
import morning.repo.ProcessTemplateRepository;
import morning.service.event.FormRuleEvent;

@Component
public class CreateFormRuleListener implements EventListener{

	private static Logger logger = LoggerFactory.getLogger(CreateFormRuleListener.class);
	@Autowired
	private ProcessTemplateRepository processTemplateRepository;
	
	@Override
	public void onEvent(Event event) throws Exception {
		logger.debug("On listener: {}",JSON.toJSONString(event));
		//更新ruleId
		FormRuleEvent ruleEvent = (FormRuleEvent)event;
		ProcessTemplate procesTemp = processTemplateRepository.findById(ruleEvent.getProcessTId()).orElseThrow(()->new  MetaServiceException("马勒戈壁，没找到！"));
		procesTemp.getFormById(ruleEvent.getDesFormTId())
							.setRuleId(ruleEvent.getRuleId());
		procesTemp.getFormById(ruleEvent.getSrcFormTid())
					.setRuleId(ruleEvent.getRuleId());
		processTemplateRepository.save(procesTemp);
	}

	@Override
	public boolean isFailOnException() {
		return true;
	}

}
