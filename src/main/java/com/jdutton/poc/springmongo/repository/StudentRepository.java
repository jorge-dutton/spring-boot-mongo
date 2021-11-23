package com.jdutton.poc.springmongo.repository;

import com.jdutton.poc.springmongo.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByEmail(String email);
}
