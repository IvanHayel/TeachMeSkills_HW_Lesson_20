package by.teachmeskills.task.service;

import by.teachmeskills.task.model.student.Student;

import java.util.List;

public interface StudentService {
    boolean addStudent(Student student);

    List<Student> getStudentList();

    Student getStudentById(Integer id);

    Student updateStudent(Student student);

    boolean deleteStudent(Student student);

    boolean deleteStudentById(Integer id);
}