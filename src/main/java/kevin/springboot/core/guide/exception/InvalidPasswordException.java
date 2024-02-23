package kevin.springboot.core.guide.exception;

public class InvalidPasswordException extends CustomException{
    private static final String message = "패스워드가 일치하지 않습니다.";

    public InvalidPasswordException() {
        super(message);
    }
}
