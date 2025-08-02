package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import spring.Client;
import spring.Client2;
import spring.Client3;
import spring.Client4;

@Configuration
public class AppCtx {
//	@Bean
//	public Client client() {
//		Client client = new Client();
//		client.setHost("host");
//		return client;
//	}
//
//	@Bean(initMethod = "connect", destroyMethod = "destroy")
//	public Client2 client2() {
//		Client2 client = new Client2();
//		client.setHost("host2");
//		return client;
//	}
//	
//	@Bean(destroyMethod = "destroy")
//	public Client3 client3() {
//		Client3 client = new Client3();
//		client.setHost("host3");
//		client.connect();
//		return client;
//	}
//
//	@Bean
//	public Client4 client4() {
//		Client4 client = new Client4();
//		client.setHost("host4");
//		return client;
//	}
//	
	@Bean
	@Scope("prototype")
//	@Scope("singleton")
	public Client client() {
		Client client = new Client();
		client.setHost("host");
		return client;
	}
}