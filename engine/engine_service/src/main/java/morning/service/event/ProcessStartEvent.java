package morning.service.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.event.EVENT_TYPE;
import morining.event.Event;
import morning.vo.TASK_STATUS;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessStartEvent extends Event{

	private String msgBody;
	private List<String> toNodeTids;
	private List<String> toNodeInsIds;
	public ProcessStartEvent(String msgBody, List<String> toNodeTids, List<String> toNodeInsIds, EVENT_TYPE type) {
		super();
		this.msgBody = msgBody;
		this.toNodeTids = toNodeTids;
		this.toNodeInsIds = toNodeInsIds;
		super.setEventType(type);
	}
}
