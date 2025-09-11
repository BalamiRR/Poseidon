package com.nnk.springboot.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Input has to be text !")
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
            message = "Password must contain at least one uppercase letter, one number, and one symbol !" )
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Input has to be text !")
    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname", nullable = false)
    private String fullName;

    @NotBlank(message = "Role is mandatory")
    private String role;


}
