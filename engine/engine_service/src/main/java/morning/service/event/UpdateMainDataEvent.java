package morning.service.event;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.event.Event;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateMainDataEvent extends Event{

	private Map<String, Set<String>> relatedFieldMap;//K 表名字 : V Fkeys
	public UpdateMainDataEvent(Map<String, Set<String>> relatedFieldMap, Event event) {
		super(event.getProcessTId(),
				event.getProcessInstanceId(),
				event.getNodeInstanceId(),
				event.getNodeTId(),
				event.getUserId(),
				event.getCreateTime(),
				event.getProcessName(),
				event.getNodeName(),
				event.getEventType());
		this.relatedFieldMap = relatedFieldMap;
	}

}
