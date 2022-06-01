package com.example.demo.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

    @Query("SELECT s from Student s WHERE s.email = ?1")    // not clean sql used, which is the code looks a little different. "Student" here is our class, as we specified @Entity 
    Optional<Student> findStudentByEmail(String email);

}
