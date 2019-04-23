package morning.service.exception;

public class MorningException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5226969933079128801L;
	private static final String MESSAGE = "";

	public MorningException() {
		super(MESSAGE);
	}

	public MorningException(Exception e) {
		super(MESSAGE, e);
	}

	public MorningException(Throwable throwable) {
		super(MESSAGE, throwable);
	}

	public MorningException(String msg) {
		super(msg);
	}

	public MorningException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
