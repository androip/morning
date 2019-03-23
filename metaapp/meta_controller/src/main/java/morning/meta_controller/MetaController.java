package morning.meta_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import morning.dto.ProcessTemplateDTO;
import morning.service.impl.MetaServiceImpl;

@Api(value = "编码接口", tags = "编码管理")
@RestController
@RequestMapping(value="/code")
public class MetaController {

	
	@Autowired
	private MetaServiceImpl metaService;
	
	@ApiOperation(value = "根据流程模版ID，获取流程模版", notes = "")
	@RequestMapping(value = {"/meta/{processTemplateId}"}, method = RequestMethod.GET)
	public ProcessTemplateDTO getProcessTemplate(@PathVariable String templateId) {
		ProcessTemplateDTO dto  = metaService.getProcessTemplateById(templateId);
		return dto;
	}
	

}
