package com.example.demo.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepo studentRepository;

    @Autowired
    public StudentService(StudentRepo studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents()
	{
		return studentRepository.findAll();
	}

    public void addNewStudent(Student student)
    {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail()); // we put students email into search whether its a duplicate
        if(studentOptional.isPresent())     // if it is, it gets put into the optional and the isPresent() method only checks whether there is a value or not
        {
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);    // this saves the student into the database
    }

    public void deleteStudent(Long studentId)
    {   
        boolean exists = studentRepository.existsById(studentId);

        if(!exists)
        {
            throw new IllegalStateException("student by the ID " + studentId + " does not exist");
        }else
        {
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email)
    {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("student by the ID " + studentId + " does not exist"));

        if(name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) // checking if the name we got is not null, equal to the one already written 
        {
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(student.getName(), name))
        {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent())
            {
                throw new IllegalStateException("email taken");
            }else
            {
                student.setEmail(email);
            }
        }
    }
}
