package top.naccl.algorithm;

/**
 * 寻找两个正序数组的中位数
 * =========================================================================================
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * -----------------------------------------------------------------------------------------
 * 示例 1：
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * -----------------------------------------------------------------------------------------
 * 示例 2：
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 */
public class _4TheMiddleOfArrays {

    public static void main(String[] args){
        Integer[] nums1 = new Integer[]{1,3,4,9};
        Integer[] nums2 = new Integer[]{1,2,3,4,5,6,7,8,9};
        double middleNum = getMiddleNumFromTwoSortedArrays(nums1, nums2);
        System.out.println("middleNum:::" + middleNum);
    }

    /**
     * 二分查找
     * ===========================================
     * 给定两个有序数组，要求找到两个有序数组的中位数，最直观的思路有以下两种：
     * > 使用归并的方式，合并两个有序数组，得到一个大的有序数组。大的有序数组的中间位置的元素，即为中位数。
     * > 不需要合并两个有序数组，只要找到中位数的位置即可。由于两个数组的长度已知，因此中位数对应的两个数组
     *   的下标之和也是已知的。维护两个指针，初始时分别指向两个数组的下标 0 的位置，每次将指向较小值的指针
     *   后移一位（如果一个指针已经到达数组末尾，则只需要移动另一个数组的指针），直到到达中位数的位置。
     * 假设两个有序数组的长度分别为 m 和 n:
     * 根据中位数的定义，当 m+n 是奇数时，中位数是两个有序数组中的第 (m+n)/2 个元素，当 m+n 是偶数时，中位
     * 数是两个有序数组中的第 (m+n)/2 个元素和第 (m+n)/2+1 个元素的平均值。因此，这道题可以转化成寻找两个
     * 有序数组中的第 k 小的数，其中 k 为 (m+n)/2 或 (m+n)/2+1。
     * 假设两个有序数组分别是 A 和 B。要找到第 k 个元素，我们可以比较 A[k/2−1] 和 B[k/2−1]，(其中 / 表示
     * 整数除法)。由于 A[k/2−1] 和 B[k/2−1] 的前面分别有 A[0..k/2−2] 和 B[0..k/2−2]，即 k/2−1 个元素,
     * 对于 A[k/2−1] 和 B[k/2−1] 中的较小值x，最多只会有 (k/2−1)+(k/2−1)≤k−2 个元素比x小，那么x就不能是
     * 第 k 小的数了,(他一定比 k 小)。
     * 因此我们可以归纳出三种情况：
     * 1.如果A[k/2 - 1] < B[k/2 - 1], 则比A[k/2 - 1]小的数最多只有A的前k/2-1个数和B的前k/2-1个数，即
     *   比A[k/2 - 1]小的数最多只有k-2个，因此A[k/2 - 1]不可能是第k个数，A[0]到A[k/2 - 1]也都不可能是第
     *   k个数，可以全部排除。
     * 2.如果 A[k/2−1] > B[k/2−1]，则可以排除 B[0] 到 B[k/2−1]。
     * 3.如果 A[k/2−1]=B[k/2−1]，则可以归入第一种情况处理。
     *                                ====----                     ==:需要移除的部分
     *                              A|1|2|3|6|                     --:保留的部分
     *                                  ^
     * A[k/2 - 1] < B[k/2 - 1]：      ====---------                -k=(m+n)/2=5
     *                              B|1|3|4|5|9|10|               |
     *                                  ^                          -k/2-1=1
     *                               ====----
     *                             A|3|4|5|6|
     *                                 ^
     * A[k/2 - 1] > B[k/2 - 1]：     ====---------
     *                             B|2|3|4|5|9|10|
     *                                 ^
     *                               ====----
     *                             A|1|3|5|6|
     *                                 ^
     * A[k/2 - 1] = B[k/2 - 1]：     ====---------
     *                             B|2|3|4|5|9|10|
     *                                 ^
     * 可以看到，比较 A[k/2−1] 和 B[k/2−1] 之后，可以排除 k/2 个不可能是第 k 小的数，查找范围缩小了一半。同时，
     * 我们将在排除后的新数组上继续进行二分查找，并且根据我们排除数的个数，减少 k 的值，这是因为我们排除的数都不大于
     * 第 k 小的数。
     * 有以下三种情况需要特殊处理：
     * > 如果 A[k/2−1] 或者 B[k/2−1] 越界，那么我们可以选取对应数组中的最后一个元素。在这种情况下，我们必须根据排
     *   除数的个数减少 k 的值，而不能直接将 k 减去 k/2。
     * > 如果一个数组为空，说明该数组中的所有元素都被排除，我们可以直接返回另一个数组中第 k 小的元素。
     * > 如果 k=1，我们只要返回两个数组首元素的最小值即可。
     * ------------------------------------------------------------------------------------------------
     * 用一个例子说明上述算法。假设两个有序数组如下：
     * A:1 3 4 9
     * B:1 2 3 4 5 6 7 8 9
     * 两个有序数组的长度分别是 4 和 9，长度之和是 13，中位数是两个有序数组中的第 7 个元素，因此需要找到第 k=7 的元素。
     * 比较两个有序数组中下标为 k/2−1=2的数，即 A[2] 和 B[2]，如下面所示：
     * A:1 3 4 9
     *       ^
     * B:1 2 3 4 5 6 7 8 9
     *       ^
     * 由于 A[2]>B[2]，因此排除 B[0] 到 B[2]，即数组 B 的下标偏移（offset）变为 3，同时更新 k 的值：k=k−k/2=4。
     * 下一步寻找，比较两个有序数组中下标为 k/2−1=1 的数，即 A[1] 和 B[4]，如下面所示，其中方括号部分表示已经被排除的数。
     * A:1 3 4 9
     *     ^
     * B:[1 2 3] 4 5 6 7 8 9
     *             ^
     * 由于 A[1]<B[4]，因此排除 A[0] 到 A[1]，即数组 A 的下标偏移变为 2，同时更新 k 的值：k=k−k/2=2。
     * 下一步寻找，比较两个有序数组中下标为 k/2−1=0 的数，即比较 A[2] 和 B[3]，如下面所示，其中方括号部分表示已经被排除的数。
     * A:[1 3] 4 9
     *         ^
     * B:[1 2 3] 4 5 6 7 8 9
     *           ^
     * 由于 A[2]=B[3]，根据之前的规则，排除 A 中的元素，因此排除 A[2]，即数组 A 的下标偏移变为 3，同时更新 k 的值： k=k−k/2=1。
     * 由于 k 的值变成 1，因此比较两个有序数组中的未排除下标范围内的第一个数，其中较小的数即为第 k 个数，由于 A[3]>B[3]，
     * 因此第 k 个数是 B[3]=4。
     * A:[1 3 4] 9
     *           ^
     * B:[1 2 3] 4 5 6 7 8 9
     *           ^
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 两个数组的中位数
     */
    public static double getMiddleNumFromTwoSortedArrays(Integer[] nums1, Integer[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }

    /**
     * 获取第k小的数
     * ===================================
     * 要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
     * 这里的 "/" 表示整除
     * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
     * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
     * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
     * 这样 pivot 本身最大也只能是第 k-1 小的元素
     * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
     * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
     * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
     *
     * @param nums1 数组1
     * @param nums2 数组2
     * @param k     第几个数
     * @return 第k小的数
     */
    public static int getKthElement(Integer[] nums1, Integer[] nums2, int k) {
        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            // 考虑中位可能大于其中一个较小的数组的长度，newIndex是从新的开始位置计算中位数的下标
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            // index的修改表示删除部分小的数据
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

}