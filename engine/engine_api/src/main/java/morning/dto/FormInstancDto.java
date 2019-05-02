package morning.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.vo.FormFieldInstance;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormInstancDto implements Serializable{
	private static final long serialVersionUID = -1146854553431468177L;

	private String formTid;
	private String formInsid;
	private String formType;
	private String formName;
	private List<FormFieldInstance> formFieldInstanceList;
	
	public void setFormFieldInstance() {
		for(FormFieldInstance field : this.formFieldInstanceList) {
			field.setFormInstanceId(this.getFormInsid());
		}
		
	}
	
}
