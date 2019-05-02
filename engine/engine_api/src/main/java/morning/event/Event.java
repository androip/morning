package morning.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {

	private String processTId;
	private String processInstanceId;
	private String nodeInstanceId;
	private String nodeTId;
	private String userId;
	private String createTime;
	private String processName;
	private String nodeName;
	public EVENT_TYPE eventType;
	
}
