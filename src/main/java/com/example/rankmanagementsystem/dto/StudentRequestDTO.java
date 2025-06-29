package com.example.rankmanagementsystem.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.*;
import com.example.rankmanagementsystem.enums.Category;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentRequestDTO {

    @NotNull
    private Long rollNo;

    @NotBlank
    private String name;

    @Pattern(regexp = "^[A-Z]{3}\\d{3}$")
    private String schoolCode;

    @DecimalMin("52.5")
    @DecimalMax("200.0")
    private double cutoff;

    @NotNull
    private Category category;
}
