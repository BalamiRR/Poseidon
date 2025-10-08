package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "FullName is mandatory")
    private String full_name;

    @NotBlank(message = "Role is mandatory")
    private String role = "USER";
}