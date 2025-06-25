package com.example.rankmanagementsystem.controller;

import com.example.rankmanagementsystem.exception.ApiResponse;
import com.example.rankmanagementsystem.service.StudentService;
import com.example.rankmanagementsystem.enums.Category;
import com.example.rankmanagementsystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/local_data")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/store")
    public ResponseEntity<ApiResponse<Void>> storeStudents(@RequestBody List<Student> students) {
        studentService.saveAllStudents(students);
        return ResponseEntity.ok(new ApiResponse<>(true, "Students stored and ranked successfully"));
    }

    @PutMapping("/modify")
    public ResponseEntity<ApiResponse<Void>> updateStudent(@RequestBody Student student) {
        studentService.updateStudentDetails(student);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student updated successfully"));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Student>>> getByCategory(@PathVariable Category category) {
        List<Student> result = studentService.getStudentsByCategory(category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category students fetched successfully", result));
    }

    @GetMapping("/general")
    public ResponseEntity<ApiResponse<List<Student>>> getByGeneralRank() {
        List<Student> result = studentService.getStudentsByGeneralRank();
        return ResponseEntity.ok(new ApiResponse<>(true, "General rank students fetched successfully", result));
    }

    @GetMapping("/details")
    public ResponseEntity<ApiResponse<Student>> getStudentDetails(@RequestHeader("rollNo") Long rollNo) {
        Student student = studentService.getStudentDetails(rollNo);
        return ResponseEntity.ok(new ApiResponse<>(true, "Student details fetched successfully", student));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearAll() {
        studentService.clearAllStudents();
        return ResponseEntity.ok(new ApiResponse<>(true, "All student records cleared"));
    }
}