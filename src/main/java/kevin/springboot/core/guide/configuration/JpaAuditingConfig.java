package kevin.springboot.core.guide.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // create_at, updated_at 자동 업데이트 위해 추가필요
public class JpaAuditingConfig {
}

