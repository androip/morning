package morning.entity.process;

import java.util.UUID;

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
	
	public NodeInstance createNodeInstance(String nodeTempId,String type,Integer status, String nodeName, String userId) {
		NodeInstance nodeIns = new NodeInstance();
		nodeIns.setNodeInsId(UUID.randomUUID().toString());
		nodeIns.setNodeTemplateId(nodeTempId);
		nodeIns.setNodeType(type);
		nodeIns.setProcessInsId(this.processInsId);
		nodeIns.setNodeStatus(status);
		nodeIns.setNodeName(nodeName);
		nodeIns.setUserId(userId);
		return nodeIns;
	}
	
}
