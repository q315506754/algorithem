package com.jiangli.rpc.webservice.client;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/20020 12:21
 */
public class HelloWorldClient {

    /**
     * wsimport -p com -keep http://localhost:8888/helloworld?wsdl
     *
     * @param args
     */
    public static void main(String args[]) {
        HelloWorldService service = new HelloWorldService();
        HelloWorld helloProxy = service.getHelloWorldPort();
        String hello = helloProxy.sayHello("你好");
        System.out.println(hello);
    }
}
