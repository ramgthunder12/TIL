public class CompressString2 {
    public static void main(String[] args) {
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbcccdde";
        String compressedStr = compressString(str);
        String decompressedStr = decompressString(compressedStr);

        System.out.println("압축 전 문자열 : " + str);
        System.out.println("압축 후 문자열 : " + compressedStr);
        System.out.println("압축 해제 후 문자열 : " + decompressedStr);
    }

    static String compressString(String str) {
        StringBuilder sb = new StringBuilder();
        int count = 1;

        for(int i = 1; i <= str.length(); i++) {
            if(i < str.length() && str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                sb.append(str.charAt(i - 1));
                if(count > 1) {
                    sb.append(count);
                }
                count = 1;
            }
        }
        return sb.toString();
    }

    static String decompressString(String str) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if(Character.isDigit(currentChar)) {
                continue;
            }
            int j = i + 1;
            int num = 0;
            while(j < str.length() && Character.isDigit(str.charAt(j))) {
                num = num * 10 + (str.charAt(j) - '0');
                //char로 표현된 숫자를 int로 변환 하는 방식(str.charAt(j) - '0') : '0'은 ASCII 코드로 48, '1'은 49 이므로 '1' - '0'은 49 - 48로 1을 가짐
                //숫자가 두자리 이상이라면 십진수로 변환(num * 10) : 만약 두자리 이상의 숫자라면 이전 자리수 * 10 + 현재 자리수 로 계산
                j++;//두자리 수 일수 도 있으므로 while문으로 계속 확인
            }
            if(num == 0) {
                num = 1;
            }
            for(int k = 0; k < num; k++) {
                sb.append(currentChar);
            }

        }
        return sb.toString();
    }
}
