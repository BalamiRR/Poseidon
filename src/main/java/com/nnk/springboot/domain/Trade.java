package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_id")
    private Integer trade_id;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Account is mandatory")
    @Column(name = "account")
    private String account;

    @Pattern(regexp="^[A-Za-z]*$", message = "Input has to be text")
    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Numbers has to be present")
    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "buy_quantity")
    private Double buy_quantity;

    public Trade() {}

    public Trade(String account, String type) {
        this.account = account;
        this.type = type;
    }

    public Trade(String account, String type, Double buy_quantity) {
        this.account = account;
        this.type = type;
        this.buy_quantity = buy_quantity;
    }
}