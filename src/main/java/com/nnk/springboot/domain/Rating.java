package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "FitchRating is mandatory")
    private String fitchRating;

    @Digits(integer = 20, fraction = 0)
    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Numbers has to be present")
    private Integer orderNumber;

    public Rating(){

    }

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
