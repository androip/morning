package morning.meta_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import morining.dto.proc.ProcessTemplateDTO;
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
	
	@ApiOperation(value = "更新流程模版", notes = "")
	@RequestMapping(value = {"/processtemplate"}, method = RequestMethod.PUT)
	public void updateProcessTemplate(@RequestBody ProcessTemplateDTO dto) {
		metaService.saveProcessTemplate(dto);
	}
	
	
	@ApiOperation(value = "根据流程模版ID，删除流程模版", notes = "")
	@RequestMapping(value = {"/processtemplate/{processtemplateId}"}, method = RequestMethod.DELETE)
	public void deleteProcessTemplate(@PathVariable String processtemplateId) {
		metaService.delete(processtemplateId);
	}
	
	@ApiOperation(value = "获取多个模版", notes = "")
	@RequestMapping(value = {"/processtemplate"}, method = RequestMethod.GET)
	public @ResponseBody List<ProcessTemplateDTO> getProcessTemplateList(@RequestParam(required=false) Integer start,@RequestParam(required=false) Integer size) {
		return metaService.getProcessTemplateList((start==null)?0:start,(size==null)?10:size);
	}
}
