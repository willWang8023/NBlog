package top.naccl.algorithm;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 给定n个非负整数表示每个宽度为1的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * ^
 * |
 * |                            ___
 * 3|            ___            |   |___     ___
 * 2|    ___    |   |___     ___|       |___|   |___
 * 1| 0 | 1 | 0 | 2   1 | 0 | 1   3   2   1   2   1 |
 * 0|------------------------------------------------------>
 * 上面是由数组[0，1，0，2，1，0，1，3，2，1，2，1]表示的高度图，在这种情况下，可以接6个单位的雨水。
 * 下面就来由浅入深介绍暴力解法 -> 备忘录解法 -> 双指针解法，在 O(N) 时间 O(1) 空间内解决这个问题。
 */
public class CatchRainwater {

    public static void main(String[] args) {
        List<Integer> pillars = Arrays.asList(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1);
//        int contain = trap(pillars);
        int contain = memorandumTrap(pillars);
        System.out.println("catch rain water:" + contain);
    }

    /**
     * 二、备忘录优化
     * 之前的暴力解法，不是在每个位置 i 都要计算 r_max 和 l_max 吗？我们直接把结果都缓存下来，别傻不拉几的每次都遍历，
     * 这时间复杂度不就降下来了嘛。我们开两个数组 r_max 和 l_max 充当备忘录，l_max[i] 表示位置 i 左边最高的柱子高度，
     * r_max[i] 表示位置 i 右边最高的柱子高度。预先把这两个数组计算好，避免重复计算：
     * 这个优化其实和暴力解法差不多，就是避免了重复计算，把时间复杂度降低为 O(N)，已经是最优了，但是空间复杂度是 O(N)。
     * 下面来看一个精妙一些的解法，能够把空间复杂度降低到 O(1)。
     *
     * @param heights 柱子高度数组
     * @return 接到的雨水量
     */
    public static Integer memorandumTrap(List<Integer> heights) {
        if(CollectionUtils.isEmpty(heights)){
            return 0;
        }
        int n = heights.size();
        int ans = 0;
        // List<Integer> 充当备忘录
        List<Integer> l_max = Arrays.asList(new Integer[n]);
        List<Integer> r_max = Arrays.asList(new Integer[n]);
        // 初始化 base case
        l_max.set(0, heights.get(0));
        r_max.set(n-1, heights.get(n-1));
        // 从左向右计算l_max,对于当前位置来说左侧最高的柱子高度
        for(int i = 1; i < n; i++){
            l_max.set(i, Math.max(l_max.get(i-1), heights.get(i)));
        }
        // 从右往左计算r_max,对于当前位置来说右侧最高的柱子高度
        for(int i = n-2; i >= 0; i--){
            r_max.set(i, Math.max(r_max.get(i+1), heights.get(i)));
        }
        // 计算答案
        for(int i = 1; i < n-1; i++){
            ans += Math.min(l_max.get(i), r_max.get(i)) -heights.get(i);
        }
        return ans;
    }

    /**
     * 一、核心思路
     * 我第一次看到这个问题，无计可施，完全没有思路，相信很多朋友跟我一样。所以对于这种问题，我们不要想整体，而应该去想局部；
     * 就像之前的文章处理字符串问题，不要考虑如何处理整个字符串，而是去思考应该如何处理每一个字符。
     * 这么一想，可以发现这道题的思路其实很简单。具体来说，仅仅对于位置 i，能装下多少水呢？
     * ^
     * |
     * |                            ___
     * 3|            ___      i      |   |___     ___
     * 2|    ___    |   |___     ___|       |___|   |___
     * 1| 0 | 1 | 0 | 2   1 | 0 | 1   3   2   1   2   1 |
     * 0|------------------------------------------------------>
     * 装 2 格水。为什么恰好是两格水呢？因为 height[i] 的高度为 0，而这里最多能盛 2 格水，2-0=2。
     * 为什么位置 i 最多能盛 2 格水呢？因为，位置 i 能达到的水柱高度和其左边的最高柱子、右边的最高柱子有关，我们分别称这两个柱子
     * 高度为 l_max 和 r_max；位置 i 最大的水柱高度就是 min(l_max, r_max)。
     * 更进一步，对于位置 i，能够装的水为：
     * water[i] = min(
     * # 左边最高的柱子
     * max(height[0..i]),
     * # 右边最高的柱子
     * max(height[i..end])
     * ) - height[i]
     * 这就是本问题的核心思路，我们可以简单写一个暴力算法：
     * 这个解法应该是很直接粗暴的，时间复杂度 O(N^2)，空间复杂度 O(1)。
     * 但是很明显这种计算 r_max 和 l_max 的方式非常笨拙，
     *
     * @param heights
     * @return
     */
    public static int trap(List<Integer> heights) {
        int n = heights.size();
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            int l_max = 0, r_max = 0;
            // 找右边最高的柱子
            for (int j = i; j < n; j++)
                r_max = Math.max(r_max, heights.get(j));
            // 找左边最高的柱子
            for (int j = i; j >= 0; j--)
                l_max = Math.max(l_max, heights.get(j));
            // 如果自己就是最高的话，
            // l_max == r_max == height[i]
            ans += Math.min(l_max, r_max) - heights.get(i);
        }
        return ans;
    }

}
