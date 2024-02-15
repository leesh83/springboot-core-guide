package kevin.springboot.core.guide.configuration;


import jakarta.servlet.http.HttpServletRequest;
import kevin.springboot.core.guide.dto.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "kevin.springboot.core.guide.controller")
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRunTimeException(RuntimeException e, HttpServletRequest request){
        log.error("advice 내 handleException 호출 url : {}, exception message : {}", request.getRequestURI(), e.getMessage());

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionResponse response = new ExceptionResponse(httpStatus.value(), httpStatus.getReasonPhrase(), e.getMessage());

        return new ResponseEntity<>(response, new HttpHeaders(), httpStatus);
    }

}
