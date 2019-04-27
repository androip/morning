package morning.entity.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeInstance {

	private String nodeInsId;
	private String nodeTemplateId;
	private String processInsId;
	private String nodeName;
	private String nodeType;
	private String nodeStatus;
}
