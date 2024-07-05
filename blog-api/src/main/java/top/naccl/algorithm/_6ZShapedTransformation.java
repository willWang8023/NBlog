package top.naccl.algorithm;

/**
 * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
 * ==============================================================================
 * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
 * 示例 1：
 * ------------------------------------------------------------------------------
 * 输入：s = "PAYPALISHIRING", numRows = 3
 * 输出："PAHNAPLSIIGYIR"
 * ------------------------------------------------------------------------------
 * 示例 2：
 * 输入：s = "PAYPALISHIRING", numRows = 4
 * 输出："PINALSIGYAHRPI"
 * 解释：
 * P     I    N
 * A   L S  I G
 * Y A   H R
 * P     I
 * 示例 3：
 * ------------------------------------------------------------------------------
 * 输入：s = "A", numRows = 1
 * 输出："A"
 */
public class _6ZShapedTransformation {

    public static void main(String[] args) {
        String s = "PAYPALISHIRING";
        String convertStr = convertZ_3(s, 2);
        System.out.println(convertStr);
    }

    /**
     * 直接构造
     * =============================================================================
     * 我们来研究方法一中矩阵的每个非空字符会对应到 s 的哪个下标（记作 idx），从而直接构造出答案。
     * 由于 Z 字形变换的周期为 t=2r−2，因此对于矩阵第一行的非空字符，其对应的 idx 均为 t 的倍数，
     * 即 idx ≡ 0 (mod t)；同理，对于矩阵最后一行的非空字符，应满足 idx ≡ r−1(mod t)。
     * 对于矩阵的其余行（行号设为 i），每个周期内有两个字符，第一个字符满足 idx ≡ i (mod t)，第二个字
     * 符满足 idx ≡ t−i (mod t)。
     * idx ≡ 0 (mod t)： 当我们将dx除以t时，余数为0。换句话说，dx是t的倍数
     *
     * @param s       源字符串
     * @param rowNums 指定转换的行数
     * @return Z型变换后的字符串
     */
    public static String convertZ_3(String s, int rowNums) {
        int n = s.length(), r = rowNums;
        if (r == 1 || r >= n) {
            return s;
        }
        // Z 字形变换的周期
        int t = r * 2 - 2;
        StringBuilder ans = new StringBuilder();
        // 枚举矩阵的行
        for (int i = 0; i < r; i++) {
            // 枚举每个周期的起始
            for (int j = 0; j < n - i; j += t) {
                ans.append(s.charAt(j + i));  // 当前周期的第一个字符
                if (0 < i && i < r - 1 && j + t - i < n) {
                    ans.append(s.charAt(j + t - i)); // 当前周期的第二个字符
                }
            }
        }
        return ans.toString();
    }

    /**
     * 压缩矩阵空间
     * ===========================================================================
     * 方法一中的矩阵有大量的空间没有被使用，能否优化呢？
     * 注意到每次往矩阵的某一行添加字符时，都会添加到该行上一个字符的右侧，且最后组成答案时只会用
     * 到每行的非空字符。因此我们可以将矩阵的每行初始化为一个空列表，每次向某一行添加字符时，添加
     * 到该行的列表末尾即可。
     *
     * @return Z型转换后的字符串
     */
    public static String convertZ_2(String s, int rowNums) {
        int n = s.length(), r = rowNums;
        if (r == 1 || r >= n) {
            return s;
        }
        // 算周期是为了计算矩阵的列，如果将矩阵的每行初始化为一个空列表，这不需要计算周期，和列
        StringBuffer[] mat = new StringBuffer[r];
        // 给矩阵的每行初始化为一个空列表
        for (int i = 0; i < r; ++i) {
            mat[i] = new StringBuffer();
        }
        int t = r * 2 - 2;
        // x是行的索引
        for (int i = 0, x = 0; i < n; ++i) {
            mat[x].append(s.charAt(i));
            // 每个周期会占用矩阵上的 1+r−2=r−1 列
            if (i % t < r - 1) {
                ++x;
            } else {
                --x;
            }
        }
        // 按行输出转换后的字符串
        StringBuilder ans = new StringBuilder();
        for (StringBuffer row : mat) {
            ans.append(row);
        }
        return ans.toString();
    }

    /**
     * 利用二维矩阵模拟
     * ===========================================================================
     * 设 n 为字符串 s 的长度，r=numRows。对于 r=1（只有一行）或者 r≥n（只有一列）的情况，
     * 答案与 s 相同，我们可以直接返回 s。对于其余情况，考虑创建一个二维矩阵，然后在矩阵上按 Z 字
     * 形填写字符串 s，最后逐行扫描矩阵中的非空字符，组成答案。
     * 根据题意，当我们在矩阵上填写字符时，会向下填写 r 个字符，然后向右上继续填写 r−2 个字符，最
     * 后回到第一行，因此 Z 字形变换的周期 t=r+r−2=2r−2，每个周期会占用矩阵上的 1+r−2=r−1 列。
     * 因此我们有 ⌈n/t⌉ 个周期（最后一个周期视作完整周期,向上取整通常使用⌈⌉符号表示），乘上每个周
     * 期的列数，得到矩阵的列数：c = ⌈n/t⌉⋅(r−1)
     * 创建一个 r 行 c 列的矩阵，然后遍历字符串 s 并按 Z 字形填写。具体来说，设当前填写的位置为 (x,y)，
     * 即矩阵的 x 行 y 列。初始 (x,y) = (0,0)，即矩阵左上角。若当前字符下标 i 满足 i mod t < r−1，
     * 则向下移动，否则向右上移动。
     * 填写完成后，逐行扫描矩阵中的非空字符，组成答案。
     *
     * @param s       给定字符串
     * @param rowNums 指定转换的行数
     * @return Z型变换后的字符串
     */
    public static String convertZ_1(String s, int rowNums) {
        int n = s.length(), r = rowNums;
        if (r == 1 || r >= n) {
            return s;
        }
        int t = r * 2 - 2;
        int c = (int) Math.ceil((double) n / t) * (r - 1);
        // 向上取整的另一种写法
//        int c = (n + t - 1) / t * (r - 1);
        char[][] mat = new char[r][c];
        for (int i = 0, x = 0, y = 0; i < n; ++i) {
            mat[x][y] = s.charAt(i);
            // 每个周期会占用矩阵上的 1+r−2=r−1 列
            if (i % t < r - 1) {
                ++x; // 向下移动
            } else {
                --x;
                ++y; // 向右上移动
            }
        }
        StringBuilder ans = new StringBuilder();
        for (char[] row : mat) {
            for (char ch : row) {
                ans.append(ch);
            }
        }
        return ans.toString();
    }

}
