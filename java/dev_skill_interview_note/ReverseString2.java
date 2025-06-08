public class ReverseString2{
	private String reverseStr(String str) {
		return new StringBuilder(str).reverse().toString();
	}	
	
	public static void main(String[] args) {
		ReverseString2 reverseString = new ReverseString2();
		
		System.out.println(reverseString.reverseStr("ABCDE"));
	}
}
