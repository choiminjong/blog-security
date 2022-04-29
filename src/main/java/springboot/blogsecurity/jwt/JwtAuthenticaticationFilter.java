package springboot.blogsecurity.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springboot.blogsecurity.auth.PrincipalDetails;
import springboot.blogsecurity.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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
        try {
//
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null){
//                System.out.println("input = " + input);
//            }
            ObjectMapper om = new ObjectMapper();
            User user =om.readValue(request.getInputStream(),User.class);
            System.out.println(user);

            //2.정상인지 로그를 시도합니다. authenticationManager 로그인 시도를 합니다.
            //authenticationToken 로그인 한 정보가 담긴다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //강제 로그인 시도
            //해당 함수가 실행이되면  PrincipalDetailsService의 loadUserByUsername() 함수가 실행됩니다.
             Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);

            //3.authentication 객체가 session 영역에 저장됨 -> 로그인이 되었다는 뜻입니다.
            //getter setter 설정이 없어서 오류 표시됨. 오류 내용 체크
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println("principalDetails.getUser().getUsername() = " + principalDetails.getUser().getUsername());
            //authentication 객체가 session 영역에 저장 후 return 합니다.
            //리턴하는 이유는 권한 관리를 security 대신 해주기 때문에 편리용도로 사용합니다.
            //JWT 토큰을 사용하면서 세션을 만들 이유가 없습니다. 단지 권한 처리때문에 session 넣어서 사용합니다.

            //4.JWT 토큰을 만들어서 응답한다.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행됩니다.
    //JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을  response 합니다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행됨 : 인증이 완료되었습니다.");

        //PrincipalDetails Session  인증정보를 불러온다.
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        //RSA 방식이 아닌 HMAC512 방식 입니다.
        String jwtToken = JWT.create()
                .withSubject("nexon")
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
    }
}
