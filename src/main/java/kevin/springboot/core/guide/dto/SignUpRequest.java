package kevin.springboot.core.guide.dto;

import jakarta.validation.constraints.NotBlank;
import kevin.springboot.core.guide.entity.User;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @NotBlank(message = "email 은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "password 은 필수 입력 값 입니다.")
    private String password;

    @NotBlank(message = "name 은 필수 입력 값 입니다.")
    private String name;

    public SignUpRequest() {
    }

    public SignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
