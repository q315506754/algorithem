package com.jiangli.other;

/**
 * BrainFuck 编译器
 *
 * @author: chenzhigang
 * @version: date: 2014年8月25日
 * mailto: chenzhigang@foxmail.com
 * blog : http://chenzhigang.github.io/
 * review
 */
public class BrainFuckMachine {
    static int[] datas = new int[2048]; //状态区域
    static int p = 0; //状态区域的指针
    static int pc = 0; //命令的指针

    public static void main(String[] args) {
        //String code ="++++++++++[>++++++++++<-]>++++.+.";
        //String code = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]"
        //        + ">++.>+.+++++++..+++.>++."
        //        + "<<+++++++++++++++.>.+++.------.--------.>+.>.";
        String code = "++++++++[>+>++>+++>++++>+++++>++++++>+++++++>++++++++>+++++++++>++++++++++>+++++++++++>++++++++++++>+++++++++++++>++++++++++++++>+++++++++++++++>++++++++++++++++<<<<<<<<<<<<<<<<-]>>>>>>>>>>>>>>>-.+<<<<<<<<<<<<<<<>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<>>>>>>>>>>>>+++.---<<<<<<<<<<<<>>>>>>>>>>>>>>-.+<<<<<<<<<<<<<<>>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<<>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<>>>>>>--.++<<<<<<>>>>>>>>>>>>>.<<<<<<<<<<<<<>>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<<>>>>>>>>>>>>>>---.+++<<<<<<<<<<<<<<>>>>>>>>>>>>>>----.++++<<<<<<<<<<<<<<.";
        run(code);
    }

    public static void run(String code) {
        char[] cmds = code.toCharArray();
        while (true) {
            switch (cmds[pc]) {
                case '>':
                    p++;
                    break;
                case '<':
                    p--;
                    break;
                case '+':
                    datas[p]++;
                    break;
                case '-':
                    datas[p]--;
                    break;
                case '.':
                    System.out.print((char) datas[p]);
                    break;
                case ',':
                    break;
                case '[':
                    if (datas[p] == 0) {
                        findNext(cmds);
                    }
                    break;
                case ']':
                    if (datas[p] != 0) {
                        findPre(cmds);
                    }
                    break;
            }
            pc++;
            if (pc > cmds.length - 1) {
                System.out.println();
                System.out.println("程序运行结束 !");
                break;
            }
        }
    }

    /**
     * 向后找 ]
     *
     * @param cmds
     */
    public static void findNext(char[] cmds) {
        while (true) {
            pc++;
            if (pc > cmds.length - 1) {
                System.out.println("代码不符合语法规范");
                return;
            }
            if (cmds[pc] == ']') {
                return;
            }

        }
    }

    /**
     * 向前找 [
     *
     * @param cmds
     */
    public static void findPre(char[] cmds) {
        while (true) {
            pc--;
            if (pc < 0) {
                System.out.println("代码不符合语法规范");
                return;
            }
            if (cmds[pc] == '[') {
                return;
            }
        }
    }
}