package morning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskOverview {

	private String userId;
	private String processInsId;
	private String processNodeInsId;
	private Integer taskStatus;
	
}
