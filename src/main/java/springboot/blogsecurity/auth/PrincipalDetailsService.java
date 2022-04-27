package springboot.blogsecurity.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springboot.blogsecurity.model.entity.User;
import springboot.blogsecurity.repository.UserRepository;

//http://localhost:8080/login  요청시 동작합니다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService 의  loadUserByUsername ");
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다." + username);
                });

        return new PrincipalDetails(principal);
    }
}
