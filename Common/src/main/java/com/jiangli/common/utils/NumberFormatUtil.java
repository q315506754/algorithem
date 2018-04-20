package com.jiangli.common.utils;

public class NumberFormatUtil {
    static String CHN_NUMBER[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    static String CHN_UNIT[] = {"", "十", "百", "千"};          //权位
    static String CHN_UNIT_SECTION[] = {"", "万", "亿", "万亿"}; //节权位

    /**
     * 阿拉伯数字转换为中文数字的核心算法实现。
     */
    public static String numberToChn(int num) {
        StringBuffer returnStr = new StringBuffer();
        Boolean needZero = false;
        int pos=0;           //节权位的位置
        if(num==0){
            //如果num为0，进行特殊处理。
            returnStr.insert(0,CHN_NUMBER[0]);
        }
        while (num > 0) {
            int section = num % 10000;
            if (needZero) {
                returnStr.insert(0, CHN_NUMBER[0]);
            }
            String sectionToChn = SectionNumToChn(section);
            //判断是否需要节权位
            sectionToChn += (section != 0) ? CHN_UNIT_SECTION[pos] : CHN_UNIT_SECTION[0];
            returnStr.insert(0, sectionToChn);
            needZero = ((section < 1000 && section > 0) ? true : false); //判断section中的千位上是不是为零，若为零应该添加一个零。
            pos++;
            num = num / 10000;
        }
        String numberToChn = returnStr.toString();
        if(numberToChn.startsWith("一十")){
            numberToChn = numberToChn.substring(1);
        }
        return numberToChn;
    }

    /**
     * 将四位的section转换为中文数字
     * @param section
     * @return
     */
    public static String SectionNumToChn(int section) {
        StringBuffer returnStr = new StringBuffer();
        int unitPos = 0;       //节权位的位置编号，0-3依次为个十百千;

        Boolean zero = true;
        while (section > 0) {

            int v = (section % 10);
            if (v == 0) {
                if ((section == 0) || !zero) {
                    zero = true; /*需要补0，zero的作用是确保对连续的多个0，只补一个中文零*/
                    //chnStr.insert(0, chnNumChar[v]);
                    returnStr.insert(0, CHN_NUMBER[v]);
                }
            } else {
                zero = false; //至少有一个数字不是0
                StringBuffer tempStr = new StringBuffer(CHN_NUMBER[v]);//数字v所对应的中文数字
                tempStr.append(CHN_UNIT[unitPos]);  //数字v所对应的中文权位
                returnStr.insert(0, tempStr);
            }
            unitPos++; //移位
            section = section / 10;
        }
        return returnStr.toString();
    }


    //将整数转换成汉字数字  
    public static String formatInteger(int num) {
        return numberToChn(num);
    }


    private static void print(Object arg0) {
        System.out.println(arg0);
    }  
      
    public static void main(String[] args) {
        int num = 245000006;  
        String numStr = formatInteger(num);
        print("num= " + num + ", convert result: " + numStr);  
        double decimal = 245006.234206;  
        print("============================================================");  

        print(formatInteger(0));
        print(formatInteger(9));
        print(formatInteger(10));
        print(formatInteger(11));
        print(formatInteger(15));
        print(formatInteger(20));
        print(formatInteger(100));
        print(formatInteger(1000));
        print(formatInteger(10000));
        print(formatInteger(100000));
        print(formatInteger(100001));
        print(formatInteger(101001));
        print(formatInteger(1000000));
        print(formatInteger(10000000));
    }
}  