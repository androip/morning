package morining.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import morining.dto.proc.ProcessTemplateDTO;
import morining.dto.proc.node.NodeTemplateDto;
import morining.dto.rule.FormTransformRuleDto;

@FeignClient(value = "meta-server")
public interface IProcessMetaService {

	@RequestMapping(value = {"/meta/processtemplate/{processTemplateId}"}, method = RequestMethod.GET)
	public @ResponseBody ProcessTemplateDTO getProcessTemplate(@PathVariable(value="processTemplateId") String processTemplateId);

	@RequestMapping(value = {"/meta/processtemplates"}, method = RequestMethod.GET)
	public @ResponseBody List<ProcessTemplateDTO> getProcessTemplateList(@RequestParam(required=false,value="start") Integer start,
			 															@RequestParam(required=false,value="size") Integer size);

	@RequestMapping(value = {"/meta/processtemplate"}, method = RequestMethod.GET)
	public @ResponseBody ProcessTemplateDTO getProcessTemplateByNodeTid(@RequestParam(value="nodeTemplateId")String nodeTemplateId);

	@RequestMapping(value = {"/meta/processtemplate/{processTid}/nodetmpl/{nodeTid}"}, method = RequestMethod.GET)
	public @ResponseBody NodeTemplateDto getNodeTemplateListByNodeTid(@PathVariable(value="processTid") String processTid,
														@PathVariable(value="nodeTid") String nodeTid);

	@RequestMapping(value = {"/meta/rule/{ruleId}"}, method = RequestMethod.GET)
	public FormTransformRuleDto getFormRuleById(@PathVariable(value="ruleId") String ruleId);
}
