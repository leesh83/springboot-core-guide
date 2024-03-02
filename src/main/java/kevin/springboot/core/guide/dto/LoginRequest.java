package kevin.springboot.core.guide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Schema(description = "이메일", example = "test@naver.com")
    @NotBlank(message = "email은 필수 입력 값 입니다.")
    private String email;

    @Schema(description = "패스워드")
    @NotBlank(message = "password 는 필수 입력 값 입니다.")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
