package kevin.springboot.core.guide.exception;


public class UserNotFoundException extends CustomException {

    private static final String message = "해당 유저가 없습니다. email : ";

    public UserNotFoundException(String email) {
        super(message + email);
    }
}
