public class ReverseString4 {
	private String reverseString(String str) {
		return new StringBuilder(str).reverse().toString();
	}

	private String reverseString(int a) {
		String str = "ABCDE";
		char[] chars = str.toCharArray();

		for(int i = chars.length - 1; i <= 0; i--) {
			System.out.print(chars[i]);
		}
		return "";
	}

	private String reverseString() {
		String str = "ABCDE";
		
		char[] chars = str.toCharArray();

		for(int i = 0; i < (chars.length -1) / 2; i++) {
			//불변의 객체가 아니면서 StringBuilder도 아닌것은?
			// temp를 매번 생성해 주는게 좋을까? 아니면 매번 덮어 씌우는게 좋을까? => 메모리사용을 조금이라도 줄이려면 for문 밖에 tmep를 선언하고, 가독성 좋게 코드를 작성하려면 for문 안에 temp를 선언하는게 좋겠어.
			char temp = chars[chars.length -1];
			chars[i] = chars[chars.length -1 - i];
			chars[i] = temp;
		}
		return chars.toString();	
	}

	public static void main(String[] args){
		ReverseString4 reverseStr = new ReverseString4();
		
		String inputValue = "ABCDE";
		
		//System.out.println(함수호출VS 값); 뭐가 더 빠를까? 메모리를 적게 쓸까? => 속도 차이 거의 없음, 가독성 함수호출 방식이 더 좋음, 함수의 결과를 println(String s)에 넣는것임 
		System.out.println(reverseStr.reverseString(inputValue));

		String result = reverseStr.reverseString(inputValue);
		System.out.println(result);
	}
}
