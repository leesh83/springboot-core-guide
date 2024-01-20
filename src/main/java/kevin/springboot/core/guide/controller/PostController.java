package kevin.springboot.core.guide.controller;

import kevin.springboot.core.guide.dto.MemberDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post-api")
public class PostController {

    //return 타입 String 인 경우는 response header 의 Content-type : text/plain 으로 응답함.
    @PostMapping("/member")
    public String postMemberDto(@RequestBody MemberDto dto){
        return dto.toString();
    }

    //return 타입이 객체인 경우는 response header 의 Content-type : application/json 으로 응답함.
    @PutMapping("/member")
    public MemberDto putMemberDto(@RequestBody MemberDto dto){
        return dto;
    }

    //ResponseEntity 를 사용하여 response의 응답코드를 설정할 수 있다.
    @PostMapping("/response-member")
    public ResponseEntity<MemberDto> postMemberDto2(@RequestBody MemberDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(dto);
    }

}
