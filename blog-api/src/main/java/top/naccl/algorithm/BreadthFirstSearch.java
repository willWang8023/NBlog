package top.naccl.algorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 在不需要计算边的属性是可以不要实际的边，直接用List属性代替，如果要计算边的权重信息，就需要通过边来构造图
 *                      1
 *                     / \
 *                    /   \
 *                   2     3
 *                  / \    | \
 *                 /   \   |  \
 *                4     5  6  7
 *                 \     \/   /
 *                  ---->8<--
 */
public class BreadthFirstSearch {

    public static void main(String[] args){
        GraphNode node1 = new GraphNode();
        node1.setLabel(1);
        GraphNode node2 = new GraphNode();
        node2.setLabel(2);
        GraphNode node3 = new GraphNode();
        node3.setLabel(3);
        List<GraphNode> node1List = new ArrayList<>();
        node1List.add(node2);
        node1List.add(node3);
        node1.setChildList(node1List);

        GraphNode node4 = new GraphNode();
        node4.setLabel(4);
        GraphNode node5 = new GraphNode();
        node5.setLabel(5);
        List<GraphNode> node2List = new ArrayList<>();
        node2List.add(node4);
        node2List.add(node5);
        node2.setChildList(node2List);

        GraphNode node6 = new GraphNode();
        node6.setLabel(6);
        GraphNode node7 = new GraphNode();
        node7.setLabel(7);
        List<GraphNode> node3List = new ArrayList<>();
        node3List.add(node6);
        node3List.add(node7);
        node3.setChildList(node3List);

        GraphNode node8 = new GraphNode();
        node8.setLabel(8);

        List<GraphNode> node8List = new ArrayList<>();
        node8List.add(node8);
        node4.setChildList(node8List);
        node5.setChildList(node8List);
        node6.setChildList(node8List);
        bfs(node1);
    }

    public static void bfs(GraphNode node) {
        List<GraphNode> visited = new ArrayList<>(); // 已经被访问过的元素
        Queue<GraphNode> q = new LinkedList<>(); // 用队列存放依次要遍历的元素
        q.offer(node);

        while (!q.isEmpty()) {
            GraphNode currNode = q.poll();
            if (!visited.contains(currNode)) {
                visited.add(currNode);
                System.out.println("节点：" + currNode.getLabel());
                for (int i = 0; i < currNode.childList.size(); i++) {
                    q.offer(currNode.childList.get(i));
                }
            }
        }
    }

    @Data
    public static class GraphNode{
        private Integer label;
        private List<GraphNode> childList = new ArrayList<>();
    }

}
