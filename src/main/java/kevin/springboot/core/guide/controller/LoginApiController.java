package kevin.springboot.core.guide.controller;

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
@RequestMapping("/login")
public class LoginApiController {
    private final LoginService loginService;

    @PostMapping("/sign-up")
    public boolean signUp(@RequestBody @Valid SignUpRequest request) {
        return loginService.signUp(request);
    }

    @PostMapping
    public LoginResponse logIn(@RequestBody @Valid LoginRequest request) {
        return loginService.logIn(request);
    }
}
