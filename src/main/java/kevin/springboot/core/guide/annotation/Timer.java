package kevin.springboot.core.guide.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Timer : 메소드 실행시간을 측정할 AOP 어노테이션
 */

@Target({ElementType.METHOD}) // 어노테이션이 적용될 대상 설정 (TYPE : 메소드)
@Retention(RetentionPolicy.RUNTIME) // 어노테이션의 지속시간 설정 RUNTIME: 서버 구동후에도 적용됨.
public @interface Timer {
}
