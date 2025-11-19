package com.example.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentIntegrationTest {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }
    
    @Test
    @Order(1)
    @DisplayName("Test 1: Sauvegarder un étudiant")
    void testSaveStudent() {
        Student student = new Student();
        student.setName("Mohamed");
        student.setAddress("Constantine");
        
        Student savedStudent = studentRepository.save(student);
        
        assertThat(savedStudent.getId()).isNotNull();
        assertThat(savedStudent.getName()).isEqualTo("Mohamed");
        assertThat(savedStudent.getAddress()).isEqualTo("Constantine");
        assertThat(studentRepository.count()).isEqualTo(1);
    }
    
    @Test
    @Order(2)
    @DisplayName("Test 2: Sauvegarder plusieurs étudiants")
    void testSaveMultipleStudents() {
        Student student1 = new Student("Ahmed", "Algiers");
        Student student2 = new Student("Fatima", "Oran");
        Student student3 = new Student("Yacine", "Setif");
        
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        
        List<Student> students = studentRepository.findAll();
        assertThat(students).hasSize(3);
        assertThat(students).extracting(Student::getName)
            .containsExactlyInAnyOrder("Ahmed", "Fatima", "Yacine");
    }
    
    @Test
    @Order(3)
    @DisplayName("Test 3: Trouver un étudiant par ID")
    void testFindStudentById() {
        Student student = new Student("Amina", "Bejaia");
        Student savedStudent = studentRepository.save(student);
        Long studentId = savedStudent.getId();
        
        Optional<Student> foundStudent = studentRepository.findById(studentId);
        
        assertThat(foundStudent).isPresent();
        assertThat(foundStudent.get().getName()).isEqualTo("Amina");
        assertThat(foundStudent.get().getAddress()).isEqualTo("Bejaia");
    }
    
    @Test
    @Order(4)
    @DisplayName("Test 4: Supprimer un étudiant")
    void testDeleteStudent() {
        Student student = new Student("Karim", "Tlemcen");
        Student savedStudent = studentRepository.save(student);
        Long studentId = savedStudent.getId();
        
        studentRepository.deleteById(studentId);
        
        assertThat(studentRepository.findById(studentId)).isEmpty();
        assertThat(studentRepository.count()).isEqualTo(0);
    }
    
    @Test
    @Order(5)
    @DisplayName("Test 5: Mettre à jour un étudiant")
    void testUpdateStudent() {
        Student student = new Student("Salim", "Annaba");
        Student savedStudent = studentRepository.save(student);
        
        savedStudent.setName("Salim Updated");
        savedStudent.setAddress("Constantine");
        Student updatedStudent = studentRepository.save(savedStudent);
        
        assertThat(updatedStudent.getName()).isEqualTo("Salim Updated");
        assertThat(updatedStudent.getAddress()).isEqualTo("Constantine");
        assertThat(studentRepository.count()).isEqualTo(1);
    }
    
    @Test
    @Order(6)
    @DisplayName("Test 6: Vérifier base de données vide")
    void testEmptyDatabase() {
        assertThat(studentRepository.count()).isEqualTo(0);
        assertThat(studentRepository.findAll()).isEmpty();
    }
    
    @Test
    @Order(7)
    @DisplayName("Test 7: Chercher étudiant inexistant")
    void testFindNonExistentStudent() {
        Optional<Student> student = studentRepository.findById(999L);
        assertThat(student).isEmpty();
    }
}