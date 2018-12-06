package telran.ashkelon2018.student.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import telran.ashkelon2018.student.domain.Student;

@Repository
public class StudentRepositoryMapImpl implements StudentRepository {
	Map<Integer, Student> students = new ConcurrentHashMap<>();

	@Override
	public boolean addStudent(Student student) {
		return students.putIfAbsent(student.getId(),
				student) == null;
	}

	@Override
	public Student removeStudent(int id) {
		return students.remove(id);
	}

	@Override
	public Student findStudentById(int id) {
		return students.get(id);
	}

	@Override
	public Student editStudent(Student student) {
		students.replace(student.getId(), student);
		return findStudentById(student.getId());
	}

}