package kevin.springboot.core.guide.dto;

import kevin.springboot.core.guide.entity.User;
import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String password;
    private String name;

    public SignUpRequest() {
    }

    public SignUpRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
