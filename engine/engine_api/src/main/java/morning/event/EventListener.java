package morning.event;

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
	 */
	public void onEvent(Event event);
	/**
	 * Whether an exception needs to be handled
	 * @return
	 */
	public boolean isFailOnException();
}
