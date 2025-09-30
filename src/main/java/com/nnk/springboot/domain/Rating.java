package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @NotBlank(message = "MoodysRating is mandatory")
    private String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    private String sandPRating;

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
