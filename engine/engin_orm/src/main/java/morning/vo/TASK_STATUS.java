package morning.vo;

public enum TASK_STATUS {

	Ready(0),
	Runing(1),
	End(2);
	
	private Integer statusCode;
	
	TASK_STATUS(int code){
		this.statusCode = code;
	}
	

	public Integer getCode() {
		return this.statusCode;
	}
	
	public static TASK_STATUS getBycode(Integer code) {
		for(TASK_STATUS val : TASK_STATUS.values()) {
			if(val.getCode() == code) {
				return val;
			}
		}
		return null;
	}
}
