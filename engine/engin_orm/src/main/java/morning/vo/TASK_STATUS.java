package morning.vo;

public enum TASK_STATUS {

	Start(0),
	Runing(1),
	End(2);
	
	private Integer statusCode;
	
	TASK_STATUS(int code){
		this.statusCode = code;
	}
	

	public Integer getCode() {
		return this.statusCode;
	}
}
