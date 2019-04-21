package morining.dto.proc;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
	private String createTime;
	private String updateTime;
	private List<NodeTemplateDto> nodeTemplateDtoList;
	private List<GatewayNodeTemplateDto> gatewayNodeTemplateDtoList;
	private List<EdgeDto> edgeDtoList;
	
	public String startNodeTmpId() {
		for(NodeTemplateDto dto:nodeTemplateDtoList) {
			if(dto.getNodeTemplateType().equals("Start")) {
				return dto.getNodeTemplateId();
			}
		}
		return null;
	}


	public String getNodeType(String nodeTid) {
		for(NodeTemplateDto dto:nodeTemplateDtoList) {
			if(dto.getNodeTemplateId().equals(nodeTid)) {
				return dto.getNodeTemplateType();
			}
		}
		return null;
	}

}
