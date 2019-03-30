package morning.service.api;

import morining.dto.proc.ProcessTemplateDTO;

public interface IMetaService {

	ProcessTemplateDTO getProcessTemplateById(String templateId);

}
