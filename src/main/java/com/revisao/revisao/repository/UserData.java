package com.revisao.revisao.repository;

import com.revisao.revisao.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserData {
    //Você não pode fazer a referência da lista (minhaLista) apontar para uma nova lista.
private final List<User> USERS = new ArrayList<>();

    {
        var neymar = User.builder().id(1L).firstName("neymar").lastName("jr").email("neymarjr@gmail.com").build();
        var messi = User.builder().id(2L).firstName("leo").lastName("messi").email("leomessigoat@gmail.com").build();
        var cristiano = User.builder().id(3L).firstName("cristiano").lastName("ronaldo").email("eusouomillior@gmail.com").build();
        USERS.addAll(List.of(neymar,messi,cristiano));

    }

    public List<User> getUSERS() {
        return USERS;
    }
}
