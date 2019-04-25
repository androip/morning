package morning.service.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
	
	private List<EventListener> listenerList = new ArrayList<EventListener>();
	private Map<EVENT_TYPE,List<EventListener>> typedListeners = new HashMap<EVENT_TYPE,List<EventListener>>();
	
	synchronized public void registerListener(EventListener listener) {
		if(null != listener) {
			if(!listenerList.contains(listener)) {
				listenerList.add(listener);
			}
		}
	}
	synchronized public void registerListener(EventListener listener,EVENT_TYPE...eventType) {
		if(null != listener) {
			if(eventType == null || eventType.length == 0 ) {
				registerListener(listener);		//注册全局事件监听器
			}else {
				for(EVENT_TYPE type :eventType) {
					addTypedEventListener(listener,type);//注册具体类型事件监听器
				}
			}
		}
	}
	

	synchronized private void addTypedEventListener(EventListener listener, EVENT_TYPE type) {
		List<EventListener> listeners = typedListeners.get(type);
		if(listeners == null) {
			listeners = new CopyOnWriteArrayList<EventListener>();
			typedListeners.put(type, listeners);
		}
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}
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
		if(!listenerList.isEmpty()) {
			for(EventListener listener:listenerList) {
				dispatchEvent(event,listener);
			}
		}
		List<EventListener> typed = typedListeners.get(event.getEventType());
		if(typed != null && typed.isEmpty()) {
			for(EventListener listener: typed) {
				dispatchEvent(event,listener);
			}
		}
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
	 * @param userId 
	 */
	public void initEvent(Event event, String procTmpId, String processInsId, String nodeInsId, String nodeTemplateId,
			EVENT_TYPE eventType, String userId) {
		event.setProcessTId(procTmpId);
		event.setProcessInstanceId(processInsId);
		event.setNodeTId(nodeTemplateId);
		event.setNodeInstanceId(nodeInsId);
		event.setEventType(eventType);
		event.setUserId(userId);
	}

}
