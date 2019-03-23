package morning.meta_controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "编码接口", tags = "编码管理")
@RestController
@RequestMapping(value="/code")
public class MetaController {
	
	@ApiOperation(value = "获取代码", notes = "")
    @RequestMapping(value = {"/getCode/{templateId}"}, method = RequestMethod.GET)
	public String getCode(@PathVariable String templateId) {
	    return templateId;
	}
	

}
