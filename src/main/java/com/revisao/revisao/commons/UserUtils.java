package com.revisao.revisao.commons;

import com.revisao.revisao.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtils {
    public List<User> newUserList(){
        var ronaldo= User.builder().id(1L).firstName("ronaldo").lastName("fenomeno").email("r9@gmail.com").build();
        var suarez = User.builder().id(2L).firstName("luiz").lastName("suarez").email("suarez@gmail.com").build();
        var romario = User.builder().id(3L).firstName("romario").lastName("faria").email("baixola@gmail.com").build();
        return new ArrayList<>(List.of(ronaldo,suarez,romario));

    }

    public User newUserToSave(){

        return User.builder().id(1L).firstName("jo").lastName("so").email("jo@gmail.com").build();
    }

}
