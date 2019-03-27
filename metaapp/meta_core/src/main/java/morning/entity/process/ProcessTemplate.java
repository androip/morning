package morning.entity.process;

import java.io.Serializable;
import java.util.List;

import morning.entity.process.edge.Edge;
import morning.entity.process.node.GatewayNodeTemplate;
import morning.entity.process.node.NodeTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4350482863676720917L;
	
	private String processTemplateId;
	
	private String processName;
	
	private List<NodeTemplate> nodeTemplateList;
	
	private List<GatewayNodeTemplate> gatewayNodeTemplateList;
	
	private List<Edge> edgeList;

}
