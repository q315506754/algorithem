package com.jiangli.sort.daily;

import com.jiangli.common.utils.RecurUtil;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Jiangli
 * @date 2020/6/16 10:02
 */
public class RecoverTree {
    //在中序序列中查找与前序序列首结点相同元素的时候，如果使用 while 循环去一个个找效率很慢
    //这里我们借助数据结构 HashMap 来辅助查找，在开始递归之前把所有的中序序列的元素和它们所在的下标存到一个 map 中，这样查找的时间复杂度是 O(logn)
    HashMap<Integer, Integer> map = new HashMap<>();

    //保留的前序遍历
    int[] preorder;

    class  TreeNode {
        TreeNode left;
        TreeNode right;
        int n;

        public TreeNode(int n) {
            this.n = n;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "n=" + n +
                    '}';
        }
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        //在开始递归之前把所有的中序序列的元素和它们所在的下标存到一个 map 中
        for (int i = 0; i < preorder.length; i++) {
            map.put(inorder[i], i);
        }
        //二叉树的重要性质是递归
        return recursive(0,0,inorder.length-1);
    }

    /** 根据前序遍历序列和中序遍历序列重新组建二叉树
     * @param pre_root_idx 前序遍历的索引
     * @param in_left_idx  中序遍历左边界的索引
     * @param in_right_idx 中序遍历右边界的索引
     */
    public TreeNode recursive(int pre_root_idx, int in_left_idx, int in_right_idx) {

        //子树中序遍历为空，说明已经越过叶子节点，此时返回 nul
        if (in_left_idx > in_right_idx) {
            return null;
        }

        //root_idx是在前序里面的
        TreeNode root = new TreeNode(preorder[pre_root_idx]);

        // 通过 map ，根据前序的根节点的值，在中序中获取当前根的索引
        int idx = map.get(preorder[pre_root_idx]);

        //左子树的根节点就是 左子树的(前序遍历）第一个，就是 +1 ,左边边界就是 left ，右边边界是中间区分的idx-1
        root.left = recursive(pre_root_idx + 1, in_left_idx, idx - 1);

        //右子树的根，就是右子树（前序遍历）的第一个,就是当前根节点 加上左子树的数量
        root.right = recursive(pre_root_idx + (idx-1 - in_left_idx +1)  + 1, idx + 1, in_right_idx);

        return root;
    }

    public static void main(String[] args) {
        TreeNode treeNode = new RecoverTree().buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        RecurUtil.print(1,treeNode,it-> Arrays.asList(it.left,it.right));
    }
}
