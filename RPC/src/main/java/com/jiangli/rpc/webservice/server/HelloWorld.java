package com.jiangli.rpc.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebService;  
import javax.jws.soap.SOAPBinding;  
import javax.jws.soap.SOAPBinding.Style;  
import javax.xml.ws.Endpoint;  
  
@WebService  
@SOAPBinding(style=Style.RPC)  
public class HelloWorld {  
  
    @WebMethod  
    public String sayHello(String name){  
        System.out.println(name);  
        return "hello  "+name;  
    }  
    public static void main(String[] args) {
        System.out.println("start publish...");
        //http://localhost:8888/helloworld?wsdl
        Endpoint.publish("http://localhost:8888/helloworld", new HelloWorld());
        System.out.println(" publish over...");

    }
}  