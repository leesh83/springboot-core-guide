package kevin.springboot.core.guide.controller;

import kevin.springboot.core.guide.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    @GetMapping("/name")
    public String getName(){
        return "kevin";
    }


    @GetMapping("/path-variable/{variable}")
    public String getVariable(@PathVariable String variable){
        return variable;
    }

    @GetMapping("/request-param")
    public String getRequestParam(@RequestParam String name,
                                  @RequestParam String email){
        return name + ", " + email;
    }

    @GetMapping("/request-dto")
    public String getRequestDto(MemberDto dto){
        return dto.toString();
    }

}
