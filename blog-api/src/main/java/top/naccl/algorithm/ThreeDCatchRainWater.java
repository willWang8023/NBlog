package top.naccl.algorithm;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 简介： 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。以下的内容回答你
 * 的疑惑和困难。<br>
 * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。<br>
 * 示例 1:<br>
 * <img width="500" height="300" src="http://www.willwang.vip/algorithm/3DCatchRainWater.jpg" alt="">
 * <br>
 * 输入: heightMap = [[1,4,3,1,3,2],[3,2,1,3,2,4],[2,3,3,2,3,1]]<br>
 * 输出: 4 <br>
 * 解释: 下雨后，雨水将会被上图蓝色的方块中。总的接雨水量为1+2+1=4。<br>
 * 示例 2:<br>
 * <img width="500" height="300" src="http://www.willwang.vip/algorithm/3DCatchRainWater1.png" alt="">
 * <br>
 * 输入: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]<br>
 * 输出: 10 <br>
 * 提示:<br>
 * m == heightMap.length<br>
 * n == heightMap[i].length<br>
 * 1 <= m, n <= 200<br>
 * 0 <= heightMap[i][j] <= 2 * 104<br>
 */
public class ThreeDCatchRainWater {

    public static void main(String[] args) {
//        Integer[][] heightMap = {{3, 3, 3, 3, 3}, {3, 2, 2, 2, 3}, {3, 2, 1, 2, 3}, {3, 2, 2, 2, 3}, {3, 3, 3, 3, 3}};
        Integer[][] heightMap = {{1, 4, 3, 1, 3, 2}, {3, 2, 1, 3, 2, 4}, {2, 3, 3, 2, 3, 1}};
        int ras = trapRainWater(heightMap);
        System.out.println("res:" + ras);
    }

    /**
     * 如果要存水，那么它的上下左右就必须要比它高才可以
     * <p>
     * 我们先从最外层数组开始，找最低（小）的一个位置开始切入，为什么要这样做呢？因为木桶效应，就是能存多少水由最短的木板决定，超过最短就会溢出了。只要最外层够高那么不管里面有多么复杂，只要内层有位置水都可以存。所以最关键的就是最外层的最低处。
     * <p>
     * 我们就是将整个数组当作木桶来处理的，只不过这个木桶里面有很多大石头而已。从最外层最低处开始判断，它的上下左右比它大还是小。
     * <p>
     * 如果比它大那就说明这个内层（上下左右位）存不了水，就更新整个木桶的最外围，其他位置不变，将这个原来最低的地方换成比它大的内层做最外围；
     * 如果比它小，那就说明可以存水，把比它小的内层（上下左右）填充水至与它同高，接下来再把这些填充过的内层作为最外围；
     * <p>
     * 它的上下左右不是比它大就是比它小，但是不管比它大还是比它小，只要遍历了这个最低处都会更新桶的最外围，然后重新找最低处，继续判断最低处的上下左右，继续更新桶的最外围，直到遍历过所有的位置为止。
     * <p>
     * 每一次遍历，它都会标记已经遍历过的数组防止重复
     * <p>
     * 代码不长，但是处处都是精华哇
     * <p>
     * 这里用到了优先队列，方向数组和三元数组，如果你不知道，我帮你找好了，你去看看再回来继续:-------> 优先队列详解和方向数组详解和三元数组
     * <p>
     * 这里用的是优先队列来储存它们的位置，所以每次poll出来的都是最低位置的数组元素位置，还非常巧妙的用到了方向数组来遍历它的上下左右位置的数组元素
     *
     * @param heights 高度数组
     * @return 可以接收的雨水量
     */
    public static int trapRainWater(Integer[][] heights) {
        if (heights == null || heights.length == 0) return 0;
        int n = heights.length;
        int m = heights[0].length;
        // 用一个vis数组来标记这个位置有没有被访问过
        boolean[][] vis = new boolean[n][m];
        // 优先队列中存放三元组 [x,y,h] 坐标和高度，所以每次poll出来的都是最低位置的数组元素位置
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        // 先把最外一圈放进去
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || i == n - 1 || j == 0 || j == m - 1) {
                    pq.offer(new int[]{i, j, heights[i][j]});
                    vis[i][j] = true;
                }
            }
        }
        int res = 0;
        // 方向数组，把dx和dy压缩成一维来做，{-1,0}:(x-1 y不动),{0,1}:(x不动 y加一),{1,0}:(x加一 y不动),{0,-1}:(x不动 y减一)
        int[] dirs = {-1, 0, 1, 0, -1};
        while (!pq.isEmpty()) {
            // poll中的前两位分别是x和y，poll是优先队列，按照高度又小到大，所以每次poll出来的都是最低位置的数组元素位置
            int[] poll = pq.poll();
            // 看一下周围四个方向，没访问过的话能不能往里灌水
            for (int k = 0; k < 4; k++) {
                int nx = poll[0] + dirs[k];
                int ny = poll[1] + dirs[k + 1];
                // 如果位置合法且没访问过
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && !vis[nx][ny]) {
                    // poll是外围，如果外围这一圈中最小的比当前这个还高，那就说明能往里面灌水啊
                    if (poll[2] > heights[nx][ny]) {
                        res += poll[2] - heights[nx][ny];
                    }
                    // 如果灌水，高度得是你灌水后的高度了，如果没灌水也要取高的
                    pq.offer(new int[]{nx, ny, Math.max(heights[nx][ny], poll[2])});
                    vis[nx][ny] = true;
                }
            }
        }
        return res;
    }


}
