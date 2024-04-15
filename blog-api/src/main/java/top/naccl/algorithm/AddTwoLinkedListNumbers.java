package top.naccl.algorithm;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Java之两个链表数字相加
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class AddTwoLinkedListNumbers {

    public static void main(String[] args) {
        // 定义两个链表
        ListNode v1 = new ListNode(2);
        v1.next = new ListNode(4);
        v1.next.next = new ListNode(3);

        ListNode v2 = new ListNode(5);
        v2.next = new ListNode(6);
        v2.next.next = new ListNode(4);
        // 计算两个链表的和
        ListNode outputNode = addTwoNumbers(v1, v2);

        // 打印链表
        printListNode("input v1", v1);
        printListNode("input v2", v2);
        printListNode("output", outputNode);
    }

    /**
     * 打印链表
     *
     * @param tips 打印提示
     * @param node listNode节点
     */
    private static void printListNode(String tips, ListNode node) {
        StringBuilder sb = new StringBuilder();
        ListNode listNode = node;
        while (listNode != null) {
            sb.append(listNode.val);
            listNode = listNode.next;
            if (ObjectUtils.isNotEmpty(listNode)) {
                sb.append("->");
            }
        }
        System.out.println(tips + ":" + sb);
    }

    private static ListNode addTwoNumbers(ListNode v1, ListNode v2) {

        ListNode header = new ListNode(0);
        ListNode p1 = v1, p2 = v2, tmp = header;
        int carryBit = 0;

        while (p1 != null || p2 != null) {
            int x = p1 != null ? p1.val : 0;
            int y = p2 != null ? p2.val : 0;
            // 两数相加在加上进位的数
            int sum = x + y + carryBit;
            // 结果中的十位保留做进位
            carryBit = sum / 10;
            // 创建一个新的链表节点，作为temp的下一个
            tmp.next = new ListNode(sum % 10);
            // 将temp更新到新建的节点上
            tmp = tmp.next;
            // 将需要相加的链表向后移动
            if (p1 != null) {
                p1 = p1.next;
            }
            if (p2 != null) {
                p2 = p2.next;
            }
        }
        // 如果最后两个数相加有进位，进位添加到结果链表的末尾
        if (carryBit > 0) {
            tmp.next = new ListNode(carryBit);
        }
        return header.next;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

}
