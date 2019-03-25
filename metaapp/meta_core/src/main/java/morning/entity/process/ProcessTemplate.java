package morning.entity.process;

import java.io.Serializable;
import java.util.List;

import morning.entity.process.node.GatewayNodeTemplate;
import morning.entity.process.node.NodeTemplate;

public class ProcessTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4350482863676720917L;
	
	private String processTemplateId;
	
	private String processName;
	
	private List<NodeTemplate> nodeTemplateList;
	
	private List<GatewayNodeTemplate> gatewayNodeTemplateList;

}
