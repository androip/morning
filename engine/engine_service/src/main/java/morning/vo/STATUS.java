package morning.vo;

public enum STATUS {
	READY("Ready"), RUNNING("Running");

	private String strVal;
	
	STATUS(String val) {
		this.strVal = val;
	}

	public String getValue() {
		return this.strVal;
	}


	
	

	
}
