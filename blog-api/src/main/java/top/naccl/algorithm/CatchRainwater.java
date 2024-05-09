package top.naccl.algorithm;

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
        int contain = trap(pillars);
        System.out.println("catch rain water:" + contain);
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
     * @param height
     * @return
     */
    public static int trap(List<Integer> height) {
        int n = height.size();
        int ans = 0;
        for (int i = 1; i < n - 1; i++) {
            int l_max = 0, r_max = 0;
            // 找右边最高的柱子
            for (int j = i; j < n; j++)
                r_max = Math.max(r_max, height.get(j));
            // 找左边最高的柱子
            for (int j = i; j >= 0; j--)
                l_max = Math.max(l_max, height.get(j));
            // 如果自己就是最高的话，
            // l_max == r_max == height[i]
            ans += Math.min(l_max, r_max) - height.get(i);
        }
        return ans;
    }

}
