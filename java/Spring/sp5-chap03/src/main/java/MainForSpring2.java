import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import assembler.Assembler;
import config.AppConfImport2;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.VersionPrinter;
import spring.WrongIdPasswordException;

public class MainForSpring2 {
	private static AnnotationConfigApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
//		ctx = new AnnotationConfigApplicationContext(AppCtx1.class, AppCtx2.class);
//		ctx = new AnnotationConfigApplicationContext(AppConfImport.class);
		ctx = new AnnotationConfigApplicationContext(AppConfImport2.class);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

//		AppCtx1 appConf1 = ctx.getBean(AppCtx1.class);
//		AppCtx2 appConf2 = ctx.getBean(AppCtx2.class);
//		
//		System.out.println(appConf1 != null);//true
//		System.out.println(appConf2 != null);//true
		
		while(true) {
			System.out.println("명령어를 입력하시오.");
			String command = reader.readLine();
			if(command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if(command.startsWith("new ")) {
				processNewCommand(command.split(" "));
				continue;
			} else if(command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
				continue;
			} else if(command.startsWith("list")) {
				processListCommand();
				continue;
			} else if(command.startsWith("info ")) {
				processInfoCommand(command.split(" "));
				continue;
			} else if(command.startsWith("version")) {
				processVersionCommand();
				continue;
			}
			printHelp();
		}
	}

	private static Assembler assembler = new Assembler();
	
	private static void processNewCommand(String[] arg) {
		if(arg.length != 5) {
			printHelp();
			return;
		}
		
		//MemberRegisterService registerService = assembler.getMemberRegisterService();
		MemberRegisterService registerService = ctx.getBean("memberRegSvc", MemberRegisterService.class);
		RegisterRequest request = new RegisterRequest();
		request.setEmail(arg[1]);
		request.setName(arg[2]);
		request.setPassword(arg[3]);
		request.setConfirmPassword(arg[4]);
		
		if(!request.isPasswordEquealToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		
		try {
			registerService.regist(request);
			System.out.println("등록했습니다.\n");
		} catch(DuplicateMemberException e) {
			System.out.println("이미 존재하는 메일입니다.\n");
		}
	}
	
	private static void processChangeCommand(String[] arg) {
		if(arg.length != 4) {
			printHelp();
			return;
		}
		
		//ChangePasswordService changePwService = assembler.getChangPasswordService();
		ChangePasswordService changePwService = ctx.getBean("changePwdSvc", ChangePasswordService.class);
		
		try {
			changePwService.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch(MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일 입니다\n.");
		} catch(WrongIdPasswordException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}
	
	private static void processListCommand() {
		MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		listPrinter.printAll();
	}
	
	private static void processInfoCommand(String[] arg) {
		if(arg.length != 2) {
			printHelp();
			return;
		}
		
		MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
		infoPrinter.printMemberInfo(arg[1]);
	}
	
	private static void processVersionCommand() {
		VersionPrinter versionPrinter = ctx.getBean("versionPrinter", VersionPrinter.class);
		versionPrinter.print();
	}
	
	private static void printHelp() {
		System.out.println("""
				
				
				잘못된 명령어 입니다. 아래 명령어 사용법을 확인하세요.
				
				명령어 사용법 : 
				- new 이메일 이름 암호 암호확인
				- change 에메일 현재비밀번호 변경비밀번호
				- list (등록된 아이디, 메일, 이름, 등록시간 정보를 확인하는 명령어)
				- info 이메일
				- version (major, minor 버전 정보를 확인하는 명령어)
				
				""");
	}
}
