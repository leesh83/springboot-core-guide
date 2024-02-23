package kevin.springboot.core.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "email은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "password 는 필수 입력 값 입니다.")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
