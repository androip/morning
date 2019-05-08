package morning.entity.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormInstance {

	private String formTid;
	private String formInstanceId;
	private String nodeInstanceId;
	private String processInstanceId;
	private String formType;
	private String formName;
}
