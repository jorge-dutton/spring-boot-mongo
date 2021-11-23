package com.jdutton.poc.springmongo.service.impl;

import com.jdutton.poc.springmongo.entity.Student;
import com.jdutton.poc.springmongo.model.StudentDto;
import com.jdutton.poc.springmongo.repository.StudentRepository;
import com.jdutton.poc.springmongo.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentDto> fetchAllStudents() {
        return studentRepository.findAll().stream().map( student -> {
            var studentDto = new StudentDto();
            BeanUtils.copyProperties(student, studentDto);
            return studentDto;
        }).collect(Collectors.toList());
    }

    @Override
    public StudentDto fetchStudent(String id) {
        var studentDto = new StudentDto();
        studentRepository.findById(id).ifPresent( student -> {
            BeanUtils.copyProperties(student, studentDto);
        });
        return studentDto;
    }

    @Override
    public String insertStudent(StudentDto studentDto) {
        if (studentDto == null) {
            return null;
        }
        var student = Student.builder()
                .firstName(studentDto.getFirstName())
                .lastName(studentDto.getLastName())
                .email(studentDto.getEmail())
                .gender(studentDto.getGender())
                .address(studentDto.getAddress())
                .favouriteSubjects(studentDto.getFavouriteSubjects())
                .createdAt(LocalDateTime.now())
                .totalSpentInBooks(studentDto.getTotalSpentInBooks())
                .build();
        var insertedStudent = studentRepository.insert(student);
        return insertedStudent.getId();
    }

    @Override
    public String updateStudent(String id, StudentDto studentDto) {
        var currentStudent = studentRepository.findById(id).orElse(null);
        if (currentStudent == null) {
            return null;
        }
        studentDto.setId(id);
        BeanUtils.copyProperties(studentDto, currentStudent);
        return studentRepository.save(currentStudent).getId();
    }


    @Override
    public String deleteStudent(String id) {
        var currentStudent = studentRepository.findById(id).orElse(null);
        if (currentStudent == null) {
            return null;
        }
        studentRepository.delete(currentStudent);
        return currentStudent.getId();
    }
}
