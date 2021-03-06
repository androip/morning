package morning.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskOverviewDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8230172459995520260L;

	private String taskName;
	private String createTime;
	private Integer taskStatus;
	private String nodeInsId;
	private String nodeTId;
	private String processInsId;
	private String processTId;
	
}
