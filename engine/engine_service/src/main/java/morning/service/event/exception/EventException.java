package morning.service.event.exception;

public class EventException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1867911281274558513L;

	private static final String MESSAGE = "";

	public EventException() {
		super(MESSAGE);
	}

	public EventException(Exception e) {
		super(MESSAGE, e);
	}

	public EventException(Throwable throwable) {
		super(MESSAGE, throwable);
	}

	public EventException(String msg) {
		super(msg);
	}
	
}
