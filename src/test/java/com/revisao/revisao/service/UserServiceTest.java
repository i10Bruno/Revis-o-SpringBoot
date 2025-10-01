package com.revisao.revisao.service;

import com.revisao.revisao.commons.UserUtils;
import com.revisao.revisao.domain.User;
import com.revisao.revisao.repository.UserHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
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

    @Test
    @DisplayName("findall return a list whith all Users when arguments is null")
    @Order(1)
    void findAll_ReturnsAllUser_WhenArgumentsIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(UserList);
        var users = service.findAll(null);

        org.assertj.core.api.Assertions.assertThat(users).isNotNull().hasSameElementsAs(UserList);

    }
    @Test
    @DisplayName("findALL returns List with found object when name exists")
    @Order(2)
    void findByname_ReturnsFoundUserInList_WhenNameIsFound() {
        var expected=UserList.getFirst();
        var UserExpected = Collections.singletonList(expected);
        BDDMockito.when(repository.findByName(expected.getFirstName())).thenReturn(UserExpected);
        var userFound=service.findAll(expected.getFirstName());
        org.assertj.core.api.Assertions.assertThat(userFound).containsAll(UserExpected);

    }








}