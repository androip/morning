package morning.entity.process.node;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.entity.process.node.form.FormProperty;
import morning.entity.process.node.form.FormStructure;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeTemplate implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2772781300961702607L;
	protected String nodeTemplateId;
	protected String nodeTemplateName;
	protected String nodeTemplateType;
	protected List<FormProperty> formProeties;
	private FormStructure fromStructure;

}
