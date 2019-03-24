package morning.entity;

import java.io.Serializable;


public class TestEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4482778810797504628L;
	private String id;
    private String userName;
    private String passWord;
    
    public TestEntity() {
		super();
	}
	public TestEntity(String id, String userName, String passWord) {
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
