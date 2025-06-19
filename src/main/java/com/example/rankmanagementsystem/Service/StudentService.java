package com.example.rankmanagementsystem.Service;

import com.example.rankmanagementsystem.enums.Category;
import com.example.rankmanagementsystem.model.Student;
import com.example.rankmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public boolean storeStudents(List<Student> students) {
        if (students.stream().map(Student::getRollNo).distinct().count() != students.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate roll numbers");
        }

        repository.deleteAll();

        // Sort by cutoff descending for ranking
        List<Student> sorted = students.stream()
                .sorted(Comparator.comparingDouble(Student::getCutoff).reversed())
                .toList();

        // General rank
        for (int i = 0; i < sorted.size(); i++) {
            sorted.get(i).setGeneralRank(i + 1);
        }

        // Category ranks
        Map<Category, Integer> categoryCounter = new EnumMap<>(Category.class);
        for (Student s : sorted) {
            int rank = categoryCounter.getOrDefault(s.getCategory(), 0) + 1;
            s.setCategoryRank(rank);
            categoryCounter.put(s.getCategory(), rank);
        }

        repository.saveAll(sorted);
        return true;
    }

    public boolean modifyStudent(Long rollNo, String name, String schoolCode) {
        Student s = repository.findById(rollNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        if (!schoolCode.matches("^[A-Z]{3}\\d{3}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid school code");
        }

        s.setName(name);
        s.setSchoolCode(schoolCode);
        repository.save(s);
        return true;
    }

    public List<Student> getByCategory(Category category) {
        return repository.findByCategoryOrderByCategoryRankAsc(category);
    }

    public List<Student> getGeneralRankList() {
        return repository.findAllByOrderByGeneralRankAsc();
    }

    public Student getDetails(Long rollNo) {
        return repository.findById(rollNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public void clearAll() {
        repository.deleteAll();
    }
}
