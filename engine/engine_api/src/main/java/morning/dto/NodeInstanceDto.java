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
public class NodeInstanceDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -849217651521149178L;
	private String nodeInsId;
	private String nodeTemplateId;
	private String processInsId;
	private String nodeName;
	private String nodeType;
	private Integer nodeStatus;
	private List<FormInstancDto> formInsDtoList;
	
	public Object getFieldValueByFKey(String condkey) {
		for(FormInstancDto form:formInsDtoList) {
			List<FormFieldInstance> fieldList = form.getFormFieldInstanceList();
			for(FormFieldInstance field:fieldList) {
				if(field.getFkey().equals(condkey)) {
					return field.getFval();
				}
			}
		}
		return null;
	}
	
}
