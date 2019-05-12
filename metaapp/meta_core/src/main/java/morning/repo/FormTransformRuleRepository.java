package morning.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import morning.bill.FormTransformRule;

public interface FormTransformRuleRepository extends MongoRepository<FormTransformRule, String> {

}
