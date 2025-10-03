package com.revisao.revisao.service;

import com.revisao.revisao.domain.User;
import com.revisao.revisao.repository.UserHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserHardCodedRepository repository;


    public List<User>findAll(String name){
        return name == null ? repository.findAll():repository.findByName(name);
    }

    public User findByid(Long id){

       return repository.findByid(id).orElseThrow(()->  new  ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

    }

    public User save(User user){
        return repository.save(user);

    }
    public void delete(Long id) {

        var user = findByid(id);
        repository.delete(user);

    }
    public  void update(User userpudate){
        var user = findByid(userpudate.getId());

        repository.save(userpudate);


    }


}
