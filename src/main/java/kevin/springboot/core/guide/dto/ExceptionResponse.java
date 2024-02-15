package kevin.springboot.core.guide.dto;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private int code;
    private String errorType;
    private String message;

    public ExceptionResponse(int code, String errorType, String message) {
        this.code = code;
        this.errorType = errorType;
        this.message = message;
    }
}
