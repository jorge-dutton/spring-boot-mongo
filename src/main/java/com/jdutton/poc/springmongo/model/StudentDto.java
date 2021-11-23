package com.jdutton.poc.springmongo.model;

import com.jdutton.poc.springmongo.entity.Address;
import com.jdutton.poc.springmongo.entity.Gender;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class StudentDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private Address address;
    private LocalDateTime createdAt;
    private List<String> favouriteSubjects;
    private BigDecimal totalSpentInBooks;
}
