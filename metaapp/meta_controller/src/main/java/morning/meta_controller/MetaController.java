package morning.meta_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import morining.dto.TestEntityDTO;
import morining.dto.proc.ProcessTemplateDTO;
import morning.entity.TestEntity;
import morning.service.impl.MetaServiceImpl;

@Api(value = "Meta管理API接口", tags = "Meta管理API接口")
@RestController
@RequestMapping(value="/meta")
public class MetaController {

	
	@Autowired
	private MetaServiceImpl metaService;
	
	@ApiOperation(value = "根据流程模版ID，获取流程模版", notes = "")
	@RequestMapping(value = {"/processtemplate/{processTemplateId}"}, method = RequestMethod.GET)
	public @ResponseBody ProcessTemplateDTO getProcessTemplate(@PathVariable String processTemplateId) {
		ProcessTemplateDTO dto  = metaService.getProcessTemplateById(processTemplateId);
		return dto;
	}
	
	@ApiOperation(value = "创建流程模版", notes = "")
	@RequestMapping(value = {"/processtemplate"}, method = RequestMethod.POST)
	public void createProcessTemplate(@RequestBody ProcessTemplateDTO dto) {
		metaService.saveProcessTemplate(dto);
	}
	
	
	@ApiOperation(value = "根据流程模版ID，删除流程模版", notes = "")
	@RequestMapping(value = {"/processtemplate/{processtemplateId}"}, method = RequestMethod.DELETE)
	public void deleteProcessTemplate(@PathVariable String processtemplateId) {
		metaService.delete(processtemplateId);
	}
	
	
	
	
	@ApiOperation(value = "创建测试对象", notes = "测试用")
	@RequestMapping(value = {"/test"}, method = RequestMethod.POST)
	public void createTestEntity(@RequestBody TestEntity entity) {
		metaService.createTestEntity(entity);
	}
	
	@ApiOperation(value = "查询测试对象", notes = "测试用")
	@RequestMapping(value = {"/test/{Id}"}, method = RequestMethod.GET)
	public @ResponseBody TestEntityDTO getTestEntity(@PathVariable String Id) {
		TestEntityDTO dto = metaService.getEntityDTO(Id);
		return dto;
	}
	
	
	
}
