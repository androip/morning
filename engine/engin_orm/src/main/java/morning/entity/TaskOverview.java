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
	private String createTime;
	private String taskName;
	private String processName;
	private Integer taskStatus;//值为0说明该节点尚未创建实例，需根据nodeTId创建实例；值为1说明节点已经创建实例
	private String nodeTId;
	private String processsTId;
	
}
