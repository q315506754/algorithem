package com.jiangli.bytecode;

/**
 * @author Jiangli
 * @date 2018/5/29 11:13
 */
public class BytecodeTest {
    public static void main(String[] args) {
        int a = 0;
        if (a==1) {
            //IF_ICMPNE  pass goto nextCmd
            //jump ret
            System.out.println("a==1");
        } else {
            //IF_ICMPNE not pass there
            System.out.println("a!=1");
        }

        //nextCmd   IF_ICMPNE
        if (a==1) {
            //IF_ICMPNE(1)  pass goto here
            //jump ret
            System.out.println("1111");
        } else if(a==2){
            //IF_ICMPNE(1) not pass there
            //IF_ICMPNE(2) pass there
            //jump ret
            System.out.println("2222");
        } else if(a==3){
            //IF_ICMPNE(2) not pass there
            //IF_ICMPNE(3) pass there
            //jump ret
            System.out.println("333");
        } else {
            //IF_ICMPNE(3) not pass there
            System.out.println("else");
        }

        //ret   RETURN
    }

}
