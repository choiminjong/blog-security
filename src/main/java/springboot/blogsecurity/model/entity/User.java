package springboot.blogsecurity.model.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String roles; //USER,ADMIN
    private String oauth;
    
    /*
    private Role roles; //USER,ADMIN
    Role 모델을 만들어서 처리할 수 있지만 간단한 프로젝트를 진행하기위해 User 테이블에서만 처리하는 방식으로 진행
    */

    public List<String> getRolesList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>(); //null 방지
    }
}