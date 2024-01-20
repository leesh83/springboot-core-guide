package kevin.springboot.core.guide.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberDto {
    private String name;
    private String email;
    private String organization;

    @Builder
    public MemberDto(String name, String email, String organization) {
        this.name = name;
        this.email = email;
        this.organization = organization;
    }
}
