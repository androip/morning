package morining.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import morining.exception.EventException;
import morining.exception.MorningException;


@Component
public class ProcessEventSupport {

	private static Logger logger = LoggerFactory.getLogger(ProcessEventSupport.class);
	
	private List<EventListener> listenerList = new ArrayList<EventListener>();
	private Map<EVENT_TYPE,List<EventListener>> typedListeners = new HashMap<EVENT_TYPE,List<EventListener>>();
	
	/**
	 * 注册全局事件监听器
	 * @param listener
	 */
	synchronized public ProcessEventSupport registerListener(EventListener listener) {
		if(null != listener) {
			if(!listenerList.contains(listener)) {
				listenerList.add(listener);
			}
		}
		return this;
	}
	
	/**
	 * 根据事件类型注册事件监听器
	 * @param listener
	 * @param eventType
	 */
	synchronized public ProcessEventSupport registerListener(EventListener listener,EVENT_TYPE...eventType) {
		logger.debug("before registerListener typedListeners {}",typedListeners);
		if(null != listener) {
			if(eventType == null || eventType.length == 0 ) {
				registerListener(listener);		//注册全局事件监听器
			}else {
				for(EVENT_TYPE type :eventType) {
					addTypedEventListener(listener,type);//注册具体类型事件监听器
				}
			}
		}
		logger.debug("after registerListener typedListeners {}",typedListeners);
		return this;
	}
	
	/**
	 * 将监听器添加到{@link EventListenerPipeline}的头部
	 * @param listener
	 * @param eventType
	 */
	synchronized public ProcessEventSupport registerListenerToPipelineHead(EventListener listener) {
		listener.pipeline().addFirst(listener);
		return this;
	}
	/**
	 * 将监听器添加到{@link EventListenerPipeline}中
	 * @param listener
	 * @param eventType
	 */
	synchronized public ProcessEventSupport registerListenerToPipeline(EventListener listener) {
		listener.pipeline().add(listener);
		return this;
	}
	
	/**
	 * 将监听器添加到{@link EventListenerPipeline}中
	 * @param perlistener 前一个监听器
	 * @param 当前监听器
	 */
	synchronized public ProcessEventSupport registerListenerToPipelineAfter(EventListener perlistener,EventListener listener) {
		if(null != perlistener) {
			perlistener.pipeline().add(listener);
		}
		return this;
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
		listenerList.remove(listener);
	}

	/**
	 * Forward All the event
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
			logger.debug("listener {} ",listener);
			listener.onEvent(event);
		} catch(Throwable t) {
			if(listener.isFailOnException()) {
				t.printStackTrace();
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
	 * @param createTime 
	 * @param processName 
	 * @param nodeName 
	 */
	public ProcessEventSupport initEvent(Event event, String procTmpId, String processInsId, String nodeInsId, String nodeTemplateId,
			EVENT_TYPE eventType, String userId, String createTime, String processName, String nodeName) {
		logger.debug("before event{}",event);
		event.setProcessTId(procTmpId);
		event.setProcessInstanceId(processInsId);
		event.setNodeTId(nodeTemplateId);
		event.setNodeInstanceId(nodeInsId);
		event.setEventType(eventType);
		event.setUserId(userId);
		event.setCreateTime(createTime);
		event.setProcessName(processName);
		event.setNodeName(nodeName);
		logger.debug("after event{}",event);
		return this;
	}
	
	/**
	 * 只转发指定类型的事件
	 * @param event
	 * @param nodeEnd
	 * @throws EventException
	 * @throws MorningException
	 */
	public void dispatchEvent(Event event, EVENT_TYPE eventType) throws EventException, MorningException {
		logger.debug("dispatchEvent {} type {}",event,eventType);
		if(event == null || event.getEventType() == null) {
			throw new EventException("Event can not be null");
		}
		logger.debug("typedListeners {}",typedListeners);
		List<EventListener> typed = typedListeners.get(eventType);
		logger.debug("typedlist {}",typed);
		if(typed != null && !typed.isEmpty()) {
			for(EventListener listener: typed) {
				dispatchEvent(event,listener);
			}
		}
	}

	/**
	 * 根据{@link EventListenerPipeline}将{@code event}转发给下一个监听
	 * @param updateEvent
	 * @param listener 当前监听
	 * @throws MorningException 
	 */
	public void dispatchEventToNextListener(EventListener listener,Event event) throws MorningException {
		try {
			logger.debug("listener {} ",listener);
			listener.pipeline().getNext(listener).onEvent(event);
		} catch(Throwable t) {
			if(listener.isFailOnException()) {
				t.printStackTrace();
				throw new MorningException("Exception while exeuting event - listener",t);
			}else {
				logger.error("Exception while exeuting event - listener");
			}
		}
	}

	synchronized public void initListener(EventListener listener) {
		listener.init();
	}
}
