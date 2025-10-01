package com.revisao.revisao.service;

import com.revisao.revisao.commons.UserUtils;
import com.revisao.revisao.domain.User;
import com.revisao.revisao.repository.UserHardCodedRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatNoException;
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

    @Test
    @DisplayName("findALL returns Empty list when name is not found")
    @Order(3)
    void findByname_ReturnsEmptyList_WhenNameIsNotFound() {
        var name = "not-found";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var UserFound = service.findAll(name);
        org.assertj.core.api.Assertions.assertThat(UserFound ).isNotNull().isEmpty();

    }


    @Test
    @DisplayName("findByid  throws ResponseStatusException when producer is not found")
    @Order(4)
    void findByID_ReturnsUser_WhenIDIsFound() {
        var expected=UserList.getFirst();
        BDDMockito.when(repository.findByid(expected.getId())).thenReturn(Optional.of(expected));
        var userFound=service.findByid(expected.getId());
        org.assertj.core.api.Assertions.assertThat(userFound).isEqualTo(expected);

    }
    @Test
    @DisplayName("findByid returns  a object when ID exists")
    @Order(5)
    void findByid_ThrowsResponseStatusException_WhenProducerIsNotFound() {
        var expected=UserList.getFirst().getId();
        BDDMockito.when(repository.findByid(expected)).thenReturn(Optional.empty());
        assertThatException()
                .isThrownBy(() -> service.findByid(expected))
                .isInstanceOf(ResponseStatusException.class);

    }
    @Test
    @DisplayName("Save creates a producer")
    @Order(6)
    void Save_CreatesProducer_WhenSuccesful() {
       var saveUser=userUtils.newUserToSave();
       BDDMockito.when(repository.save(saveUser)).thenReturn(saveUser);

       var saved=service.save(saveUser);

        org.assertj.core.api.Assertions.assertThat(saved).isEqualTo(saveUser).hasNoNullFieldsOrProperties();

    }
    @Test
    @DisplayName("delete remove a producer")
    @Order(7)
    void delete_RemoveUser_whenSuccesful() {

        var expectedUser=UserList.getFirst();
        BDDMockito.when(repository.findByid(expectedUser.getId())).thenReturn(Optional.of(expectedUser));
        BDDMockito.doNothing().when(repository).delete(expectedUser);
        assertThatNoException().isThrownBy(() -> service.delete(expectedUser.getId()));


    }
    @Test
    @DisplayName("delete throws ResponseStatusException when user is not found")
    @Order(8)
    void delete_ThrowsResponseStatusException_whenProducerIsNotFound() {
        var userToDelete = UserList.getFirst();
        BDDMockito.when(repository.findByid(userToDelete.getId())).thenReturn(Optional.empty());


        assertThatException()
                .isThrownBy(() -> service.delete(userToDelete.getId()))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("uptate updates a user")
    @Order(9)
    void update_updatesProducer_WhenSuccesful() {
        var userToUpdate = UserList.getFirst();
        userToUpdate.setFirstName("ibra");
        BDDMockito.when(repository.findByid(userToUpdate.getId())).thenReturn(Optional.of(userToUpdate));

        assertThatNoException()
                .isThrownBy(() -> service.update(userToUpdate));


    }
    @Test
    @DisplayName("update throws ResponseStatusException when user is not found")
    @Order(10)
    void update_ThrowsResponseStatusException_whenProducerIsNotFound() {
        var userToUpdate = UserList.getFirst();
        BDDMockito.when(repository.findByid(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThatException()
                .isThrownBy(() -> service.update(userToUpdate))
                .isInstanceOf(ResponseStatusException.class);





    }












}