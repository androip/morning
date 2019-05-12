package morining.dto.rule;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormTransformRuleDto implements Serializable{
	

		/**
	 * 
	 */
	private static final long serialVersionUID = -112792063057100330L;
    private String transformId;
    private String processTid;
	private String srcFormTid;
    private String desFormTId;
    private FieldTransformRuleDto fieldRule;

}
