package com.example.rankmanagementsystem.service;

import com.example.rankmanagementsystem.dto.StudentRequestDTO;
import com.example.rankmanagementsystem.enums.Category;
import com.example.rankmanagementsystem.exception.BadRequestException;
import com.example.rankmanagementsystem.exception.ResourceNotFoundException;
import com.example.rankmanagementsystem.model.Student;
import com.example.rankmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public void saveAllStudents(List<StudentRequestDTO> inputList) {
        if (inputList.stream().map(StudentRequestDTO::getRollNo).distinct().count() != inputList.size()) {
            throw new BadRequestException("Duplicate roll numbers found.");
        }

        for (StudentRequestDTO s : inputList) {
            if (!s.getSchoolCode().matches("^[A-Z]{3}\\d{3}$")) {
                throw new BadRequestException("Invalid school code for rollNo: " + s.getRollNo());
            }
            if (s.getCutoff() < 52.5 || s.getCutoff() > 200.0) {
                throw new BadRequestException("Invalid cutoff for rollNo: " + s.getRollNo());
            }
        }

        repository.deleteAll();

        List<Student> students = inputList.stream()
                .map(dto -> Student.builder()
                        .rollNo(dto.getRollNo())
                        .name(dto.getName())
                        .schoolCode(dto.getSchoolCode())
                        .cutoff(dto.getCutoff())
                        .category(dto.getCategory())
                        .build())
                .sorted(Comparator.comparingDouble(Student::getCutoff).reversed())
                .collect(Collectors.toList());

        // Assign general ranks
        IntStream.range(0, students.size())
                .forEach(i -> students.get(i).setGeneralRank(i+1));

        // Assign category ranks
        Map<Category, Integer> categoryCounter = new EnumMap<>(Category.class);
        students.stream().forEachOrdered(s -> {
            int rank = categoryCounter.getOrDefault(s.getCategory(), 0) + 1;
            s.setCategoryRank(rank);
            categoryCounter.put(s.getCategory(), rank);
        });

        repository.saveAll(students);
    }

    public void updateStudentDetails(StudentRequestDTO incoming) {
        Student existing = repository.findById(incoming.getRollNo())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Roll No: " + incoming.getRollNo()));

        if (!incoming.getSchoolCode().matches("^[A-Z]{3}\\d{3}$")) {
            throw new BadRequestException("Invalid school code format.");
        }

        existing.setName(incoming.getName());
        existing.setSchoolCode(incoming.getSchoolCode());

        repository.save(existing);
    }

    public List<Student> getStudentsByCategory(Category category) {
        return repository.findAllByCategoryOrderByCategoryRankAsc(category);
    }

    public List<Student> getStudentsByGeneralRank() {
        return repository.findAllByOrderByGeneralRankAsc();
    }

    public Student getStudentDetails(Long rollNo) {
        return repository.findById(rollNo)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Roll No: " + rollNo));
    }

    public void clearAllStudents() {
        repository.deleteAll();
    }
}
