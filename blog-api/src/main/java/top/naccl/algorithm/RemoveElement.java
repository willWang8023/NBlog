package top.naccl.algorithm;

/**
 * 移除元素
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
 * 假设 nums 中不等于 val 的元素数量为 k，要通过此题，您需要执行以下操作：
 * 更改 nums 数组，使 nums 的前 k 个元素包含不等于 val 的元素。nums 的其余元素和 nums 的大小并不重要。
 * 返回 k。
 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
 * 输出：5, nums = [0,1,4,0,3,_,_,_]
 * 解释：你的函数应该返回 k = 5，并且 nums 中的前五个元素为 0,0,1,3,4。
 * 注意这五个元素可以任意顺序返回。
 * 你在返回的 k 个元素之外留下了什么并不重要（因此它们并不计入评测）。
 */
public class RemoveElement {

    public static void main(String[] args) {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int num = removeElement(nums, 2);
        System.out.println("num:" + num);
    }

    /**
     * 一对快慢指针的爱情故事
     *
     * @param nums 数组
     * @param val  值
     * @return 不等于val的值的数量
     */
    public static int removeElement(int[] nums, int val) {
        int slow = 0, fast = 0;   //一对夫妇，原本都是零起点
        while (fast < nums.length) {//但是有一个跑得快，一个跑得慢
            if (nums[fast] != val) {//于是跑得快的那个先去寻找共同目标
                nums[slow] = nums[fast];//如果找到了，就送给跑得慢的那个
                slow++;//然后跑得慢的那个也就离目标近一点
            }
            fast ++;//但是不管是否找得到，跑得快的那方都一直奔跑到生命的尽头
        }
        return slow;//最终留下跑得慢的一方
    }

}
