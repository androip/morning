package morning.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestEntity implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4482778810797504628L;
	private String id;
    private String userName;
    private String passWord;
    
}
