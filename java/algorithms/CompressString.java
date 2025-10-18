public class CompressString {
    public static void main(String[] args) {
        String str = "aaabbbcccdde";
        String compressedStr = compressString(str);
        System.out.println("압축 전 문자열: " + str);
        System.out.println("압축 후 문자열: " + compressedStr);
    }

    static String compressString(String str) {
        String strPlusSpase = str + " ";
        String answer = "";

        int count = 1;
        for(int i = 1; i < strPlusSpase.length(); i++) {
            char firstCharater = strPlusSpase.charAt(i - 1);
            char secendCharater = strPlusSpase.charAt(i);
            
            if(firstCharater == secendCharater) {
                count++;
            } else {
                answer = answer + firstCharater;
                if(count > 1) {
                    answer = answer + count;
                    count = 1;
                }
            }

        }
        return answer;
    }
}
