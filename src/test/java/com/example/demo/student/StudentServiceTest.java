package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @Project spring-boot-fullstack-professional
 * @Author DILAN on 1/22/2022
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);

        // when
        underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        AssertionsForClassTypes.assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWenEmailIsTaken() {
        // given
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);

        // when
        BDDMockito.given(studentRepository.selectExistsEmail(student.getEmail()))
                        .willReturn(true);

       AssertionsForClassTypes.assertThatThrownBy(() -> underTest.addStudent(student))
               .isInstanceOf(BadRequestException.class)
               .hasMessageContaining("Email " + student.getEmail() + " taken");

       verify(studentRepository, never()).save(any());
    }


    @Test
    @Disabled
    void deleteStudent() {
    }
}