package top.naccl.algorithm;

import top.naccl.util.StringUtils;

/**
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * =====================================
 * 示例 1：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 * -------------------------------------
 * 示例 2：
 * 输入：s = "cbbd"
 * 输出："bb"
 */
public class _5LongestPalindromeSubstr {

    public static void main(String[] args){
        String str = "babad";
        String subStr = subStr(str);
        System.out.println(subStr);
    }

    /**
     * 中心扩展算法
     * ================================
     * 可以从每一种边界情况开始「扩展」，也可以得出所有的状态对应的答案。
     * 边界情况即为子串长度为 111 或 222 的情况。我们枚举每一种边界情况，并从对应的子串开始不断地向两边扩展。
     * 如果两边的字母相同，我们就可以继续扩展，例如从 P(i+1,j−1) 扩展到 P(i,j)；如果两边的字母不同，我们就
     * 可以停止扩展，因为在这之后的子串都不能是回文串了。
     * 「边界情况」对应的子串实际上就是我们「扩展」出的回文串的「回文中心」
     * 我们枚举所有的「回文中心」并尝试「扩展」，直到无法扩展为止，此时的回文串长度即为此「回文中心」下的最长回
     * 文串长度。我们对所有的长度求出最大值，即可得到最终的答案。
     *
     * @return 最长回文子串
     */
    public static String subStr(String str) {
        if(StringUtils.isEmpty(str)){
            return "";
        }
        int start = 0, end = 0;
        for(int i = 0; i < str.length(); i++){
            int len1 = expandAroundCenter(str, i, i);
            int len2 = expandAroundCenter(str, i, i + 1);
            int len = Math.max(len1, len2);
            if(len > end - start){
                start = i - (len-1) / 2;
                end = i + len / 2;
            }
        }
        return str.substring(start, end + 1);
    }

    /**
     * 从i位置向两端扩展，获取回文子串的长度
     * @param str 给定的字符串长度
     * @param left 往左扩展的起始位置
     * @param right 往右开始的起始位置
     * @return 回文子串的长度
     */
    private static int expandAroundCenter(String str, int left, int right){
        while(left >= 0 & right < str.length() && str.charAt(left) == str.charAt(right)){
            -- left;
            ++ right;
        }
        return right - left - 1;
    }

}
