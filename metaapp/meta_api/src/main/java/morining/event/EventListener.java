package morining.event;

/**
 * The <code>EventListener</code> interface is the primary method for
 * handling events.
 * @author jp
 *
 */
public interface EventListener {

	/**
	 * handle event
	 * @param event
	 * @throws Exception 
	 */
	public void onEvent(Event event) throws Exception;
	/**
	 * Register listener.
	 * In addition,if current listener has future handle and is head of <code>EventListenerPipeline</code>,
	 * call this method to init <code>EventListenerPipeline</code>.
	 */
	public void init();
	/**
	 * Whether an exception needs to be handled
	 * @return
	 */
	public boolean isFailOnException();
	
	public EventListenerPipeline pipeline();
}
