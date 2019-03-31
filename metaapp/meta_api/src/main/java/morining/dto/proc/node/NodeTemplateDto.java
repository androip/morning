package morining.dto.proc.node;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.dto.proc.node.form.FormPropertyDto;
import morining.dto.proc.node.form.FormStructureDto;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeTemplateDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3836503776666113261L;
	
	protected String nodeTemplateId;
	protected String nodeTemplateName;
	protected String nodeTemplateType;
	protected FormPropertyDto formProeties;
	private FormStructureDto fromStructure;

	

}
