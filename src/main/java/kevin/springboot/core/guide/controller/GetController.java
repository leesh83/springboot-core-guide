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
    public String getRequestParam(@Parameter(description = "이름", example = "kevin") @RequestParam String name,
                                  @Parameter(description = "이메일", example = "kevin@naver.com") @RequestParam String email) {
        return name + ", " + email;
    }

    //GET 요청의 requestParam 데이터를 DTO 매핑하는것도 가능하다.
    @Operation(summary = "dto 타입으로 매핑받고 리턴하기 api")
    @GetMapping("/request-dto")
    public MemberDto getRequestDto(MemberDto dto) {
        return dto;
    }

}
