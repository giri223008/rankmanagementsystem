package com.example.rankmanagementsystem.model;

import com.example.rankmanagementsystem.enums.Category;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "student")
public class Student {

    @Id
    private Long rollNo;

    private String name;

    @Pattern(regexp = "^[A-Z]{3}\\d{3}$", message = "Invalid school code")
    private String schoolCode;

    @DecimalMin(value = "52.5", inclusive = true)
    @DecimalMax(value = "200.0", inclusive = true)
    private Double cutoff;

    @Enumerated(EnumType.STRING)
    private Category category; //

    private int generalRank;
    private int categoryRank;
}