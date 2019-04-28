package morning.service.aop;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import morning.event.Event;

@Aspect
@Component
public class StoreEvent {

	private static Logger logger = LoggerFactory.getLogger(StoreEvent.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Pointcut("execution(public * morning.service.event.ProcessEventSupport.dispatchEvent(..))")
	private void eventSupportAspect() {}

	/**
	 * 转发事件之前，将其持久化到mongoDB
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("eventSupportAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature = (MethodSignature) signature;
        //获取到方法的所有参数名称的字符串数组
//        String[] parameterNames = methodSignature.getParameterNames();
		logger.info("[ {} ] stored",args[0]);
        mongoTemplate.save(args[0], "event_store");
//        for (int i =0 ,len=parameterNames.length;i < len ;i++){
//        		logger.info("参数名："+ parameterNames[i] + " = " +JSON.toJSONString(args[i]));
//        		
//        }
    }

	public List<Event> getEventById(String nodeInstanceId) {
		Query query = new Query();
		Criteria cri = new Criteria();
		cri.where("1").is("1").and("nodeInstanceId").is(nodeInstanceId);
		query.addCriteria(cri);
		return mongoTemplate.find(query, Event.class);
	
	}


	
}
