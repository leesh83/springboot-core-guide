package kevin.springboot.core.guide.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kevin.springboot.core.guide.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SignUpRequest {
    @NotBlank(message = "email 은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "password 은 필수 입력 값 입니다.")
    private String password;

    @NotBlank(message = "name 은 필수 입력 값 입니다.")
    private String name;

    @NotNull(message = "roles 은 필수 입력 값 입니다.")
    private List<UserRole> roles;

    public SignUpRequest() {
    }

    @Builder
    public SignUpRequest(String email, String password, String name, List<UserRole> roles) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }


}
