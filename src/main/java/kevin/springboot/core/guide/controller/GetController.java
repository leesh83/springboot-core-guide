package kevin.springboot.core.guide.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    @GetMapping("/name")
    public String getName(){
        return "kevin";
    }

    //@PathVariable
    @GetMapping("/path-variable/{variable}")
    public String getVariable(@PathVariable String variable){
        return variable;
    }

    //@RequestParam
    @GetMapping("/request-param")
    public String getRequestParam(@RequestParam String name,
                                  @RequestParam String email){
        return name + ", " + email;
    }


}
