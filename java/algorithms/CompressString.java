public class CompressString {
    public static void main(String[] args) {
        String str = "aaaaaaaaaaabccc";
        String compressedStr = compressString(str);
        String decompressedStr = decompressString(compressedStr);
        System.out.println("압축 전 문자열: " + str);
        System.out.println("압축 후 문자열: " + compressedStr);
        System.out.println("압축 해제 후 문자열: " + decompressedStr);
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

    static String decompressString(String str) {
        String strPlusSpase = str + " ";
        String answer = "";

        for(int i = 1; i < strPlusSpase.length(); i++) {
            char firstCharater = strPlusSpase.charAt(i - 1);
            char secendCharater = strPlusSpase.charAt(i);
            //두번째 글짜가 숫자 이면, 숫자 만큼 반복문 돌리기
            if(Character.isDigit(secendCharater)) {
                String secendCharaterStr = String.valueOf(secendCharater);
                int secendCharaterInt = Integer.parseInt(secendCharaterStr);
                String firstString = "";

                for(int j = 0; j < secendCharaterInt; j++) {
                    firstString = firstString + firstCharater;
                }
                answer = answer + firstString;
            } else {
                if(Character.isDigit(firstCharater)) {
                    continue;
                }
                answer = answer + firstCharater;
            }
        }
        return answer;
    }
}
