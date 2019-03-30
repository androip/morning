package morning.entity.process.node.form;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.entity.process.node.form.filed.FieldProperty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormProperty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7981351158519301023L;
	
	private String formTemplateId;
	private String formName;
	private FORMTYPE formType;
	private FieldProperty filedProperties;
	
	
	

}
