package morining.dto.proc;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.dto.TestEntityDTO;
import morining.dto.proc.edge.EdgeDto;
import morining.dto.proc.node.GatewayNodeTemplateDto;
import morining.dto.proc.node.NodeTemplateDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessTemplateDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2129030445545654283L;
	
	private String processTemplateId;
	
	private String processName;
	
	private List<NodeTemplateDto> nodeTemplateList;
	
	private List<GatewayNodeTemplateDto> gatewayNodeTemplateList;
	
	private List<EdgeDto> edgeList;

}
