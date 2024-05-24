package top.naccl.algorithm;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * =============================================================
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 * -------------------------------------------------------------
 * 示例 1：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 * -------------------------------------------------------------
 * 示例 2：
 * 输入：nums = [3,2,4], target = 6
 * 输出：[1,2]
 * -------------------------------------------------------------
 * 示例 3：
 * 输入：nums = [3,3], target = 6
 * 输出：[0,1]
 * -------------------------------------------------------------
 * 提示：
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * 只会存在一个有效答案
 */
public class _1TwoNumSum {

    public static void main(String[] args){
        int[] nums = new int[]{2,7,11,15};
        int target = 9;
        int[] resultNums = hashTableFunc(nums, target);
        System.out.println("resultNums:::" + JSON.toJSONString(resultNums));
    }


    /**
     * 哈希表
     * 创建一个哈希表，对于每一个 x，我们首先查询哈希表中是否存在 target - x，然后将 x 插入到哈希表中，即可保证不会让 x 和自己匹配。
     *
     * @return 两数之和等于目标的两个数在数组中的下标
     */
    public static int[] hashTableFunc(int[] nums, int target) {

        Map<Integer, Integer> hashTable = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            // 如果找到一个值和目标值的差在hashTable中存在，则找到了需要的两个数字，
            if(hashTable.containsKey(target - nums[i])){
                return new int[]{hashTable.get(target - nums[i]), i};
            }
            hashTable.put(nums[i], i);
        }
        return new int[0];
    }

}
