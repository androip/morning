package morining.exception;

public class RuleException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5375625795070438026L;
	
	
	private static final String MESSAGE = "";

	public RuleException() {
		super(MESSAGE);
	}

	public RuleException(Exception e) {
		super(MESSAGE, e);
	}

	public RuleException(Throwable throwable) {
		super(MESSAGE, throwable);
	}

	public RuleException(String msg) {
		super(msg);
	}

}
