package telran.ashkelon2018.student.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import telran.ashkelon2018.student.configuration.DBConfig;
import telran.ashkelon2018.student.domain.Student;

@Repository
public class StudentRepositoryDBImpl implements StudentRepository {

//	@Autowired
//	DBConfig dbConfig;

	@Autowired
	DataSource dataSource;

	@Override
	public boolean addStudent(Student student) {
		try (Connection connection = dataSource.getConnection()) {
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
		// FIXME
		Student student = findStudentById(id);
		if (student == null) {
			return null;
		}
		try (Connection connection = dataSource.getConnection()) {
			String query = "DELETE FROM student_db.students WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.execute();
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Student findStudentById(int id) {
		try (Connection connection = dataSource.getConnection()) {
			String query = "SELECT name,password FROM student_db.students WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			Student student = new Student(id, resultSet.getString(1), resultSet.getString("password"));
			query = "SELECT exam,score FROM student_db.scores WHERE student_id=?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				student.addScore(resultSet.getString(1), resultSet.getInt(2));
			}
			return student;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Student editStudent(Student studentNew) {
		Student student = findStudentById(studentNew.getId());
		if (student == null) {
			return null;
		}
		student.setName(studentNew.getName());
		student.setPassword(studentNew.getPassword());
		try (Connection connection = dataSource.getConnection()) {
			String query = "UPDATE student_db.students SET name=?, password=? WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, student.getName());
			statement.setString(2, student.getPassword());
			statement.setInt(3, student.getId());
			statement.execute();
			return student;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addScore(int id, String examName, int score) {
		try (Connection connection = dataSource.getConnection()) {
			String query = "SELECT * FROM  student_db.scores WHERE exam=? AND student_id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, examName);
			statement.setInt(2, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				query = "UPDATE student_db.scores SET score=? WHERE exam=? AND student_id=?";
				statement = connection.prepareStatement(query);
				statement.setString(2, examName);
				statement.setInt(1, score);
				statement.setInt(3, id);
				statement.execute();
				return true;
			} else {
				query = "INSERT INTO student_db.scores VALUES(?, ?, ?)";
				statement = connection.prepareStatement(query);
				statement.setInt(1, id);
				statement.setString(2, examName);
				statement.setInt(3, score);
				statement.execute();
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

}
