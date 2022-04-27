package springboot.blogsecurity.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Filter 3 = " );

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰 : id:pw 정상적으로 들어와 로그인이 완료되면 토큰을 만들어주고 그걸 응답합니다.
        // 요청할 떄 마다 header의 Authorization에 value 값으로 토큰을 가지고 오면
        // 그때 넘겨받은 토큰이 문제가 없는지 검증을 진행합니다. ( RSA,HS256)
         if(req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth = " + headerAuth);

            if(headerAuth.equals("hello")){
                chain.doFilter(req,res);
            }else{
                PrintWriter out = response.getWriter();
                out.print("not Allow");
            }
        }

        chain.doFilter(request,response);
    }
}
