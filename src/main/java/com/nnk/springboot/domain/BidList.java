package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bidListId;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "Account field is mandatory")
    private String account;

    @Pattern(regexp="^[A-Za-z]+$", message = "Input has to be text")
    @NotBlank(message = "Type field is mandatory")
    private String type;

    @Min(value = 0L, message = "The value must be positive")
    private Double bidQuantity;

    private Double askQuantity;
    private Double bid;
    private Double ask;
    private String benchmark;
    private LocalDateTime bidListDate;
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    private String creationName;
    private LocalDateTime creationDate;
    private String revisionName;
    private LocalDateTime revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;

    public BidList() {
    }

    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

    public void setAccount(String account) {
        this.account = (account == null) ? null : account.trim();
    }

    public void setType(String type) {
        this.type = (type == null) ? null : type.trim();
    }

}