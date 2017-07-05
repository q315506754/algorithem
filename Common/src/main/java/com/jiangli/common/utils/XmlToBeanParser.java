package com.jiangli.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/7/4 16:45
 */
public class XmlToBeanParser<T> {
    private List<T> list;
    
    public   static <T> XmlToBeanParser parse(String tagName, String xmlName, Class<T> cls){
        XmlToBeanParser<T> ret = new XmlToBeanParser<T>();
        List<T> dt = new LinkedList<T>();
        ret.setList(dt);
        
        
        File xmlFile = parseRelativeFile(xmlName);
        
       DocumentBuilderFactory builderFactory =  DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
    
            Document doc = builder.parse(new FileInputStream(xmlFile));
            Element root = doc.getDocumentElement();
            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
    
                String nodeName = item.getNodeName();
    
                if(tagName.equals(nodeName)) {
                    Element element = (Element) item;
    
                    T t = cls.newInstance();
                    dt.add(t);
                    
                    NamedNodeMap attributes = element.getAttributes();
                    for (int j = 0; j < attributes.getLength(); j++) {
                        Node attrOne = attributes.item(j);
                        String attrName = attrOne.getNodeName();
                        String propName=getPropName(attrName);
                        String attrValue = attrOne.getNodeValue().trim();
                        if (StringUtils.isNotEmpty(attrValue)) {
                            BeanUtils.setProperty(t,propName,attrValue);
                        }
                    }
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return ret;
    }
    
    private static String getPropName(String attrName) {
        if("interface".equals(attrName)){
            return "interfaceName";
        }
        return attrName;
    }
    
    private static File parseRelativeFile(String xmlName) {
        String base = new File("").getAbsolutePath() ;
        File dir = new File(base ,"src/main/resources/spring") ;
        return new File(dir,xmlName);
    }
    
    public List<T> getList() {
        return list;
    }
    
    public void setList(List<T> list) {
        this.list = list;
    }
}
