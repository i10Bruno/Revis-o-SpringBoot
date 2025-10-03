package com.revisao.revisao.controller;

import com.revisao.revisao.Mapper.UserMapperImpl;
import com.revisao.revisao.commons.FileUtils;
import com.revisao.revisao.commons.UserUtils;
import com.revisao.revisao.domain.User;
import com.revisao.revisao.repository.UserData;
import com.revisao.revisao.repository.UserHardCodedRepository;
import com.revisao.revisao.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Import({UserMapperImpl.class, UserService.class, UserHardCodedRepository.class,UserData.class})


@ComponentScan("com.revisao.revisao")


class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoSpyBean
    private UserHardCodedRepository repository;


    @MockitoBean
    private UserData userData;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private UserUtils userUtils;

    private List<User> UserList;

    @BeforeEach
    void init() {

        UserList = userUtils.newUserList();
    }
    @Test
    @DisplayName("GET v1/user return a list whith all user when arguments is null")
    @Order(1)
    void findAll_ReturnsAllUser_WhenArgumentsIsNull() throws Exception {

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);


        String response = fileUtils.readResourceFile("user/get-user-null-name-200.json");


        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).
                andExpect(content().json(response));;

    }
    @Test
    @DisplayName(" GET v1/User?param=r9 findAll returns List with found object when name exists")
    @Order(2)
    void findAll_ReturnsFoundUserInList_WhenNameIsFound() throws Exception {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        String response = fileUtils.readResourceFile("user/get-user-r9-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user").param("firstName","ronaldo"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));




    }


}