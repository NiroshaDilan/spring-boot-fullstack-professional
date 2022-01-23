package com.example.demo.student;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @Project spring-boot-fullstack-professional
 * @Author DILAN on 1/22/2022
 */
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;


    // after each test table will delete
    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentExistsEmail() {
        // Give
        String email = "jamila@gmail.com";
        Student student = new Student("Jamila", email, Gender.FEMALE);
        underTest.save(student);
        // When
        Boolean expected = underTest.selectExistsEmail(email);
        // Then
        AssertionsForClassTypes.assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        // Give
        String email = "jamila@gmail.com";
        // When
        Boolean expected = underTest.selectExistsEmail(email);
        // Then
        AssertionsForClassTypes.assertThat(expected).isFalse();
    }
}