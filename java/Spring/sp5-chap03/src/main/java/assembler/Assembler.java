package assembler;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

public class Assembler {
	private MemberDao memberDao;
	private MemberRegisterService registerService;
	private ChangePasswordService changePwService;
	
	public Assembler() {
		memberDao = new MemberDao();
		registerService = new MemberRegisterService(memberDao);
		changePwService = new ChangePasswordService();
		changePwService.setMemberDao(memberDao);
	}
	
	public MemberDao getMemberDao() {
		return memberDao;
	}
	
	public MemberRegisterService getMemberRegisterService() {
		return registerService;
	}
	
	public ChangePasswordService getChangPasswordService() {
		return changePwService;
	}
}
