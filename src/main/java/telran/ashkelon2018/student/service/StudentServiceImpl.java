package telran.ashkelon2018.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.student.dao.StudentRepository;
import telran.ashkelon2018.student.domain.Student;
import telran.ashkelon2018.student.dto.ScoreDto;
import telran.ashkelon2018.student.dto.StudentDto;
import telran.ashkelon2018.student.dto.StudentEditDto;
import telran.ashkelon2018.student.dto.StudentResponseDto;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository studentRepository;

	@Override
	public boolean addStudent(StudentDto studentDto) {
		Student student = new Student(studentDto.getId(),
				studentDto.getName(), studentDto.getPassword());
		return studentRepository.addStudent(student);
	}

	@Override
	public StudentResponseDto deleteStudent(int id) {
		return convertToStudentResponseDto(studentRepository
				.removeStudent(id));
	}

	private StudentResponseDto convertToStudentResponseDto(Student student) {
		
		return StudentResponseDto.builder()
				.id(student.getId())
				.name(student.getName())
				.scores(student.getScores())
				.build();
	}

	@Override
	public StudentDto editStudent(int id, StudentEditDto studentEditDto) {
		Student student = studentRepository.
				findStudentById(id);
		if (studentEditDto.getName() != null) {
			student.setName(studentEditDto.getName());
		}
		if (studentEditDto.getPassword() != null) {
			student.setPassword(studentEditDto.getPassword());
		}
		studentRepository.editStudent(student);
		return convertToStudentDto(student);
	}

	private StudentDto convertToStudentDto(Student student) {
		return StudentDto.builder()
				.id(student.getId())
				.name(student.getName())
				.password(student.getPassword())
				.build();
	}

	@Override
	public StudentResponseDto getStudent(int id) {
		return convertToStudentResponseDto(studentRepository
				.findStudentById(id));
	}

	@Override
	public boolean addScore(int id, ScoreDto scoreDto) {
		Student student = studentRepository.findStudentById(id);
		boolean res = student.addScore(scoreDto.getExamName(),
				scoreDto.getScore());
		studentRepository.editStudent(student);
		return res;
	}

}
