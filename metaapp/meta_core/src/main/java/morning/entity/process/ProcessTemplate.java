package morning.entity.process;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.entity.process.edge.Edge;
import morning.entity.process.node.GatewayNodeTemplate;
import morning.entity.process.node.NodeTemplate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "process_template")
public class ProcessTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4350482863676720917L;
	@Id
	private String processTemplateId;
	
	private String processName;
	
	private List<NodeTemplate> nodeTemplateList;
	
	private List<GatewayNodeTemplate> gatewayNodeTemplateList;
	
	private List<Edge> edgeList;

}
