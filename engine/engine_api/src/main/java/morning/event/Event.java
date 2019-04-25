package morning.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class Event {

	private String processTId;
	private String processInstanceId;
	private String nodeInstanceId;
	private String nodeTId;
	private String userId;
	private EVENT_TYPE eventType;
	
	
	
}
