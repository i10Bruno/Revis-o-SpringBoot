package com.revisao.revisao.controller;


import com.revisao.revisao.Mapper.UserMapper;
import com.revisao.revisao.request.UserPostRequest;
import com.revisao.revisao.request.UserPutRequest;
import com.revisao.revisao.response.UserGetResponse;
import com.revisao.revisao.response.UserPostResponse;
import com.revisao.revisao.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserMapper mapper;
    private final UserService service;


    @GetMapping
    public ResponseEntity<List<UserGetResponse>> listAll(@RequestParam(name = "firstName", required = false) String name){
        var user = service.findAll(name);
        var userGetResponseList=mapper.toUserGetResponseList(user);

        return ResponseEntity.ok(userGetResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> FindByid(@PathVariable Long id){

        var user=service.findByid(id);
        var userGetResponse=mapper.toUserGetResponse(user);
        return ResponseEntity.ok(userGetResponse);
    }

    @PostMapping
    public ResponseEntity<UserPostResponse> save(@RequestBody UserPostRequest user){
        var users = mapper.toUserPostrequest(user);
        var saves= service.save(users);
        var response=mapper.toUserPostResponse(saves);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletebyid(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();

    }

    @PutMapping

    public ResponseEntity<Void> update(@RequestBody UserPutRequest user){
        var users=mapper.toUserPut(user);
         service.update(users);
        return ResponseEntity.noContent().build();

    }

}
