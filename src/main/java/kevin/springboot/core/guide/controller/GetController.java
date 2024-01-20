package kevin.springboot.core.guide.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kevin.springboot.core.guide.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

@Tag(name = "GET 예제 API", description = "GET 예제 API 입니다.")
@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    @Operation(summary = "Get 기본 api")
    @GetMapping("/name")
    public String getName() {
        return "kevin";
    }


    @Operation(summary = "path-variable 로 매핑받기 api")
    @GetMapping("/path-variable/{name}")
    public String getVariable(@PathVariable String name) {
        return name;
    }

    @Operation(summary = "requestParam으로 매핑받기 api")
    @GetMapping("/request-param")
    public String getRequestParam(@Parameter(name = "이름") @RequestParam String name,
                                  @Parameter(name = "이메일") @RequestParam String email) {
        return name + ", " + email;
    }

    //dto 타입으로 requestParam 데이터를 매핑하는것도 가능하다.
    @Operation(summary = "dto 타입으로 매핑받기 api")
    @GetMapping("/request-dto")
    public String getRequestDto(MemberDto dto) {
        return dto.toString();
    }

}
