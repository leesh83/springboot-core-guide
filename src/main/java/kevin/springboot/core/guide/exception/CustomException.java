package kevin.springboot.core.guide.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
