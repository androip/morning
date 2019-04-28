package morning.service.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.event.Event;
import morning.vo.TASK_STATUS;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessStartEvent extends Event{


	private String msgBody;
	private List<String> toNodeTid;
	private List<String> toNodeInsId;
}
