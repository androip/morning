package morning.entity;

import java.util.UUID;

import javax.net.ssl.SSLEngineResult.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessInstance {

	private String processInsId;
	private String processTemplateId; 
	private String processName;
	private String createTime;
	private String updateTime;
	private String createUserId;
	private String status;
	
	public NodeInstance createNodeInstance(String nodeTempId,String type,String status) {
		NodeInstance nodeIns = new NodeInstance();
		nodeIns.setNodeInsId(UUID.randomUUID().toString());
		nodeIns.setNodeTemplateId(nodeTempId);
		nodeIns.setNodeType(type);
		nodeIns.setProcessInsId(this.processInsId);
		nodeIns.setNodeStatus(status);
		return nodeIns;
	}
	
}
