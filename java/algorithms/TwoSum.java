import java.util.HashSet;
import java.util.Set;

public class TwoSum {
    static boolean hasTwoSum(int[] nums, int target) {
        Set<Integer> seen = new HashSet<>();
        
        for(int i = 0; i < nums.length; i++) {
            if(seen.contains(target - nums[i])) {
                return true;
            }
            seen.add(nums[i]);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        System.out.println(hasTwoSum(nums, target));
    }
}
