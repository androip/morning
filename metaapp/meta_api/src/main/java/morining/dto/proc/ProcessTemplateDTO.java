package morining.dto.proc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public String extractStartNodeTmpId() {
		for(NodeTemplateDto dto:nodeTemplateDtoList) {
			if(dto.getNodeTemplateType().equals("Start")) {
				return dto.getNodeTemplateId();
			}
		}
		return null;
	}


	/**
	 * 根据节点TID取得节点类型
	 * @param nodeTid
	 * @return
	 */
	public String getNodeType(String nodeTid) {
		for(NodeTemplateDto dto:nodeTemplateDtoList) {
			if(dto.getNodeTemplateId().equals(nodeTid)) {
				return dto.getNodeTemplateType();
			}
		}
		return null;
	}


	public NodeTemplateDto getNodeTemplateById(String nodeTid) {
		for(NodeTemplateDto dto : nodeTemplateDtoList) {
			if(nodeTid.equals(dto.getNodeTemplateId()))
				return dto;
		}
		return null;
	}

	/**
	 * 取节点的出度
	 * @param nodeTid
	 * @return
	 */
	public List<EdgeDto> getEdgeDtoByFrom(String nodeTid) {
		
		return new ArrayList<EdgeDto>() {
			private static final long serialVersionUID = -1365629067842162842L;

		{
			edgeDtoList.forEach(dto->{
				if(dto.getFrom().equals(nodeTid)) {
					add(dto);
				}
			});
		}};
	}


	public NodeTemplateDto extractStartNodeTmp() {
		for(NodeTemplateDto dto:nodeTemplateDtoList) {
			if(dto.getNodeTemplateType().equals("Start")) {
				return dto;
			}
		}
		return null;
		
	}


	/**
	 * 根据当前节点TID获取下一个节点的类型
	 * @param curNodeTId
	 * @return K:下一个节点ID，V:下一个节点类型
	 */
	public Map<String,String> getNextNodeType(String curNodeTId) {
		List<EdgeDto> froms = getEdgeDtoByFrom(curNodeTId);
		Map<String,String> typeMap = new HashMap<String,String>(){
			private static final long serialVersionUID = 1L;
			{
				froms.forEach(edge->{
					put(edge.getTo(),getNodeType(edge.getTo()));
				});
			}
		};
		return typeMap;
	}


	public GatewayNodeTemplateDto getGatewayNodeTemplateById(String nodeTId) {
		for(GatewayNodeTemplateDto dto : gatewayNodeTemplateDtoList) {
			if(nodeTId.equals(dto.getNodeTemplateId()))
				return dto;
		}
		return null;
	}

}
