package com.jiangli.leetcode.test.others;

/**
 * 链表翻转
 A -> B -> C -> NULL


 prev = NULL

 next = A.next
 A.next =prev
 prev = A

 next = B.next
 B.next =prev
 prev = B

 *
 * @author Jiangli
 * @date 2020/5/26 20:10
 */
public class ReverseLinkList {

    static class Node{
        String s;
        Node next;
    }

    public static Node createChain(String... s) {
        Node head = null;
        Node prev = null;
        for (String s1 : s) {
            Node one = new Node();
            one.s = s1;
            if (head == null) {
                head = one;
            }
            if (prev != null) {
                prev.next = one;
            }
            prev = one;
        }
        return head;
    }

    public static void printChain(Node node) {
        if (node == null) {
            System.out.println("NULL");
        } else {
            System.out.print(node.s);
            System.out.print("->");
            printChain(node.next);
        }
    }

    //public static Node reverseChain(Node node) {
    //    Node prev = null;
    //    Node next = node;
    //
    //    while (next != null) {
    //        Node t = next;
    //        next = next.next;
    //        t.next = prev;
    //        prev = t;
    //    }
    //
    //    return prev;
    //}
    public static Node reverseChain(Node node) {
        Node prev = null;
        Node next;

        while (node != null) {
            next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }

        return prev;
    }

    public static void main(String[] args) {
        Node chain = createChain("A", "B", "C");
        printChain(chain);

        Node node = reverseChain(chain);
        printChain(node);
    }
}
