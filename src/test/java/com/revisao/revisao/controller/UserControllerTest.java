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
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @Test
    @DisplayName(" GET v1/user?name=x findALL returns Empty list when name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenNameIsNotFound() throws Exception {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        String response = fileUtils.readResourceFile("user/get-user-x-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user").param("firstName","x"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));



    }
    @Test
    @DisplayName("GET v1/user/1 findByid returns a user with given id")
    @Order(4)
    void findByid_returnsUserById_WhenSucceful() throws Exception {

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var id=UserList.getFirst().getId();
        String response = fileUtils.readResourceFile("user/get-user-by-id-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/{id}",id))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andExpect(content().json(response));


    }
    @Test
    @DisplayName("GET v1/user/99   throws ResponseStatusException 404 when user is not found")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenUserIsNotFound() throws Exception {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/user/{id}",id))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isNotFound()).andExpect(status().reason("User not found"));



    }
    @Test
    @DisplayName("POST v1/user  creates a producer")
    @Order(6)
    void Save_CreatesUser_WhenSuccesful() throws Exception {

        var request = fileUtils.readResourceFile("user/post-request-user-200.json");
        var response = fileUtils.readResourceFile("user/post-response-user-201.json");
        var userTosave=userUtils.newUserToSave();
        BDDMockito.when(repository.save(ArgumentMatchers.any())).thenReturn(userTosave);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/user").content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());


    }
    @Test
    @DisplayName("PUT v1/user updates a user")
    @Order(7)
    void update_updatesUser_WhenSuccesful() throws Exception {
        var request = fileUtils.readResourceFile("user/put-request-user-200.json");

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user").content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("PUT v1/user throws ResponseStatusException when user is not found")
    @Order(8)
    void update_ThrowsResponseStatusException_whenUserIsNotFound() throws Exception {

        var request = fileUtils.readResourceFile("user/put-request-user-404.json");

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/user")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found")); // Mensagem de erro esperada
    }
    @Test
    @DisplayName("DELETE v1/user/{id} removes a user when successful")
    @Order(9)
    void delete_removesUser_whenSuccessful() throws Exception {

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);

        var id = UserList.getFirst().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent()); // Espera 204 No Content para sucesso
    }
    @Test
    @DisplayName("DELETE v1/user/{id} throws ResponseStatusException when user is not found")
    @Order(10)
    void delete_ThrowsResponseStatusException_whenUserIsNotFound() throws Exception {

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);


        var id = 99L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/user/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found")); // Mensagem de erro esperada
    }




}