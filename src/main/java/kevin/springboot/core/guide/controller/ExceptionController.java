package kevin.springboot.core.guide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @GetMapping("/runtime-exception")
    public void goRuntimeException(){
        throw new RuntimeException();
    }
}
