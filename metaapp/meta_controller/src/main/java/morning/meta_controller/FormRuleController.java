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
import morining.dto.rule.FormTransformRuleDto;
import morining.exception.RuleException;
import morning.service.impl.MetaServiceImpl;

@Api(value = "单据转换管理API接口", tags = "单据转换管理API接口")
@RestController
@RequestMapping(value="/meta/rule")
public class FormRuleController {

	@Autowired
	private MetaServiceImpl metaService;
	
	@ApiOperation(value = "创建单据转换规则", notes = "")
	@RequestMapping(value = {""}, method = RequestMethod.POST)
	public void createFromRule(@RequestBody FormTransformRuleDto dto) {
		metaService.createFromRule(dto);
	}
	
	@ApiOperation(value = "创建单据转换规则", notes = "")
	@RequestMapping(value = {"/{ruleId}"}, method = RequestMethod.GET)
	public @ResponseBody FormTransformRuleDto getFromRule(@PathVariable String ruleId) throws RuleException {
		return metaService.getFromRuleById(ruleId);
	}
	
}
