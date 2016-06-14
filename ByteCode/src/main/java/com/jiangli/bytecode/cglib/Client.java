package com.jiangli.bytecode.cglib;

public class Client {

    public static void main(String[] args) {
        BookServiceBean service = BookServiceFactory.getInstance();
        doMethod(service);
    }

    public static void doMethod(BookServiceBean service) {
        service.create();
        service.update();
        service.query();
        service.delete();

        System.out.println(service.getClass());


        BookServiceBean service1 = BookServiceFactory.getProxyInstance(new MyCglibProxy("boss"));
        service1.create();
        service1.create();
        BookServiceBean service2 = BookServiceFactory.getProxyInstance(new MyCglibProxy("john"));
        service2.create();
        service2.create();

        System.out.println(service1.getClass());
        System.out.println(service1.getClass().getSuperclass());
        System.out.println(service2.getClass());
        System.out.println(service2);

        BookServiceBean service3 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("john"));
        service3.query();
        BookServiceBean service4 = BookServiceFactory.getProxyInstanceByFilter(new MyCglibProxy("john"));
        service4.query();
    }
}   