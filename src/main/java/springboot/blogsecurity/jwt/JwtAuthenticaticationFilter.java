package springboot.blogsecurity.jwt;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 존재합니다.
// login 요청해서  username,password 전송하면(post)
// UsernamePasswordAuthenticationFilter 동작합니다.
public class JwtAuthenticaticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticaticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //login 요청을 하면 로그인 하기위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("JwtAuthenticaticationFilter : 로그인 시도중 ");

        //1.username, password 데이터 받음

        //2.정상인지 로그를 시도합니다. authenticationManager로 로그인 시도를 합니다.
        //PrincipalDetailsService의 loadUserByUsername() 함수가 실행됩니다.

        //3.PrincipalDetail를 세션에 담는다.

        //4.JWT 토큰을 만들어서 응답한다.
        return super.attemptAuthentication(request, response);
    }
}
