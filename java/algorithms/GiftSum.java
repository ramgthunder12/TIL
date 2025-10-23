import java.util.HashSet;
import java.util.Set;

public class GiftSum {
    
    static boolean hasGiftSum(int[] gift, int W) {
        int n = gift.length;
        Set<Integer> seen = new HashSet<>();
        for(int i = 0; i < n; i++) {
            for(int x : seen) {
                System.out.println("gitft[%d] : %d, x : %d".formatted(i, gift[i], x));
                if(seen.contains(W - gift[i] - x)) {//contians(Object o) : o.equals()로 비교함
                    return true;
                }
            }
            seen.add(gift[i]);
        }
        
        return false;
    }

    public static void main(String[] args) {
        int[] gift = {1, 2, 3, 9, 5};
        int W = 10;
        System.out.println(hasGiftSum(gift, W));
    }
}