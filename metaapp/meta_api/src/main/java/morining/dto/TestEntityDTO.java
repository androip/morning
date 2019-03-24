package morining.dto;

import java.io.Serializable;

public class TestEntityDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030066814668296013L;
	private String id;
    private String userName;
    private String passWord;
    
    public TestEntityDTO() {
		super();
	}
	public TestEntityDTO(String id, String userName, String passWord) {
    	super();
    	this.id = id;
    	this.userName = userName;
    	this.passWord = passWord;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	
}
