package morning.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.process.ProcessTemplate;
import morning.repo.MetaDao;
import morning.repo.ProcessTemplateRepository;
import morning.service.factory.ProcessTemplateDtoFactory;
import morning.service.factory.ProcessTemplateFactory;

@Service
public class MetaServiceImpl {

	@Autowired
	private ProcessTemplateRepository processTemplateRepository;
	@Autowired
	private MetaDao metaDao;
	@Autowired
	private ProcessTemplateFactory processTemplateFactory;
	
	@Autowired
	private ProcessTemplateDtoFactory processTemplateDtoFactory;
	
	public ProcessTemplateDTO getProcessTemplateById(String templateId) {
		ProcessTemplate entity = processTemplateRepository.findById(templateId).orElseThrow(()->new RuntimeException("马勒戈壁，没找到！"));
		ProcessTemplateDTO dto = processTemplateDtoFactory.createDto(entity);
		return dto;
	}

	public void saveProcessTemplate(ProcessTemplateDTO dto) {
		ProcessTemplate entity = processTemplateFactory.create(dto);
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


}
