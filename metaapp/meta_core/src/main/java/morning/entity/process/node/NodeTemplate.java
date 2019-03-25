package morning.entity.process.node;

import java.io.Serializable;

import morning.entity.process.node.form.FormProperty;
import morning.entity.process.node.form.FormStructure;

public class NodeTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2772781300961702607L;
	
	protected String nodeTemplateId;
	protected String nodeTemplateName;
	protected String nodeTemplateType;
	protected FormProperty formProeties;
	private FormStructure fromStructure;

}
