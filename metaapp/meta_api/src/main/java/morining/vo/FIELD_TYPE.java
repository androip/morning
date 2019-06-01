package morining.vo;

public enum FIELD_TYPE {

	String("String"),
	Integer("Integer"),
	Boolean("Boolean"),
	Float("Float"),
	Related("Related");
	
	private String val;
	
	FIELD_TYPE(String val) {
		this.val = val;
	}

	public String getValue() {
		return this.val;
	}
    
}
