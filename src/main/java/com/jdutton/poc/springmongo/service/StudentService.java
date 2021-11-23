package com.jdutton.poc.springmongo.service;

import com.jdutton.poc.springmongo.model.StudentDto;

import java.util.List;

public interface StudentService {
    List<StudentDto> fetchAllStudents();
    StudentDto fetchStudent(String email);
    String insertStudent(StudentDto studentDto);
    String updateStudent(String id, StudentDto studentDto);
    String deleteStudent(String id);
}
