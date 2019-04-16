package morning.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import morning.service.impl.EngineService;

@Api(value = "Engin管理API接口")
@RestController
@RequestMapping(value="/engin")
public class EngineController {

	@Autowired
	EngineService engineService;
	
	@ApiOperation(value = "指定流程模版，实例化一个流程", notes = "")
	@RequestMapping(value = {"/procins"}, method = RequestMethod.POST)
	public String  getProcessTemplate(@RequestParam String processTemplateId,@RequestParam String userId) {
		String processId = engineService.startProcess(processTemplateId,userId);
		return processId;
	}
	
}
















