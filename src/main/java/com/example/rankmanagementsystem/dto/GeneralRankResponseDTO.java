package com.example.rankmanagementsystem.dto;

import com.example.rankmanagementsystem.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralRankResponseDTO {
    private int generalRank;
    private Long rollNo;
    private String schoolCode;

    public GeneralRankResponseDTO(Student student) {
        this.generalRank = student.getGeneralRank();
        this.rollNo = student.getRollNo();
        this.schoolCode = student.getSchoolCode();
    }
}
