package spring2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("s2InfoPrinter")
public class MemberInfoPrinter {
	//@Autowired
	private MemberDao memberDao;
	//@Autowired
	private MemberPrinter printer;
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if(member == null) {
			System.out.println("데이터 없음\n");
			return ;
		}
		
		printer.print(member);
		System.out.println();
	}
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Autowired
	@Qualifier("s2MemberPrinter")
	public void setPrinter(MemberPrinter printer) {
		this.printer = printer;
	}
}
