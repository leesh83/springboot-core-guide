package kevin.springboot.core.guide.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kevin.springboot.core.guide.dto.LoginRequest;
import kevin.springboot.core.guide.dto.LoginResponse;
import kevin.springboot.core.guide.dto.SignUpRequest;
import kevin.springboot.core.guide.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원가입, 로그인 API", description = "회원가입, 로그인 API")
@RequestMapping("/login")
public class LoginApiController {
    private final LoginService loginService;

    @Operation(summary = "회원가입")
    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody @Valid SignUpRequest request) {
        return loginService.signUp(request);
    }

    @Operation(summary = "로그인(토큰 발급)")
    @PostMapping
    public LoginResponse logIn(@RequestBody @Valid LoginRequest request) {
        return loginService.logIn(request);
    }
}
