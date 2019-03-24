package morning.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import morning.entity.TestEntity;

@Repository
public interface TestRepository extends MongoRepository<TestEntity,String>{

}
