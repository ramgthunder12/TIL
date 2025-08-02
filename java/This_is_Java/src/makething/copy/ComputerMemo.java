package makething.copy;

import java.util.Scanner;

public class ComputerMemo implements Memo{
	@Override
	public void write() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("입력하세요.");
		String str = scanner.nextLine();
		System.out.println("Computer Memo : " + str);
	}
}
