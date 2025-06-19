package com.example.rankmanagementsystem.controller;

import com.example.rankmanagementsystem.service.StudentService;
import com.example.rankmanagementsystem.enums.Category;
import com.example.rankmanagementsystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/local_data")
public class StudentController {

    @Autowired
    private StudentService service;

    @PutMapping("/store")
    public boolean store(@RequestBody List<Student> students) {
        return service.storeStudents(students);
    }

    @PostMapping("/modify")
    public boolean modify(@RequestBody Map<String, String> data) {
        Long rollNo = Long.parseLong(data.get("rollNo"));
        return service.modifyStudent(rollNo, data.get("name"), data.get("schoolCode"));
    }

    @GetMapping("/category/{category}")
    public List<Map<String, Object>> getByCategory(@PathVariable Category category) {
        return service.getByCategory(category).stream()
                .map(s -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("categoryRank", s.getCategoryRank());
                    map.put("rollNo", s.getRollNo());
                    map.put("schoolCode", s.getSchoolCode());
                    return map;
                }).toList();
    }

    @GetMapping("/category/gendral")
    public List<Map<String, Object>> getGeneralRankList() {
        return service.getGeneralRankList().stream()
                .map(s -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("generalRank", s.getGeneralRank());
                    map.put("rollNo", s.getRollNo());
                    map.put("schoolCode", s.getSchoolCode());
                    return map;
                }).toList();
    }

    @GetMapping("/details")
    public Student getDetails(@RequestHeader("rollNo") Long rollNo) {
        return service.getDetails(rollNo);
    }

    @DeleteMapping("/clear")
    public void clear() {
        service.clearAll();
    }
}

