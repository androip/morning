package morning.web;

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
import morning.dto.NodeInstanceDto;
import morning.dto.TaskOverviewDto;
import morning.exception.DBException;
import morning.service.impl.EngineService;

@Api(value = "Engin管理API接口")
@RestController
@RequestMapping(value="/engin")
public class EngineController {

	@Autowired
	private EngineService engineService;
	
	@ApiOperation(value = "指定流程模版，实例化一个流程", notes = "")
	@RequestMapping(value = {"/procins"}, method = RequestMethod.POST)
	public String  getProcessTemplate(@RequestParam String processTemplateId,@RequestParam String userId) {
		String processId = engineService.startProcess(processTemplateId,userId);
		return processId;
	}
	
	@ApiOperation(value = "根据用户查询任务一览", notes = "")
	@RequestMapping(value = {"/procins/tasks"}, method = RequestMethod.GET)
	public @ResponseBody List<TaskOverviewDto>  getTaskOverviewList(@RequestParam String userId) {
		List<TaskOverviewDto>  dtolist = engineService.getTaskOverviewList(userId);
		return dtolist;
	}
	
	
	@ApiOperation(value = "提交表单", notes = "")
	@RequestMapping(value = {"/procins/nodeins"}, method = RequestMethod.POST)
	public void  submitForm(@RequestBody NodeInstanceDto nodeInstanceDto,@RequestParam String userId) throws DBException {
		engineService.submitForm(nodeInstanceDto,userId);
	}
	
	@ApiOperation(value = "如果节点状态为Running（已经创建），取节点", notes = "")
	@RequestMapping(value = {"/procins/nodeins/{instancessId}"}, method = RequestMethod.GET)
	public void  getNdoeInstance(@PathVariable String instancessId,@RequestParam String userId) throws DBException {
		//TODO
	}
	
	@ApiOperation(value = "保存表单", notes = "")
	@RequestMapping(value = {"/procins/nodeins"}, method = RequestMethod.PUT)
	public @ResponseBody NodeInstanceDto  getTaskOverviewList(@RequestBody NodeInstanceDto nodeInstanceDto,@RequestParam String userId) {
		NodeInstanceDto  dto = engineService.saveForm(nodeInstanceDto,userId);
		return dto;
	}
	
}
















