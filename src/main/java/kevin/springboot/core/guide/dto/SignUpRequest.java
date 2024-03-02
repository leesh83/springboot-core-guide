package kevin.springboot.core.guide.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kevin.springboot.core.guide.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class SignUpRequest {
    @Schema(description = "이메일", example = "test@naver.com")
    @NotBlank(message = "email 은 필수 입력 값 입니다.")
    private String email;

    @Schema(description = "패스워드")
    @NotBlank(message = "password 은 필수 입력 값 입니다.")
    private String password;

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "name 은 필수 입력 값 입니다.")
    private String name;

    @Schema(description = "권한")
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
