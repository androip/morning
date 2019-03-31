package morning.entity.process.node.form;

public enum FORMTYPE {

	BILL("bill"),
	DETAIL("detail"),
	ATTACHE("attache");
	
	String typeValue;
	
	FORMTYPE(String typeValue){
		this.typeValue = typeValue;
	}
	
	public String getTypeValue(){
		return this.typeValue;
	}
	
	public FORMTYPE getEnumByTypeValue(String typeValue) {
		for(FORMTYPE formType:FORMTYPE.values()) {
			if(formType.getTypeValue().equals(typeValue)) {
				return formType;
			}
		}
		return null;
		
	}
	
}
