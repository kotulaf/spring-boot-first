package com.example.demo.student;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter 
@Setter
@Entity
@Table
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private long id;
    private String name;
    private String email;
    private LocalDate dob;
    @Transient
    private int age;

    public Student(long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public int getAge()
    {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    @Override 
    public String toString()
    {
        return "Student{" + 
                "id= " + id + 
                ", name='" + name + "\'" + 
                ", email='" + email + "\'" + 
                ", dob =" + dob + 
                ", age=" + age + 
                '}';
    }
}
