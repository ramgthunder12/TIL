public class ReverseString3{
	private void reverseStr(String str) {
		char[] strToChar = str.toCharArray();

		for(int a = strToChar.length - 1; a >= 0; a--) {
			System.out.print(strToChar[a]);
		}
	}

	public static void main(String[] args) {
		ReverseString3 reverseString = new ReverseString3();
		reverseString.reverseStr("ABCDE");
	}
}
