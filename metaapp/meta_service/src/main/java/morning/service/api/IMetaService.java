package morning.service.api;

import morning.dto.ProcessTemplateDTO;


public interface IMetaService {

	ProcessTemplateDTO getProcessTemplateById(String templateId);

}
