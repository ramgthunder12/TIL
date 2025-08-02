package spring;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Client4 {
	private String host;
	
	public void setHost(String host) {
		this.host = host;
	}
	
	@PostConstruct
	public void connect() {
		System.out.println("Client4.connect()");
	}
	
	public void send() {
		System.out.println("Client4.send() to " + host);
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("Client4.destroy()");
	}
}
