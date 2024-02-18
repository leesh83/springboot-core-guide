package kevin.springboot.core.guide.exception;


public class DuplicatedEmailException extends RuntimeException {

    private static final String message = "이미 등록된 이메일 입니다.";

    public DuplicatedEmailException() {
        super(message);
    }
}
