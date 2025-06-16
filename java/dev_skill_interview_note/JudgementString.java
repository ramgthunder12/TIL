public class JudgementString {

	private boolean isPalindrome(String str) {
		char[] charArray = str.toCharArray();
		
		for(int i = 0; i < charArray.length / 2; i ++) {
			int lastIndex = charArray.length - 1;

			char beforChar = Character.toLowerCase(charArray[i]);
			char afterChar = Character.toLowerCase(charArray[lastIndex -i]);
	
			if(beforChar != afterChar) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String str = "Level";
		
		JudgementString jud = new JudgementString();
		
		System.out.println(jud.isPalindrome(str)); 
	}
}
