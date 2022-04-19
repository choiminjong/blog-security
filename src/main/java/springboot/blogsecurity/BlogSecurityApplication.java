package springboot.blogsecurity;

import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import springboot.blogsecurity.model.entity.User;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


@EnableJpaAuditing
@SpringBootApplication
public class BlogSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogSecurityApplication.class, args);
	}

	@Bean
	//JPA의 Audit 기능
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	//AuditorAware 인터페이스 구현 클래스
	public class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		@NonNull
		public Optional<String> getCurrentAuditor() {
			return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
		}
	}
}
