package morning.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import morning.entity.process.ProcessTemplate;


public interface ProcessTemplateRepository extends MongoRepository<ProcessTemplate,String>,QueryByExampleExecutor<ProcessTemplate>{

}
