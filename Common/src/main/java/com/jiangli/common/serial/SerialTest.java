package com.jiangli.common.serial;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class SerialTest {

    public static void main(String[] args) throws IOException {
        /* serialVersionUID保持不变*/
        //写入时age为long型12345678912L (超过int范围)
        //将age改成Long型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age
        //将age改成int型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age
        //将age改成Integer型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age

        //写入时age为long型1234L (没有超过int范围)
        //将age改成Long型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age
        //将age改成int型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age
        //将age改成Integer型，java.io.InvalidClassException: com.jiangli.common.serial.Person; incompatible types for field age
        //ps:反序列化时相同的字段名的签名必须完全一样，没有自动转换这一说



         /* 没有serialVersionUID*/
         //写入时age为int型1234567891 (没有超过int范围)
        //将age改成Integer型，java.io.InvalidClassException: com.jiangli.common.serial.Person; local class incompatible: stream classdesc serialVersionUID = -8077718692270080604, local class serialVersionUID = -4106660943636403392
        //将构造参数第三个int改为Integer型，java.io.InvalidClassException: com.jiangli.common.serial.Person; local class incompatible: stream classdesc serialVersionUID = -8077718692270080604, local class serialVersionUID = 6636190109912739804
        //ps:自动生成的serialVersionUID跟构造函数、字段签名有关


//        Person person = new Person(1234, "wang", 12345678912L);
//        Person person = new Person(1234, "wang", 1234L);
        Person person = new Person(1234, "wang", 1234567891);
        System.out.println("Person Serial" + person);
        FileOutputStream fos = new FileOutputStream("Person.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(person);
        oos.flush();
        oos.close();


    }
}