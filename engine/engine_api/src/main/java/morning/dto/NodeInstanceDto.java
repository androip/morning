package morning.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeInstanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -849217651521149178L;
	private String nodeInsId;
	private String nodeTemplateId;
	private String processInsId;
	private String nodeName;
	private String nodeType;
	private String nodeStatus;
	
}
