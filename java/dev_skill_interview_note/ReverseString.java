public class ReverseString{
	public String reversString(String str) {
		String result = new StringBuilder(str).reverse().toString();
		return result;	
	}
	
	public static void main(String[] args) {
		ReverseString reversString = new ReverseString();
		
		String result = reversString.reversString("ABCDE");
		
		System.out.println(result);
	}
}
