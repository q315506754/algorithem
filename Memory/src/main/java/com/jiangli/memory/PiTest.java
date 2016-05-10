package com.jiangli.memory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class PiTest {
    public static void main(String[] args) {
        System.out.println(Math.PI);
        List<String> list = new LinkedList<String>();
        String pi100 = "3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679";
        String pi200 = "8214808651 3282306647 0938446095 5058223172 5359408128 4811174502 8410270193 8521105559 6446229489 5493038196";
        String pi300 = "4428810975 6659334461 2847564823 3786783165 2712019091 4564856692 3460348610 4543266482 1339360726 0249141273";
        String pi400 = "7245870066 0631558817 4881520920 9628292540 9171536436 7892590360 0113305305 4882046652 1384146951 9415116094";

//		list.add(pi100);
//		list.add(pi200);
//		list.add(pi300);
//		list.add(pi400);
        try {
            BufferedReader rb = new BufferedReader(new InputStreamReader(PiTest.class.getClassLoader().getResourceAsStream("src/pi.txt")));
            String readL = null;
            while (true) {
                readL = rb.readLine();
                if (readL == null) {
                    break;
                }
                list.add(readL);
//				System.out.println(readL);
            }
            rb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        easyRemember(list.toArray(new String[list.size()]), 2, 25);

    }

    public static void easyRemember(String[] pis, int interval, int lineNums) {
        String sum = "";
        for (String sOne : pis) {
            sOne = sOne.replaceAll(" ", "");
            sum += sOne;

        }
        easyRemember(sum, interval, lineNums);
    }

    public static void easyRemember(String pi, int interval, int lineNums) {
        int length = pi.length();
        System.out.print(pi.charAt(0) + "" + pi.charAt(1));
        int eachLineCount = 0;
        lineNums = interval * lineNums;
        int lineFlag = 1;
        for (int i = 2; i < length; ) {
            char charAt = pi.charAt(i);
            System.out.print(charAt);
            i++;
            eachLineCount++;
            if (i % interval == 0) {
                System.out.print(" ");
            }
            if (eachLineCount % lineNums == 0) {
                eachLineCount = 0;
                System.out.println();

                lineFlag++;

                System.out.print(lineFlag + "  ");
            }
        }
    }
}
