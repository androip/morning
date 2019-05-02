package morning.service.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.event.EVENT_TYPE;
import morning.event.Event;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskSubmitEvent extends Event {

	private String msgBody;
	private List<String> toNodeTids;
	private List<String> toNodeInsIds;
	
	public TaskSubmitEvent(String msgBody, List<String> toNodeTids, List<String> toNodeInsIds, EVENT_TYPE type) {
		this.msgBody = msgBody;
		this.toNodeTids = toNodeTids;
		this.toNodeInsIds = toNodeInsIds;
		super.setEventType(type);
	}

}
