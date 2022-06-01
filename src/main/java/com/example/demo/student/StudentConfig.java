package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepo repo) 
    {
        return args -> {
            Student maria = new Student("Maria", "maria.yuoko@gmail.com", LocalDate.of(2000, Month.FEBRUARY, 5));
            Student alex = new Student("Alex", "alex.garcia@gmail.com", LocalDate.of(2002, Month.JUNE, 8));

            repo.saveAll(List.of(maria, alex));
        };
    }
}
