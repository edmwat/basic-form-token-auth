package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	
	public static final List<Student> STUDENTS = Arrays.asList(
			new Student(1L, "James Bond"),
			new Student(2L, "Maria Jones"),
			new Student(3L,"anna smith")
			);
	
	@GetMapping(path="{studentId}")
	public Student getStudent(@PathVariable("studentId") Long studentId) {
		return  STUDENTS.stream()
				.filter(student -> studentId.equals(student.getStudentId()))
				.findFirst()
				.orElseThrow(()->new IllegalStateException("Student "+studentId));
	}

}
