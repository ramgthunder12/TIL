package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;

@Configuration
public class AppCtx2 {
	@Autowired
	private DataSource dataSource;

	@Bean
	public MemberDao memberDao() {
		return new MemberDao(dataSource);
	}
}
