package com.example.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerTest {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Test
    @Order(1)
    void shouldSaveStudent() {
        Student student = new Student();
        student.setName("Charlie");
        student.setAddress("Algeria");
        
        studentRepository.save(student);
        
        assertThat(studentRepository.count()).isEqualTo(1);
    }
    
    @Test
    @Order(2)
    void shouldFindAllStudents() {
        List<Student> students = studentRepository.findAll();
        
        assertThat(students).hasSize(1);
        assertThat(students.get(0).getName()).isEqualTo("Charlie");
    }
}