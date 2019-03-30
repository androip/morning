package morining.dto.proc.node.form.field;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class RelationInfoDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930635352286346174L;
	
	/**
	 * Select viewFkey
	 * from relationSource
	 * where conditionFKey and foreignFkey;
	 */
	private String relationSource;
	private String viewFkey;
	private String foreignFkey;
	private String conditionFKey;


}
