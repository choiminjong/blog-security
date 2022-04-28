package springboot.blogsecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springboot.blogsecurity.model.entity.User;
import springboot.blogsecurity.repository.UserRepository;

@RestController
public class RestApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }

    @PostMapping("/home")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("/auth/join")
    public String join(@RequestBody User user){
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword());
        System.out.println("email = " + user.getEmail());

        user.setPassword((passwordEncoder.encode(user.getPassword())));
        user.setRoles("ROLE_USER");

        userRepository.save(user);
        return "화원가입이 완료되었습니다.";
    }


}
