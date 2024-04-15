package top.naccl.alogorithm;

import java.util.Stack;

/**
 * 题目描述
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。
 * 思路：
 * 这道题的思路主要就是需要构建两个stack，来解决这个问题，一个栈是用来存每次入栈的数组，另一个栈则是用来存放每次的最小值，
 * 每次入栈的时候就和这个最小值的栈的栈顶的数据进行比较，如果比后压栈的值更小，那么这个值既需要压入这个data栈，也需要压
 * 入这个最小值的栈。然后出站的时候，就拿这个出站的值和最小值的栈顶进行比较如果两个值不一样，说明当前出站的这个值不是最小值，
 * 如果相等，则说明当前出站的值是最小值。这样一来就使得这个最小值的栈的栈顶始终都保存着最小的值。
 */
public class ImplementStackWithMinFunction {

    Stack<Integer> data = new Stack<>();
    Stack<Integer> min = new Stack<>();
    Integer temp = null;

    public void push(int node) {
        if(temp == null){
            temp = node;
            data.push(node);
            min.push(node);
        }else{
            if(node <= temp){
                temp = node;
                min.push(node);
            }
            data.push(node);
        }
    }

    public void pop() {
        int top = data.pop();
        int minTop = min.peek();
        if(top == minTop){
            min.pop();
        }
    }

    public int top() {
        return data.peek();
    }

    public int min() {
        return min.peek();
    }

}
