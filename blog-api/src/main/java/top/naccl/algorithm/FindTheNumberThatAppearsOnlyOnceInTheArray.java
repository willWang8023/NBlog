package top.naccl.algorithm;


import java.util.ArrayList;
import java.util.List;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 * 要求时间复杂度是O(n)，空间复杂度是O(1)。
 */
public class FindTheNumberThatAppearsOnlyOnceInTheArray {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 2, 3};
        int[] result = getLastOnceNum(nums);
        for (int j : result) {
            System.out.println(j);
        }
    }


    private static int[] getLastOnceNum(int[] nums) {
        List<Integer> numList = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return null;
        }
        for (int num : nums) {
            if (numList.contains(num)) {
                // 由于整数类型既可以视为int，又可以视为Object，当我们从List中移除一个整数时，是有歧义的
                // 正确使用方法如下，先将其转为Integer
                numList.remove((Integer) num);
            } else {
                numList.add(num);
            }
        }
        return numList.stream().mapToInt(Integer::intValue).toArray();
    }

}
