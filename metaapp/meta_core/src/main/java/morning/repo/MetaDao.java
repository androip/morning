package morning.repo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import morning.entity.process.ProcessTemplate;

@Component
public class MetaDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	
	public List<ProcessTemplate> queryByName(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("processName").is(name));
		return mongoTemplate.find(query, ProcessTemplate.class);
	}


	public List<ProcessTemplate> query(Map<String, Object> requestMap) {
		Query query = new Query();
		Criteria cri = new Criteria();
		cri.where("1").is("1");
		for (Map.Entry<String, Object> entry : requestMap.entrySet()) { 
			cri.and(entry.getKey()).is(entry.getValue());
		}
		query.addCriteria(cri);
		return mongoTemplate.find(query, ProcessTemplate.class);
	}

}
