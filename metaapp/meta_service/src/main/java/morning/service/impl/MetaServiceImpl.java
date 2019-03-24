package morning.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import morining.dto.TestEntityDTO;
import morning.dto.ProcessTemplateDTO;
import morning.entity.TestEntity;
import morning.repo.TestRepository;
import morning.service.api.IMetaService;

@Service
public class MetaServiceImpl implements IMetaService {

	@Autowired
	private TestRepository testRepository;
	
	@Override
	public ProcessTemplateDTO getProcessTemplateById(String templateId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createTestEntity(TestEntity entity) {
		testRepository.save(entity);
		
	}

	public TestEntity getEntity(String id) {
		
		return testRepository.findById(id).get();
	}

	public TestEntityDTO getEntityDTO(String id) {
		TestEntity obj = testRepository.findById(id).get();
		TestEntityDTO dto = new TestEntityDTO(obj.getId(),obj.getUserName(),obj.getPassWord());
		return dto;
	}

}
