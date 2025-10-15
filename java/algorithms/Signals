public class Signals{

    public static void main(String[] args) {
        int[][]signals = {{3, 2, 3}, {2, 3, 2}};

        
        for(int i = 1; i < signals.length; i++) {
            int greenProntSignalTime = signals[i - 1][0];
            int greenBackSignalTime = signals[i][0];

            int yellowProntSignalTime = signals[i - 1][1];
            int yellowBackSignalTime = signals[i][1];
            
            int totalProntSignalTime = signals[i - 1][0] + signals[i - 1][1] + signals[i - 1][2];
            int totalBackSignalTime = signals[i][0] + signals[i][1] + signals[i][2];

            for(int j = 0; j < 1000; j++) {
                for(int k = 0; k < 1000; k++) {
                    for(int h = 0; h < yellowBackSignalTime; h++) {//첫번째 신호등이 초록불 + (주기 * 시작점)
                        if(totalProntSignalTime * j + greenProntSignalTime < totalBackSignalTime * k + greenBackSignalTime + h &&
                            totalProntSignalTime * j + greenProntSignalTime + yellowProntSignalTime >= totalBackSignalTime * k + greenBackSignalTime + h) {
                            System.out.println("정전 : " + (totalBackSignalTime * j) + greenBackSignalTime + h + "초");
                            return;
                        }
                    }
                }
            }
        }
    }
}
