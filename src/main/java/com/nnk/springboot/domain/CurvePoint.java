package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "CurvePoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Id")
    private Integer id;

    @Min(value = 0, message = "The value must be positive")
    @NotNull(message = "Term is mandatory")
    private Integer curveId;

    @NotNull(message = "Term is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Term must be positive")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @DecimalMin(value = "0.0", inclusive = true, message = "Value must be positive")
    private Double value;

    private LocalDateTime asOfDate;
    private LocalDateTime creationDate;

    public CurvePoint(){

    }

    public CurvePoint(Integer curveId, Double term, Double value ) {
        this.term = term;
        this.value = value;
        this.curveId = curveId;
    }
}
