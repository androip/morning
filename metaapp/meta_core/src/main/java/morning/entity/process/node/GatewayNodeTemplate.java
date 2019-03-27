package morning.entity.process.node;

import java.util.List;

import lombok.Data;


@Data
public class GatewayNodeTemplate extends NodeTemplate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8026257902721853283L;
	private List <String> defaultEdgeId;
	private Condition condition;

}
