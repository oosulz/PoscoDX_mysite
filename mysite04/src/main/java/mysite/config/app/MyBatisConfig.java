package mysite.config.app;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

	@Bean
	public SqlSessionFactory sqlSessionFactory(ApplicationContext applicationContext, DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mysite/config/app/mybatis/configuration.xml"));
		return sqlSessionFactory.getObject();
	}
	
	@Bean
	public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
		
		 return sqlSessionFactory.openSession();
	}

}
