package morning.entity.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EdgeIns {
	
	
	private String id;
	private String processInsId;
	private String processTId;
	private String fromNodeInsId;
	private String fromNodeTId;
	private String toNodeInsId;
	private String toNodeTId;

}
