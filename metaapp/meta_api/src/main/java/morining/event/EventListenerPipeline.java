package morining.event;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 事件处理链表，根据链表顺序处理事件
 * @author jp
 */
public class EventListenerPipeline {

	private static Logger logger = LoggerFactory.getLogger(EventListenerPipeline.class);
	private LinkedList<EventListener> pipelineList = new LinkedList<EventListener>();
	
	public void addFirst(EventListener listener) {
		pipelineList.addFirst(listener);;
	}
	
	public void add(EventListener listener) {
		pipelineList.add(listener);
	}
	
	/**
	 * 取下一个listener
	 * @param listener 当前监听器
	 * @return
	 */
	public EventListener getNext(EventListener listener) {
		int index = pipelineList.indexOf(listener);
		if(index < 0 ) {
			return null;
		}
		EventListener next = pipelineList.get(++index);
		logger.info("====="+pipelineList.toString());
		return next;
	}
	
	/**
	 * 取前一个listener
	 * @param listener 当前监听器
	 * @return
	 */
	public EventListener getLast(EventListener listener) {
		int index = pipelineList.lastIndexOf(listener);
		if(index < 0 ) {
			return null;
		}
		return pipelineList.get(--index);
	}

}
