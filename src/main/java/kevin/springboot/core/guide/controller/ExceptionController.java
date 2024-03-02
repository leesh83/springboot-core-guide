package kevin.springboot.core.guide.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
@Hidden // swagger 문서에 표시하지 않는다.
public class ExceptionController {

    @GetMapping("/runtime-exception")
    public void goRuntimeException(){
        throw new RuntimeException();
    }
}
