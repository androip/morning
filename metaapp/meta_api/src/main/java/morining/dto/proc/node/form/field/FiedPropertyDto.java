package morining.dto.proc.node.form.field;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FiedPropertyDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -8731963498758693899L;
	private String filedKey;
	private String filedName;
	private String filedViewName;
	private String filedType;
	private Boolean readOnly;
	private Boolean visible;
	private RelationInfoDto relation;
	
	
}
