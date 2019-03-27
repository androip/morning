package morining.dto;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestEntityDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5030066814668296013L;
	private String id;
    private String userName;
    private String passWord;
	
}
