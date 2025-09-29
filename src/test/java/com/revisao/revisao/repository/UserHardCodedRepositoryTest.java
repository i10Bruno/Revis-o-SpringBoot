package com.revisao.revisao.repository;

import com.revisao.revisao.commons.UserUtils;
import com.revisao.revisao.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserHardCodedRepositoryTest {


    @InjectMocks
    private UserHardCodedRepository repository;

    @Mock
    private UserData userData;

    @InjectMocks
    private UserUtils userUtils;

    private List<User> UserList;

    @BeforeEach
    void init() {

        UserList = userUtils.newUserList();
    }


    @Test
    @DisplayName("findall return a list whith all users")
    @Order(1)

    void findAll_ReturnsAllUsers_WhenSuccesful(){
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var user=repository.findAll();

        org.assertj.core.api.Assertions.assertThat(user).isNotNull().hasSameElementsAs(UserList);

    }
    @Test
    @DisplayName("findByid return an user with given id")
    @Order(2)
    void findByid_ReturnsUserById_WhenSuccesful() {

        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var expected=UserList.getFirst();
        var user=repository.findByid(expected.getId());
        org.assertj.core.api.Assertions.assertThat(user).isPresent().contains(expected);

    }
    @Test
    @DisplayName("findall a User by Name")
    @Order(3)
    void findByName_ReturnsUserByName_WhenSuccesful() {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var expected=UserList.getFirst();
        var user=repository.findByName(expected.getFirstName());
        org.assertj.core.api.Assertions.assertThat(user).hasSize(1).contains(expected);


    }
    @Test
    @DisplayName("findByname returns empty when name is null")
    @Order(4)
    void findByname_ReturnsEmptyList_WhenNameIsNull() {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var user = repository.findByName(null);
        org.assertj.core.api.Assertions.assertThat(user).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("save creates an anime")
    @Order(5)
    void save_CreatesUser_WhenSuccesfull() {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var expected= userUtils.newUserToSave();
        var user =repository.save(expected);
        Assertions.assertThat(user).isEqualTo(expected).hasNoNullFieldsOrProperties();

        var userSavedOptional = repository.findByid(expected.getId());

        Assertions.assertThat(userSavedOptional).isPresent().contains(expected);




    }
    @Test
    @DisplayName("delete removes an anime")
    @Order(6)
    void delete_RemoveAnime_WhenSuccesfull() {
        BDDMockito.when(userData.getUSERS()).thenReturn(UserList);
        var expected=UserList.getFirst();
        repository.delete(expected);
        var users = repository.findAll();
        org.assertj.core.api.Assertions.assertThat(users).isNotEmpty().doesNotContain(expected);


    }


}