package com.revisao.revisao.service;

import com.revisao.revisao.commons.UserUtils;
import com.revisao.revisao.domain.User;
import com.revisao.revisao.repository.UserHardCodedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {


    @Mock
    private UserHardCodedRepository repository;

    @InjectMocks
    private  UserService service;

    @InjectMocks
    private UserUtils userUtils;


    private List<User> UserList;

    @BeforeEach
    void init() {

        UserList = userUtils.newUserList();
    }




}