package morining.dto.rule;

import java.io.Serializable;
import java.util.List;

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
	private List<String> srcFkey;
    private String desFkey; //目标字段只有一个
    private String formula;
    private String ruleType;

}
