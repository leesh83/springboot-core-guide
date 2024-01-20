package kevin.springboot.core.guide.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class MemberDto {
    @Schema(description = "이름", example = "kevin")
    private String name;

    @Schema(description = "이메일", example = "kevin@naver.com")
    private String email;

    @Schema(description = "등록시간")
    private LocalDateTime createDateTime;


    @Builder
    public MemberDto(String name, String email, LocalDateTime createDateTime) {
        this.name = name;
        this.email = email;
        this.createDateTime = createDateTime;
    }
}
