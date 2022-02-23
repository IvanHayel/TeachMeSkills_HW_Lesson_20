package by.teachmeskills.task.service.impl;

import by.teachmeskills.task.dao.StudentDao;
import by.teachmeskills.task.model.student.Student;
import by.teachmeskills.task.service.StudentService;
import by.teachmeskills.task.transaction.EntityTransaction;
import lombok.NonNull;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.util.List;

@Log
public record StudentServiceImpl(Connection connection) implements StudentService {
    public StudentServiceImpl(@NonNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addStudent(@NonNull Student student) {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        boolean transactionSuccess = studentDao.create(student);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully added.", student));
        } else {
            transaction.rollback();
            log.info(String.format("Adding %s denied.", student));
        }
        transaction.end();
        return transactionSuccess;
    }

    @Override
    public List<Student> getStudentList() {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        List<Student> studentList = studentDao.findAll();
        boolean transactionSuccess = !studentList.isEmpty();
        if (transactionSuccess) {
            transaction.commit();
            log.info("All students found successfully.");
        } else {
            transaction.rollback();
            log.info("No students found.");
        }
        transaction.end();
        return studentList;
    }

    @Override
    public Student getStudentById(@NonNull Integer id) {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        Student desiredStudent = studentDao.findEntityById(id);
        boolean transactionSuccess = desiredStudent != null;
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("Student with id = %d found successfully.", id));
        } else {
            transaction.rollback();
            log.info(String.format("There is no students with id = %d.", id));
        }
        transaction.end();
        return desiredStudent;
    }

    @Override
    public Student updateStudent(@NonNull Student student) {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        Student updatedStudent = studentDao.update(student);
        boolean transactionSuccess = updatedStudent != null;
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully updated.", student));
        } else {
            transaction.rollback();
            log.info(String.format("Update of %s denied.", student));
        }
        transaction.end();
        return updatedStudent;
    }

    @Override
    public boolean deleteStudent(@NonNull Student student) {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        boolean transactionSuccess = studentDao.delete(student);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("%s successfully deleted.", student));
        } else {
            transaction.rollback();
            log.info(String.format("Delete of %s denied.", student));
        }
        transaction.end();
        return transactionSuccess;
    }

    @Override
    public boolean deleteStudentById(@NonNull Integer id) {
        StudentDao studentDao = new StudentDao();
        EntityTransaction transaction = new EntityTransaction(connection);
        transaction.begin(studentDao);
        boolean transactionSuccess = studentDao.deleteEntityById(id);
        if (transactionSuccess) {
            transaction.commit();
            log.info(String.format("Delete of student with id = %d successfully ended.", id));
        } else {
            transaction.rollback();
            log.info(String.format("Delete of student with id = %d denied.", id));
        }
        transaction.end();
        return transactionSuccess;
    }
}