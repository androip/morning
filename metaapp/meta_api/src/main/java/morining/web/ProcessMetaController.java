package morining.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import morining.api.IProcessMetaService;
import morining.dto.proc.ProcessTemplateDTO;

@RestController
public class ProcessMetaController {
	
	@Autowired
	IProcessMetaService processMetaService;
	
	@RequestMapping(value = "/meta/process",method = RequestMethod.GET)
    public @ResponseBody ProcessTemplateDTO getEntity(@PathVariable String id){
        return processMetaService.getProcessTemplate(id);
    }
	
}
