package morining.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import morining.api.ITestEntityService;
import morining.dto.TestEntityDTO;

@RestController
public class TestEntityApiController {

	@Autowired
	ITestEntityService testEntityService;
    
	@RequestMapping(value = "/api/{id}",method = RequestMethod.GET)
    public @ResponseBody TestEntityDTO getEntity(@PathVariable String id){
        return testEntityService.sayHiFromClientOne(id);
    }

	
	
}
