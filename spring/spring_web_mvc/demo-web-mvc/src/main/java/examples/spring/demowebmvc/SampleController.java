package examples.spring.demowebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

//    @RequestMapping(value = "/hello", headers = "!" + HttpHeaders.FROM)
    @RequestMapping(value = "/hello", params = "name")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
