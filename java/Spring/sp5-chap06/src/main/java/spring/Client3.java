package spring;

public class Client3 {
	private String host;
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void connect() {
		System.out.println("Client3.connect()");
	}
	
	public void send() {
		System.out.println("Client3.send() to " + host);
	}
	
	public void destroy() {
		System.out.println("Client3.destroy()");
	}
}
