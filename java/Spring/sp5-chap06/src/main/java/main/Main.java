package main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import config.AppCtx;
import spring.Client;

public class Main {
	public static void main(String[] args) throws Exception {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
	
//		Client client = ctx.getBean(Client.class);

//		Client2 client = ctx.getBean(Client2.class);

//		Client3 client = ctx.getBean(Client3.class);

//		Client4 client = ctx.getBean(Client4.class);
		
//		client.send();
		
		Client client = ctx.getBean(Client.class);
		Client client2 = ctx.getBean(Client.class);

		System.out.println(client == client2);
		
		client.destroy();
		
		ctx.close();
	}
}
