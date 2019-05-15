package morning.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StoreEvent {

	private static Logger logger = LoggerFactory.getLogger(StoreEvent.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Pointcut("execution(public * morining.event.ProcessEventSupport.dispatchEvent(..))")
	private void eventSupportAspect() {}

	/**
	 * 转发事件之前，将其持久化到mongoDB
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("eventSupportAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		logger.info("[ {} ] stored",args[0]);
        mongoTemplate.save(args[0], "event_store");		
    }
}
