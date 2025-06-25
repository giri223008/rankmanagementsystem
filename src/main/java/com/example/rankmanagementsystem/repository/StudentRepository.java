package com.example.rankmanagementsystem.repository;

import com.example.rankmanagementsystem.enums.Category;
import com.example.rankmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    //List<Student> findByCategoryOrderByCategoryRankAsc(Category category);
    List<Student> findAllByCategoryOrderByCategoryRankAsc(Category category);
    List<Student> findAllByOrderByGeneralRankAsc();
}
