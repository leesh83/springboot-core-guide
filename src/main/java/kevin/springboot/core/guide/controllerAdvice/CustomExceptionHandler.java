package kevin.springboot.core.guide.controllerAdvice;


import jakarta.servlet.http.HttpServletRequest;
import kevin.springboot.core.guide.dto.ExceptionResponse;
import kevin.springboot.core.guide.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kevin.springboot.core.guide.controller")
@Slf4j
public class CustomExceptionHandler {

    //CustomException 과 하위클래스 예외들을 캐치하여 응답을 만든다.
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ExceptionResponse> handleCustomException(CustomException e, HttpServletRequest request){
        log.error("advice 내 handleCustomException 호출 url : {}, exception : {},  message : {}", request.getRequestURI(), e.toString(), e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }

    //lombok @validation exception handler
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        log.error("advice 내 handleMethodArgumentNotValidException 호출 url : {}, exception : {}, message : {}", request.getRequestURI(), e.toString(), e.getMessage());
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(),
                e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }
}
