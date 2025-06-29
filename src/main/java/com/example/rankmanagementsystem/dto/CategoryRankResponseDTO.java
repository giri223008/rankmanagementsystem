package com.example.rankmanagementsystem.dto;

import com.example.rankmanagementsystem.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryRankResponseDTO {
    private int categoryRank;
    private Long rollNo;
    private String schoolCode;

    public CategoryRankResponseDTO(Student student) {
        this.categoryRank = student.getCategoryRank();
        this.rollNo = student.getRollNo();
        this.schoolCode = student.getSchoolCode();
    }
}
