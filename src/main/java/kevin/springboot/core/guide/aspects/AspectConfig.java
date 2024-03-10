package kevin.springboot.core.guide.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 로깅 AOP 구성
 */
@Aspect
@Component
@Slf4j
public class AspectConfig {

    // @UserActionLog 어노테이션이 붙은 메소드에 적용됨.
    @Pointcut("@annotation(kevin.springboot.core.guide.annotation.UserActionLog)")
    private void userActionLog() {
    }

    // @Timer 어노테이션이 붙은 메소드에 적용됨.
    @Pointcut("@annotation(kevin.springboot.core.guide.annotation.Timer)")
    private void timer() {
    }

    //@Before 메소드 실행 전 실행
    @Before("userActionLog()") //userActionLog() pointcut 적용
    public void before(JoinPoint joinPoint) {
        log.info("AOP - Before: {}", joinPoint.getSignature().getName()); // 메소드명
        for (Object arg : joinPoint.getArgs()) {
            log.info("joinpoint arg : {}", arg); // 컨트롤러 메소드의 매개변수값들이 toString 으로 출력된다. User, RequestDTO 등
        }

        //aop 에서 request 정보 조회 가능하다.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("joinpoint request : {}, {}, {}", request.getMethod(), request.getRequestURI(), request.getRequestURL());
        // GET, /product/10, http://localhost:8080/product/10

    }

    //@After 메소드 실행 후 실행
    @After("userActionLog()") //userActionLog() pointcut 적용
    public void after(JoinPoint joinPoint) {
        log.info("AOP - After: {}", joinPoint.getSignature().getName()); // 메소드명

        for (Object arg : joinPoint.getArgs()) {
            log.info("joinpoint arg : {}", arg);
        }
    }

    //@Around 메소드 실행 전,후 실행
    @Around("timer()") //timer() pointcut 적용
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object returnObj = proceedingJoinPoint.proceed();
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        log.info("AOP - around : {}, 실행시간 : {} ms", proceedingJoinPoint.getSignature().getName(), duration);
        return returnObj; // return obj 를 해줘야 api response가 정상 응답된다.
    }

    //@AfterReturning 메소드 예외없이 정상 실행 + 반환 완료 후 실행
    @AfterReturning(value = "userActionLog()", returning = "returnObj") //userActionLog() pointcut 적용
    public void afterReturning(JoinPoint joinPoint, Object returnObj) {
        log.info("AOP - AfterReturning: {}", joinPoint.getSignature().getName()); // 메소드명

        for (Object arg : joinPoint.getArgs()) {
            log.info("joinpoint arg : {}", arg);
        }

        //API의 response 를 확인할 수 있다.
        log.info("returnObj : {}", returnObj.toString());
    }

    //@AfterReturning 메소드에서 예외 발생 시 실행.
    //매개변수 (Exception e) 적용시 계속 구동 에러가 발생해서 활성화 하지 못함. 컨트롤러 exception 핸들링은 @ControllerAdvice + @ExceptionHandler 통해서 관리하므로 이건 필요없을듯.
//    @AfterThrowing("userActionLog()") //userActionLog() pointcut 적용
//    public void afterThrowing(JoinPoint joinPoint, Exception e) {
//        log.info("LoggingAspect - afterThrowing: {}, exception: {}", joinPoint.getSignature().getName()); // 메소드명, exception 명
//
//        for(Object arg : joinPoint.getArgs()){
//            log.info("joinpoint arg : {}" , arg);
//        }
//    }

}
