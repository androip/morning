package morning.exception;

public class DBException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672664819120705828L;
	
	private static final String MESSAGE = "";

	public DBException() {
		super(MESSAGE);
	}

	public DBException(Exception e) {
		super(MESSAGE, e);
	}

	public DBException(Throwable throwable) {
		super(MESSAGE, throwable);
	}

	public DBException(String msg) {
		super(msg);
	}
	
}
