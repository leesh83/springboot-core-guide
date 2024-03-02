package kevin.springboot.core.guide.configuration;

import kevin.springboot.core.guide.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * JpaAuditingConfig 에서 AuditorAware<Long> 를 반환하기 위한 구현체
 */
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        //현재 인증객체에서 유저정보를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.empty();
        }
        User user = (User) authentication.getPrincipal();
        return Optional.of(user.getId());
    }
}
