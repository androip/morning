package morning.service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.event.EVENT_TYPE;
import morining.event.Event;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormRuleEvent extends Event {
	private String ruleId;
	private String srcFormTid;
    private String desFormTId;
    
    public FormRuleEvent(String transformId, String srcFormTid, String desFormTId, EVENT_TYPE type,
    		String processTid) {
    		this.ruleId = transformId;
    		this.srcFormTid = srcFormTid;
    		this.desFormTId = desFormTId;
    		this.setEventType(type);
    		this.setProcessTId(processTid);
    }

}
