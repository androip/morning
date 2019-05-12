package morining.dto.rule;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldTransformRuleDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8508591734237976136L;
	private String srcFkey;
    private String desFkey;
    private String formula;
    private String ruleType;

}
