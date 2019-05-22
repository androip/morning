package morning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.rule.FormTransformRuleDto;
import morining.event.EVENT_TYPE;
import morining.event.Event;
import morining.event.ProcessEventSupport;
import morining.exception.EventException;
import morining.exception.MetaServiceException;
import morining.exception.MorningException;
import morining.exception.RuleException;
import morning.bill.FormTransformRule;
import morning.entity.process.ProcessTemplate;
import morning.repo.FormTransformRuleRepository;
import morning.repo.MetaDao;
import morning.repo.ProcessTemplateRepository;
import morning.service.event.FormRuleEvent;
import morning.service.event.listener.CreateFormRuleListener;
import morning.service.factory.FormTransformRuleDTOFactory;
import morning.service.factory.FormTransformRuleFactory;
import morning.service.factory.ProcessTemplateDtoFactory;
import morning.service.factory.ProcessTemplateFactory;

@Service
public class MetaServiceImpl {

	@Autowired
	private ProcessTemplateRepository processTemplateRepository;
	@Autowired
	private MetaDao metaDao;
	@Autowired
	private FormTransformRuleRepository formTransformRuleRepository;
	@Autowired
	private ProcessTemplateDtoFactory processTemplateDtoFactory;
	@Autowired
	private ProcessEventSupport eventSupport;
	@Autowired
	private CreateFormRuleListener createFormRuleListener;

	
	public ProcessTemplateDTO getProcessTemplateById(String templateId) throws MetaServiceException {
		ProcessTemplate entity = processTemplateRepository.findById(templateId).orElseThrow(()->new MetaServiceException("马勒戈壁，没找到！"));
		ProcessTemplateDTO dto = processTemplateDtoFactory.createDto(entity);
		return dto;
	}

	public void saveProcessTemplate(ProcessTemplateDTO dto) {
		ProcessTemplate entity = new ProcessTemplateFactory().create(dto);
		processTemplateRepository.save(entity);
	}

	public void delete(String processtemplateId) {
		processTemplateRepository.deleteById(processtemplateId);
	}

	/**
	 * paging query
	 * @param start
	 * @param size
	 * @return
	 */
	public List<ProcessTemplateDTO> getProcessTemplateList(int start,int size) {
		Page<ProcessTemplate> entityList = processTemplateRepository.findAll(PageRequest.of(start, size));
		List<ProcessTemplateDTO> dtoList = new ArrayList<ProcessTemplateDTO>();
		for(ProcessTemplate obj : entityList) {
			ProcessTemplateDTO dto = processTemplateDtoFactory.createDto(obj);
			dtoList.add(dto);
		}
		return dtoList;
	}

	public List<ProcessTemplateDTO> getProcessTemplateLByCondition(Map<String, Object> requestMap) {
		List<ProcessTemplate> objList = metaDao.query(requestMap);
		return processTemplateDtoFactory.createDtoList(objList);
	}


	// 需要添加缓存
	@Cacheable(value = "nodeTmp" ,key = "#p0+#p1")
	public NodeTemplateDto getNodeTemplateLByNodeTid(String processTid,String nodeTid) throws MetaServiceException {
		ProcessTemplateDTO proceTmpDto = getProcessTemplateById(processTid);
		NodeTemplateDto nodeTmp = proceTmpDto.getNodeTemplateById(nodeTid);
		return nodeTmp;
	}

	public ProcessTemplateDTO getProcessTemplateLByNodeTid(String nodeTemplateId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createFromRule(FormTransformRuleDto dto) {
		FormTransformRule rule = new FormTransformRuleFactory().create(dto);
		formTransformRuleRepository.save(rule);
		eventSupport.registerListener(createFormRuleListener, EVENT_TYPE.rule_create);
		//创建事件
		Event event = new FormRuleEvent(rule.getTransformId(),
												rule.getSrcFormTid(),
												rule.getDesFormTId(),
												EVENT_TYPE.rule_create,
												dto.getProcessTid());
		//通知Meta更新ruleID
		noticeByEvent(event);
	}

	private void noticeByEvent(Event event) {
		try {
			eventSupport.dispatchEvent(event, event.getEventType());
		} catch (EventException | MorningException e) {
			e.printStackTrace();
		}
		
	}

	public FormTransformRuleDto getFromRuleById(String ruleId) throws RuleException {
		FormTransformRule rule = formTransformRuleRepository.findById(ruleId).orElseThrow(()->new RuleException("马勒戈壁，没找到！"));
		FormTransformRuleDto dto = new FormTransformRuleDTOFactory().createDto(rule);
		return dto;
	}


}











