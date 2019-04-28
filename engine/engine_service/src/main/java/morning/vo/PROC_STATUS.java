package morning.vo;

public enum PROC_STATUS {
	READY("Ready"), RUNNING("Running");

	private String strVal;
	
	PROC_STATUS(String val) {
		this.strVal = val;
	}

	public String getValue() {
		return this.strVal;
	}


	
	

	
}
