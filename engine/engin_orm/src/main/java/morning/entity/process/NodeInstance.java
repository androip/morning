package morning.entity.process;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.dto.FormInstancDto;
import morning.util.IdGenerator;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NodeInstance {

	private String nodeInsId;
	private String nodeTemplateId;
	private String processInsId;
	private String nodeName;
	private String nodeType;
	private Integer nodeStatus;
	private String userId;
	
	public List<FormInstance> createFormInstance(List<FormInstancDto> formProperties) {
		List<FormInstance> formInsList = new ArrayList<FormInstance>();
		for(FormInstancDto fromDto : formProperties) {
			String formInsId = IdGenerator.generatFormId();
			fromDto.setFormInsid(formInsId);
			FormInstance formIns = new FormInstance(fromDto.getFormTid(),
					formInsId,
					this.nodeInsId,
					this.processInsId,
					fromDto.getFormType(),
					fromDto.getFormName());
			formInsList.add(formIns);
			
		}
		return formInsList;
	
	}
}













