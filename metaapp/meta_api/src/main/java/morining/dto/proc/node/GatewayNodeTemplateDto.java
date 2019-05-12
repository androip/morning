package morining.dto.proc.node;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GatewayNodeTemplateDto extends NodeTemplateDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818683516046862892L;

	private List <String> defaultEdgeId;
	private ConditionDto condition;
	public Object getFieldValueByFKey(String condkey) {
		// TODO Auto-generated method stub
		return null;
	}

}
