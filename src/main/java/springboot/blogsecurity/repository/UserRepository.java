package springboot.blogsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.blogsecurity.model.entity.User;

import java.util.Optional;

// @Repository 어노테이션이 없어도 IoC 처리가 가능합니다.
// JpaRepository 상속해서 사용하기때문에 자동 Bean등록이 됩니다.
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
