package com.jiangli.leetcode.test.practice;

//运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
//
//
//
// 实现 LRUCache 类：
//
//
// LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
// int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
// void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上
//限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
//
//
//
//
//
//
// 进阶：你是否可以在 O(1) 时间复杂度内完成这两种操作？
//
//
//
// 示例：
//
//
//输入
//["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
//[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
//输出
//[null, null, null, 1, null, -1, null, -1, 3, 4]
//
//解释
//LRUCache lRUCache = new LRUCache(2);
//lRUCache.put(1, 1); // 缓存是 {1=1}
//lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//lRUCache.get(1);    // 返回 1
//lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//lRUCache.get(2);    // 返回 -1 (未找到)
//lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//lRUCache.get(1);    // 返回 -1 (未找到)
//lRUCache.get(3);    // 返回 3
//lRUCache.get(4);    // 返回 4
//
//
//
//
// 提示：
//
//
// 1 <= capacity <= 3000
// 0 <= key <= 3000
// 0 <= value <= 104
// 最多调用 3 * 104 次 get 和 put
//
// Related Topics 设计


import org.junit.Test;

public class q460_lfu_dblink {

    //leetcode submit region begin(Prohibit modification and deletion)
    //leetcode submit region begin(Prohibit modification and deletion)
    class LFUCache {
        int CAPACITY;
        Node head;

        class Node {
            int k;
            int v;
            int viTimes=0;
            Node next;
            Node prev;
        }

        public LFUCache(int capacity) {
            this.CAPACITY = capacity;
        }

        public void _link(String get) {
            System.out.print(get+" ");
            Node from = head;
            while (from != null) {
                System.out.print(from.v+ "(" + from.viTimes + ")" +" -> ");
                from = from.next;
            }
            System.out.println();
        }

        public Node _findNode(int key) {
            //find
            Node from = head;
            while (from != null) {
                if (from.k == key) {
                    break;
                }
                from = from.next;
            }

            //fu
            if (from != null){
                from.viTimes++;
            }

            //re-pos
            if (from != null) {
                if (from.prev != null) {
                    from.prev.next = from.next;
                }

                if (from.next != null) {
                    from.next.prev = from.prev;
                }

                _insertFromHead(from,false);
            }

            return from;
        }

        public void _insertFromHead(Node key, boolean check) {
            Node from = head;
            Node tpre = null;
            //find position
            while (from != null) {
                if (from.viTimes <= key.viTimes) {
                    break;
                }
                tpre = from;
                from = from.next;
            }

            //insert after pos
            if (from == null) {
                key.prev = tpre;
                key.next = null;
                if (tpre != null) {
                    tpre.next = key;
                }
            } else {
                key.prev = from.prev;
                from.prev = key;
                if (key.prev != null) {
                    key.prev.next = key;
                }
                key.next = from;
            }

            if (tpre == null) {
                head= key;
            }


            if (check) {
                int i = 0;
                from = head;
                while (from != null) {
                    i++;
                    if (i == CAPACITY && from.next != null) {
                        from.next = null;
                        break;
                    }
                    from = from.next;
                }
            }
        }

        public int get(int key) {
            Node node = this._findNode(key);

            _link("get");
            return node !=null?node.v:-1;
        }

        public void put(int key, int value) {
            Node node = this._findNode(key);
            if (node != null) {
                node.v = value;
            } else {
                Node first = new Node();
                first.k = key;
                first.v = value;
                _insertFromHead(first,true);
            }

            _link("put");
        }
    }

    @Test
    public void test_aa() {
        LFUCache one = new LFUCache(2);
        one.put(1,1);
        one.put(2,2);
        System.out.println(one.get(1));
        one.put(3,3);
        System.out.println(one.get(2));
        System.out.println(one.get(3));
        one.put(4,4);
        System.out.println(one.get(1));
        System.out.println(one.get(3));
        System.out.println(one.get(4));
    }



/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
//leetcode submit region end(Prohibit modification and deletion)

}
