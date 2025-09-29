package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Term is mandatory")
    private Integer curveId;

    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Term is mandatory")
    private Double term;

    @Min(value = 0L, message = "The value must be positive")
    @NotNull(message = "Value is mandatory")
    private Double value;

    private LocalDateTime asOfDate;
    private LocalDateTime creationDate;

    public CurvePoint(Integer curveId, Double term, Double value ) {
        this.term = term;
        this.value = value;
        this.curveId = curveId;
    }
}
