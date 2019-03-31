package morining.dto.proc.node.form;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morining.dto.proc.node.form.field.FiedPropertyDto;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormPropertyDto implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 971341107525135810L;
	
	private String formTemplateId;
	private String formName;
	private String formType;
	private List<FiedPropertyDto> fieldProperties;

}
