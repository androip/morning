package morning.service.event;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import morning.event.EVENT_TYPE;
import morning.event.Event;
import morning.event.EventListener;
import morning.service.event.exception.EventException;
import morning.service.exception.MorningException;
import morning.service.impl.EngineService;

@Component
public class ProcessEventSupport {

	private static Logger logger = LoggerFactory.getLogger(ProcessEventSupport.class);
	
	private List<EventListener> listenerList;
	private Map<EVENT_TYPE,List<EventListener>> typedListeners;
	private Event event;
	
	public void registerObserver(EventListener listener) {
		// TODO Auto-generated method stub

	}
	public void registerObserver(EventListener listener,EVENT_TYPE eventType) {
		// TODO Auto-generated method stub
		
	}

	public void removeObserver(EventListener listener) {
		// TODO Auto-generated method stub

	}

	/**
	 * Forward the event
	 * @param event
	 * @throws EventException
	 * @throws MorningException 
	 */
	public void dispatchEvent(Event event) throws EventException, MorningException {
		if(event == null || event.getEventType() == null) {
			throw new EventException("Event can not be null");
		}
//		if(!listenerList.isEmpty()) {
//			for(EventListener listener:listenerList) {
//				dispatchEvent(event,listener);
//			}
//		}
//		List<EventListener> typed = typedListeners.get(event.getEventType());
//		if(typed != null && typed.isEmpty()) {
//			for(EventListener listener: typed) {
//				dispatchEvent(event,listener);
//			}
//		}
	}
	private void dispatchEvent(Event event , EventListener listener) throws MorningException {
		try {
			listener.onEvent(event);
		} catch(Throwable t) {
			if(listener.isFailOnException()) {
				throw new MorningException("Exception while exeuting event - listener",t);
			}else {
				logger.error("Exception while exeuting event - listener");
			}
		}
		
	}
	/**
	 * Set basic properties for event
	 * @param event
	 * @param procTmpId
	 * @param processInsId
	 * @param nodeInsId
	 * @param nodeTemplateId
	 * @param eventType
	 */
	public void initEvent(Event event, String procTmpId, String processInsId, String nodeInsId, String nodeTemplateId,
			EVENT_TYPE eventType) {
		event.setProcessTId(procTmpId);
		event.setProcessInstanceId(processInsId);
		event.setNodeTId(nodeTemplateId);
		event.setNodeInstanceId(nodeInsId);
		event.setEventType(eventType);
	}

}
