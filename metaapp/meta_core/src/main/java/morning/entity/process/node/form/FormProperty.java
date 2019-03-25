package morning.entity.process.node.form;

import java.io.Serializable;

import morning.entity.process.node.form.filed.FiledProperty;

public class FormProperty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7981351158519301023L;
	
	private String formTemplateId;
	private String formName;
	private FORMTYPE formType;
	private FiledProperty filedProperties;
	
	
	

}
