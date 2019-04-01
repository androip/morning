package morning.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import morning.entity.process.ProcessTemplate;


public interface ProcessTemplateRepository extends MongoRepository<ProcessTemplate,String>{

}
