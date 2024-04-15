package top.naccl.algorithm;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
public class DeepFirstSearch {

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

        List<GraphNode> visited = new ArrayList<>();
        dfs(node1,visited);
    }

    public static GraphNode dfs(GraphNode node, List<GraphNode> visited){
        if(visited.contains(node)){
            return node;
        }

        visited.add(node);
        System.out.println(node.getLabel());
        if(!node.childList.isEmpty()){
            for(int i = 0; i<node.childList.size(); i++){
                GraphNode graphNode = node.getChildList().get(i);
                dfs(graphNode, visited);
            }
        }
        return node;
    }

    @Data
    public static class GraphNode{
        private Integer label;
        private List<GraphNode> childList = new ArrayList<>();
    }

}
