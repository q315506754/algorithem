package com.jiangli.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author Jiangli
 * @date 2017/3/15 8:50
 */
public class CamelMain {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
//                from("test-jms:queue:test.queue").to("file://test");
                from("file:C://a//testSrc.txt").to("file:C://a//test.txt");
            }
        });

//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        // Note we can explicit name the component
//        context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));


        context.start();
        System.out.println("start");
        ProducerTemplate template = context.createProducerTemplate();
        for (int i = 0; i < 10; i++) {
            template.sendBody("file:C://a//testSrc.txt", "Test Message: " + i);
        }
        Thread.sleep(99999);
    }

}
