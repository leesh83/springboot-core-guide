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

    //dto 타입으로 requestParam 데이터를 매핑하는것도 가능하다.
    @GetMapping("/request-dto")
    public String getRequestDto(MemberDto dto){
        return dto.toString();
    }

}
