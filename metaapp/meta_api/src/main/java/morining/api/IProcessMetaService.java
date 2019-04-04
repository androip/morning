package morining.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import morining.dto.TestEntityDTO;
import morining.dto.proc.ProcessTemplateDTO;

@FeignClient(value = "meta-server")
public interface IProcessMetaService {

	 @RequestMapping(value = {"/meta/processtemplate/{processTemplateId}"}, method = RequestMethod.GET)
	 public @ResponseBody ProcessTemplateDTO getProcessTemplate(@PathVariable String processTemplateId);
}
