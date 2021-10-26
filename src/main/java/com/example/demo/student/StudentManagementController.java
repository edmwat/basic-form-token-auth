package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {
	
	public static final List<Student> STUDENTS = Arrays.asList(
			new Student(1L, "James Bond"),
			new Student(2L, "Maria Jones"),
			new Student(3L,"anna smith")
			);
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','ADMINTRAINEE')")
	public List<Student> getAllStudents(){
		return STUDENTS;
	}
	@PostMapping
	@PreAuthorize("hasAuthority('student:write')")
	public void registerNewStudents(@RequestBody Student student) {
		System.out.println("register");
	}
	@DeleteMapping(path ="{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Long studentId) {
		System.out.println("delete a student");
	}
	@PutMapping(path ="{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") long studentId,@RequestBody Student student) {
		System.out.println("update %s"+ studentId);
	}

}
