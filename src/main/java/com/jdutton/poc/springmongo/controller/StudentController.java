package com.jdutton.poc.springmongo.controller;

import com.jdutton.poc.springmongo.entity.Student;
import com.jdutton.poc.springmongo.model.StudentDto;
import com.jdutton.poc.springmongo.service.StudentService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudents() {
        return studentService.fetchAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable("id") String id) {
        return studentService.fetchStudent(id);
    }

    @PostMapping
    public String insertStudent(@RequestBody StudentDto studentDto) {
        return studentService.insertStudent(studentDto);
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable String id, @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(id, studentDto);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable("id") String id) {
        return studentService.deleteStudent(id);
    }
}
