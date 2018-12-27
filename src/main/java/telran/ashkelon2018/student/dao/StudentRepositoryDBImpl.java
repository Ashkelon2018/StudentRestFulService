package telran.ashkelon2018.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import telran.ashkelon2018.student.configuration.DBConfig;
import telran.ashkelon2018.student.domain.Student;

@Repository
public class StudentRepositoryDBImpl implements StudentRepository {

	@Autowired
	DBConfig dbConfig;

	@Override
	public boolean addStudent(Student student) {
		try (Connection connection = dbConfig.getConnection()) {
			String query = "INSERT INTO student_db.students VALUES(?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, student.getId());
			statement.setString(2, student.getName());
			statement.setString(3, student.getPassword());
			statement.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Student removeStudent(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student findStudentById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student editStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

}
