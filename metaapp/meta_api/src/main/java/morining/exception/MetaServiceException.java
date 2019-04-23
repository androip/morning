package morining.exception;

public class MetaServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1672664819120705828L;
	
	private static final String MESSAGE = "";

	public MetaServiceException() {
		super(MESSAGE);
	}

	public MetaServiceException(Exception e) {
		super(MESSAGE, e);
	}

	public MetaServiceException(Throwable throwable) {
		super(MESSAGE, throwable);
	}

	public MetaServiceException(String msg) {
		super(msg);
	}

}
