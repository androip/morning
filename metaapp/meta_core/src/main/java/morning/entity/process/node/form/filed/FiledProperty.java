package morning.entity.process.node.form.filed;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FiledProperty implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6993012690669251958L;
	private String filedKey;
	private String filedName;
	private String filedViewName;
	private String filedType;
	private Boolean readOnly;
	private Boolean visible;
	private RelationInfo relation;

}
