package morning.meta_controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
	
	@ApiOperation(value = "分页获所有个模版", notes = "")
	@RequestMapping(value = {"/processtemplates"}, method = RequestMethod.GET)
	public @ResponseBody List<ProcessTemplateDTO> getProcessTemplateList(@RequestParam(required=false) Integer start,@RequestParam(required=false) Integer size) {
		return metaService.getProcessTemplateList((start==null)?0:start,(size==null)?10:size);
	}
	
	
	@ApiOperation(value = "根据条件获多个模版", notes = "")
	@RequestMapping(value = {"/processtemplatelist"}, method = RequestMethod.GET)
	public @ResponseBody List<ProcessTemplateDTO> getProcessTemplateListBy(HttpServletRequest request) {
		Map<String,Object> requestMap = getParams(request);
		return metaService.getProcessTemplateLByCondition(requestMap);
	}
	
	private Map<String,Object> getParams(HttpServletRequest request) {  
        Map<String,Object> map = new HashMap<String,Object>();  
        Enumeration paramNames = request.getParameterNames();  
        while (paramNames.hasMoreElements()) {  
            String paramName = (String) paramNames.nextElement();  

            String[] paramValues = request.getParameterValues(paramName);  
            if (paramValues.length >0) {  
                String paramValue = paramValues[0];  
                if (paramValue.length() != 0) {  
                    map.put(paramName, paramValue);  
                }  
            }  
        }  
        return map;
    }

}
