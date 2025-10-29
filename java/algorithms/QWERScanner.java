import java.util.Arrays;

public class QWERScanner {
    public static int[] versionScanner(int TC, int[][] QWER) {
        int[] answer = new int[TC];

        int versionFirstBit = 0;
        int versionSecendBit = 0;
        int[] versionBits = new int[4];
        int count = 0;

        for(int i = 0; i < TC; i++) {
            for(int j = 4; j < 64; j = j + 16) {
                versionFirstBit = ((QWER[i][j] ^ QWER[i][j + 1]) ^ QWER[i][j + 2]) ^ QWER[i][j + 3];
                versionSecendBit = ((QWER[i][j + 4] ^ QWER[i][j + 5]) ^ QWER[i][j + 6]) ^ QWER[i][j + 7];
                versionBits[count] = (versionFirstBit * 2) + versionSecendBit;
                count++;
            }
            answer[i] = versionBits[0] * 1000 + versionBits[1] * 100 + versionBits[2] * 10 + versionBits[3];
            count = 0;
        }

        return answer;
    }

    public static String[] domainScanner(int TC, int[][] QWER) {
        String[] answer = new String[TC];
        int optionChunk1_1 = 0;
        int optionChunk1_2 = 0;
        int optionChunk2_1 = 0;
        int optionChunk2_2 = 0;

        int result = 0;

        for(int i = 0; i < TC; i++) {
            StringBuilder domain = new StringBuilder();
            for(int j = 64; j < 192; j = j + 16) {
                    optionChunk1_1 = QWER[i][j];
                    optionChunk1_2 = QWER[i][j + 1];

                    optionChunk2_1 = QWER[i][j + 2];
                    optionChunk2_2 = QWER[i][j + 3];

                    if(optionChunk1_1 == 0) {
                        result = QWER[i][j + 4] * 16 + QWER[i][j + 5] * 8 + QWER[i][j  + 6] * 4 + QWER[i][j  + 7] * 2 + QWER[i][j  + 8] * 1;

                        if(optionChunk1_2 == 0) {
                            //00
                        } else {
                            //01
                            result = result + 32;
                        }
                        domain.append(result);
                    } else {
                        result = QWER[i][j  + 4] * 16 + QWER[i][j + 5] * 8 + QWER[i][j  + 6] * 4 + QWER[i][j  + 7] * 2 + QWER[i][j + 8] * 1;
                        char c = 0;
                        if(result != 0) {
                            if(optionChunk1_2 == 0) {
                                //10
                                c = (char)(result + 64);
                            } else {
                                //11
                                c = (char)(result + 96);
                            }
                        } else {
                            c = 0;
                        }
                        
                        domain.append(c);
                    }

                    if(optionChunk2_1 == 0) {
                        result = QWER[i][j + 9] * 16 + QWER[i][j + 10] * 8 + QWER[i][j  + 11] * 4 + QWER[i][j  + 12] * 2 + QWER[i][j  + 13] * 1;

                        if(optionChunk2_2 == 0) {
                            //00
                        } else {
                            //01
                            result = result + 32;
                        }
                        domain.append(result);
                    } else {
                        result = QWER[i][j + 9] * 16 + QWER[i][j + 10] * 8 + QWER[i][j  + 11] * 4 + QWER[i][j  + 12] * 2 + QWER[i][j  + 13] * 1;
                        char c = 0;
                        if(result != 0) {
                            if(optionChunk2_2 == 0) {
                                //10
                                c = (char)(result + 64);
                            } else {
                                //11
                                c = (char)(result + 96);
                            }
                        } else {
                            c = 0;
                        }
                        domain.append(c);
                    }
            }
            answer[i] = domain.toString();
        }
        return answer;
    }

    public static void main(String[] args) {
        int[][] QWER = {
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 
            0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 
            0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0,
            1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 
            0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 
            0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 
            1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 
            1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            };
        int TC = QWER.length;
        System.out.println(Arrays.toString(versionScanner(TC, QWER)));
        System.out.println(Arrays.toString(domainScanner(TC, QWER)));
    }
}