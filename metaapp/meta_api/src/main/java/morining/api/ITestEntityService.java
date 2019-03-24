package morining.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import morining.dto.TestEntityDTO;

@FeignClient(value = "meta-server")
public interface ITestEntityService {
	 @RequestMapping(value = "/meta/test/{id}",method = RequestMethod.GET)
	 @ResponseBody TestEntityDTO sayHiFromClientOne(@PathVariable(value="id") String id);
}
