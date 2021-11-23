package com.jdutton.poc.springmongo;

import com.jdutton.poc.springmongo.entity.Address;
import com.jdutton.poc.springmongo.entity.Gender;
import com.jdutton.poc.springmongo.entity.Student;
import com.jdutton.poc.springmongo.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringMongoPocApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SpringMongoPocApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringMongoPocApplication.class, args);
	}

	@Bean
	CommandLineRunner runner (StudentRepository studentRepository,
							  MongoTemplate mongoTemplate) {
		return args -> {
			var address = new Address("Spain","28000","Madrid");
			var subjects = List.of("Peppa Pig","Patrulla Canina", "Musizon");

			var student = Student.builder()
					.firstName("Marta")
					.lastName("Dutton")
					.email("chupi@ttt.com")
					.gender(Gender.FEMALE)
					.address(address)
					.createdAt(LocalDateTime.now())
					.favouriteSubjects(subjects)
					.totalSpentInBooks(BigDecimal.valueOf(30.25)).build();


			//usingMongoTemplate(studentRepository, mongoTemplate, student);

			studentRepository.findByEmail(student.getEmail())
					.ifPresentOrElse( s -> {
						LOG.warn("Email {} already exists in DB", student.getEmail());
					}, () -> {
						LOG.info("Inserting record or email {}", student.getEmail());
						studentRepository.insert(student);
					});

		};
	}

	private void usingMongoTemplate(StudentRepository studentRepository,
									MongoTemplate mongoTemplate,
									Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(student.getEmail()));
		var students = mongoTemplate.find(query, Student.class);

		if(!students.isEmpty()) {
			throw new IllegalStateException(String.format("Email %s already exists in DB", student.getEmail()));
		}

		studentRepository.insert(student);
	}
}
