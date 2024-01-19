package kevin.springboot.core.guide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicApiController {

    @GetMapping
    public String getHello(){
        return "hello";
    }
}
