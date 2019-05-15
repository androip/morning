package morining.event;

/**
 * The <code>EventListener</code> interface is the primary method for
 * handling events.
 * <code>AddEventListener</code> method. 
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
	 * Whether an exception needs to be handled
	 * @return
	 */
	public boolean isFailOnException();
}
