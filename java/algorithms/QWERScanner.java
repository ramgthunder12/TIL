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
        
        for(int i = 0; i < TC; i++) {
            StringBuilder domain = new StringBuilder();

            for(int j = 64; j < 192; j += 16) {
                int[][] opt = {{QWER[i][j], QWER[i][j + 1]}, {QWER[i][j + 2], QWER[i][j + 3]}};

                for(int k = 0; k < 2; k++) {
                    int start = j + 4 + (k * 5);
                    int val = 0;
                    
                    for(int b = 0; b < 5; b++) {
                        val = val * 2 + QWER[i][start + b];
                    }

                    if(opt[k][0] == 0) {
                        if(opt[k][1] == 1) {
                            val += 32;
                        }
                        domain.append(val);
                    } else {
                        if(val == 0) {
                            continue;
                        }
                        char c = (char) (val + (opt[k][1] == 0 ? 64 : 96));
                        domain.append(c);
                    }
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