package com.revisao.revisao.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
//@builder simplifica  a criação de obj em java
@Builder

@Getter
@Setter
//comparar obj pelos atributos  e não pela memoria
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class User {
    @EqualsAndHashCode.Include
    private Long id;
    // A anotação mapeia o campo 'firstName' para 'first_name' no JSON
    @JsonProperty("fist_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;




}
