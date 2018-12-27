package telran.ashkelon2018.student.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.ashkelon2018.student.dao.StudentRepository;
import telran.ashkelon2018.student.dao.StudentRepositoryMapImpl;

@Configuration
public class StudentBeanConfiguration {
	
//	@Bean
//	public StudentRepository createStudentRepo() {
//		return new StudentRepositoryMapImpl();		
//	}
	
	@Bean
	public ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		return mapper;
	}

}
