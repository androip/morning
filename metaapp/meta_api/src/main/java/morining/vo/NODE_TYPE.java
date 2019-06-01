package morining.vo;

public enum NODE_TYPE {

	
	Start("Start"),
	Task("Task"),
	Gateway("Gateway"),
	End("End");
	
	private String val;
	
	NODE_TYPE(String val) {
		this.val = val;
	}

	public String getValue() {
		return this.val;
	}


	
}
