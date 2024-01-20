package kevin.springboot.core.guide.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloApiController {

    @Hidden //swagger에서 표시하지 않을 api
    @GetMapping
    public String getHello() {
        return "hello";
    }
}
