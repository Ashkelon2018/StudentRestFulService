package telran.ashkelon2018.student.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.ashkelon2018.student.dto.ScoreDto;
import telran.ashkelon2018.student.dto.StudentDto;
import telran.ashkelon2018.student.dto.StudentEditDto;
import telran.ashkelon2018.student.dto.StudentResponseDto;
import telran.ashkelon2018.student.service.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	ObjectMapper mapper;
	
	@PostMapping("/student")
	public boolean addStudent(@RequestBody StudentDto studentDto) {
		return studentService.addStudent(studentDto);
	}
	
	@DeleteMapping("/student/{id}")
	public StudentResponseDto removeStudent(@PathVariable int id,
			@RequestHeader("Authorization") String token) {
		return studentService.deleteStudent(id, token);
	}
	
	@PutMapping("/student/{id}")
	public StudentDto updateStudent(@PathVariable int id, 
			@RequestBody StudentEditDto studentEditDto, 
			HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("Authorization");
		//StudentEditDto studentEditDto = getBody(request);
		StudentDto res = studentService.editStudent(id, studentEditDto, token);
		//response.setStatus(418);
		return res;
	}
	
	private StudentEditDto getBody(HttpServletRequest request) {
		StringBuilder json = new StringBuilder("");
		try {
			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(request.getInputStream()));
			String str = reader.readLine();
			while(str != null && !str.isEmpty()) {
				json.append(str);
				str = reader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			return mapper.readValue(json.toString(), StudentEditDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	@GetMapping("/student/{id}")
	public StudentResponseDto getStudent(@PathVariable int id) {
		return studentService.getStudent(id);
	}
	
	@PutMapping("/teacher/{id}")
	public boolean addScore(@PathVariable int id,
			@RequestBody ScoreDto scoreDto) {
		return studentService.addScore(id, scoreDto);
	}

}
