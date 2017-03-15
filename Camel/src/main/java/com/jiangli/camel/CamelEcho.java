package com.jiangli.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 *
 * to get more components,click
 * http://camel.apache.org/components.html
 *
 * @author Jiangli
 * @date 2017/3/15 8:50
 */
public class CamelEcho {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
//                from("test-jms:queue:test.queue").to("file://test");
                RouteDefinition from = from("stream:in?promptMessage=Enter something: ");
                from.transform(new Expression() {
                    @Override
                    public <T> T evaluate(Exchange exchange, Class<T> type) {
                        System.out.println(""+exchange);
                        System.out.println(""+type);
                        System.out.println(""+type.getTypeParameters());
//                        System.out.println(""+type.getTypeParameters()[0]);
                        try {
                            System.out.println(BeanUtils.describe(exchange));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        T in1 = exchange.getIn(type);
                        Message in = (Message) in1;
                        exchange.getOut();
                        System.out.println(in);
                        Object body = in.getBody();
                        in.setBody(String.valueOf(body).toUpperCase());

                        return in1;
                    }
                });
                from.to("stream:out");
            }
        });



        context.start();
        Thread.sleep(99999);
    }

}
