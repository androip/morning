package morning.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.event.Event;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProcessStartEvent extends Event{

	private String time;
	
}
