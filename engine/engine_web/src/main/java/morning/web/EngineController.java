package morning.web;

import java.util.List;
import java.util.Map;

import morning.vo.FormFieldInstance;
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
	public String  getProcessTemplate(@RequestParam String processTemplateId,@RequestParam String userId) throws DBException {
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

	@ApiOperation(value = "查询表单信息/加载字段", notes = "K：表单实例ID，V：表单字段信息。此时节点状态为Running")
	@RequestMapping(value = {"/procins/nodeins/{nodeInsId}"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, List<FormFieldInstance>> getRunningFrom(@PathVariable String nodeInsId) throws DBException {
		return engineService.getNodeIns(nodeInsId);
	}
	
	@ApiOperation(value = "查询表单信息/根据单据转换规则加载字段", notes = "K：表单TID，V：表单字段信息。此时节点状态为Ready")
	@RequestMapping(value = {"/form"}, method = RequestMethod.GET)
	public @ResponseBody Map<String, Map<String,Object>> getReadyForm(@RequestParam String processTId ,@RequestParam String nodeTid,@RequestParam String procInsId) throws DBException {
		return engineService.getReadyForm(processTId,nodeTid,procInsId);
	}
	
	@ApiOperation(value = "保存表单", notes = "")
	@RequestMapping(value = {"/procins/nodeins"}, method = RequestMethod.PUT)
	public  void  saveForm(@RequestBody NodeInstanceDto nodeInstanceDto,@RequestParam String userId) throws DBException {
		engineService.saveForm(nodeInstanceDto,userId);
	}
	
	
}
















