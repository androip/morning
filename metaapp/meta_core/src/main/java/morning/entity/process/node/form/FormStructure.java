package morning.entity.process.node.form;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormStructure implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8995567799970941109L;
	
	private String parentId;
	private String selfId;
	

}
