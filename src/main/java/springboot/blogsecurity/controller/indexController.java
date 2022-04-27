package springboot.blogsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springboot.blogsecurity.model.entity.User;
import springboot.blogsecurity.repository.UserRepository;

@Controller
public class indexController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "/user/join";
    }

//    @PostMapping("/join")
//    public @ResponseBody String join(User user){
//        user.setRole("ROLE_USER");
//        userRepository.save(user); //회원가입잘되지만시큐리티 로그인을 할 수 없습니다.
//        // 패스워드 암호가되지않았기때문에 로그인 할 수 없습니다.
//        return "/user/join";
//    }

    @PostMapping("/join")
    public String join(User user){
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "redirect:/loginForm";
    }

}