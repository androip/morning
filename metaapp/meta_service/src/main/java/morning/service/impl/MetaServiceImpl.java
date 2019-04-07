package morning.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import morining.dto.TestEntityDTO;
import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.TestEntity;
import morning.entity.process.ProcessTemplate;
import morning.repo.ProcessTemplateRepository;
import morning.repo.TestRepository;
import morning.service.api.IMetaService;

@Service
public class MetaServiceImpl implements IMetaService {

	@Autowired
	private TestRepository testRepository;
	@Autowired
	private ProcessTemplateRepository processTemplateRepository;
	
	@Autowired
	private ProcessTemplateFactory processTemplateFactory;
	
	@Autowired
	private ProcessTemplateDtoFactory processTemplateDtoFactory;
	
	@Override
	public ProcessTemplateDTO getProcessTemplateById(String templateId) {
		ProcessTemplate entity = processTemplateRepository.findById(templateId).orElseThrow(()->new RuntimeException("马勒戈壁，没找到！"));
		ProcessTemplateDTO dto = processTemplateDtoFactory.createDto(entity);
		return dto;
	}

	public TestEntityDTO getEntityDTO(String id) {
		TestEntity obj = testRepository.findById(id).get();
		TestEntityDTO dto = new TestEntityDTO(obj.getId(),obj.getUserName(),obj.getPassWord());
		return dto;
	}

	public void saveProcessTemplate(ProcessTemplateDTO dto) {
		ProcessTemplate entity = processTemplateFactory.create(dto);
		processTemplateRepository.save(entity);
	}

	public void delete(String processtemplateId) {
		processTemplateRepository.deleteById(processtemplateId);
	}

	public List<ProcessTemplateDTO> getProcessTemplateList(int start,int size) {
		Page<ProcessTemplate> entityList = processTemplateRepository.findAll(PageRequest.of(start, size));
		List<ProcessTemplateDTO> dtoList = new ArrayList<ProcessTemplateDTO>();
		for(ProcessTemplate obj : entityList) {
			ProcessTemplateDTO dto = processTemplateDtoFactory.createDto(obj);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
