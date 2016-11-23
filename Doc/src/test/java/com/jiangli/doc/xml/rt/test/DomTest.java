package com.jiangli.doc.xml.rt.test;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.DefaultBeanDefinitionDocumentReader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jiangli
 * @date 2016/11/22 10:05
 */
public class DomTest {
    @Test
    public void test_aaaa() throws Exception {
        ClassLoader classLoader = DomTest.class.getClassLoader();
        URL resource = classLoader.getResource("test/dispatcher.xml");
        InputStream resourceAsStream = classLoader.getResourceAsStream("test/dispatcher.xml");
        InputSource inputSource = new InputSource(resourceAsStream);
        System.out.println(resource);
        System.out.println(resourceAsStream);
        System.out.println(inputSource);

        boolean namespaceAware = true;
        EntityResolver entityResolver=null;
        ErrorHandler errorHandler=new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.err.println("warning~~~"+exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.err.println("error~~~"+exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.err.println("fatalError~~~"+exception);
            }
        };
//        errorHandler=null;
        String profileSpec = "pro,test,dev";

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(namespaceAware);
        factory.setValidating(true);
//        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");

        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        if (entityResolver != null) {
            docBuilder.setEntityResolver(entityResolver);
        }
        if (errorHandler != null) {
            docBuilder.setErrorHandler(errorHandler);
        }

        Document doc = docBuilder.parse(inputSource);
        System.out.println(doc.getClass().getCanonicalName());
        System.out.println(doc.getDoctype());
//        doc.get

        Element root = doc.getDocumentElement();
        System.out.println(root);
        System.out.println(root.getClass().getCanonicalName());

        String namespaceURI = root.getNamespaceURI();
        System.out.println("namespaceURI:"+namespaceURI);


        String split = "----------------------------------------------------------";
        System.out.println(split);
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);

            if (node instanceof Element) {
                Element ele = (Element) node;
               System.out.println("Element:"+ele+" tagName:"+ele.getTagName());
            }else{
                System.out.println("Node:"+node);
            }

            NamedNodeMap attributes = node.getAttributes();

            System.out.println("detail:"+ org.apache.commons.beanutils.BeanUtils.describe(node));
            System.out.println("attributes:"+ attributes);
            if (attributes != null && attributes.getLength() > 0) {
                for (int j = 0; j < attributes.getLength(); j++) {
                    Node item = attributes.item(j);
                    System.out.println(String.format("attribute%d Node:%s ",j,item));
                    showNodeInfo(item);
                }
            }
            showNodeInfo(node);
            System.out.println(split);
        }

        String[] specifiedProfiles = StringUtils.tokenizeToStringArray(
                profileSpec, BeanDefinitionParserDelegate.MULTI_VALUE_ATTRIBUTE_DELIMITERS);
        System.out.println(Arrays.toString(specifiedProfiles));


        Properties mappings =
                PropertiesLoaderUtils.loadAllProperties("META-INF/spring.handlers", classLoader);
        System.out.println(mappings);
        Map<String, Object> handlerMappings = new ConcurrentHashMap<String, Object>(mappings.size());
        CollectionUtils.mergePropertiesIntoMap(mappings, handlerMappings);
        System.out.println(handlerMappings);


        Enumeration<URL> resources = classLoader.getResources("META-INF/spring.handlers");
        System.out.println(resources);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url);
        }

        DefaultBeanDefinitionDocumentReader dbdd = BeanUtils.instantiateClass(DefaultBeanDefinitionDocumentReader.class);
        System.out.println(dbdd);


    }

    private void showNodeInfo(Node node) {
        System.out.println(String.format("info-getNodeName:%s getNodeValue:%s getLocalName:%s ",node.getNodeName(),node.getNodeValue(),node.getLocalName()));
    }


}
