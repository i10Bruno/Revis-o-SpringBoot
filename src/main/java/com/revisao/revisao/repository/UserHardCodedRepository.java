package com.revisao.revisao.repository;

import com.revisao.revisao.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//construtor para final
@RequiredArgsConstructor
public class UserHardCodedRepository {

    private final UserData userData;



    public List<User> findAll(){

        return userData.getUSERS();
    }

    public Optional<User> findByid(Long id){

        return userData.getUSERS().stream().filter(user -> user.getId().equals(id)).findFirst();
    }
    public List<User> findByName(String name){

        return userData.getUSERS().stream().filter(user -> user.getFirstName().equalsIgnoreCase(name)).toList();
    }
    public User save( User user){
        userData.getUSERS().add(user);
        return user;
    }
    public void delete( User user){
        userData.getUSERS().remove(user);
    }
    public void update(User user) {
        delete(user);
        save(user);

    }





}
