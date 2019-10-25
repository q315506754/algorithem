package com.jiangli.springMVC;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2019/10/17 15:50
 */
public class MvcUrlHelper implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private ApplicationContext applicationContext;
    static Map<String, RequireResource> urlToResourceMap = new HashMap<>();
    static Map<String, Set<String>> resourceToUrlMap = new HashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("onApplicationEvent:" + event);
        //WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        System.out.println("bean" + bean);

        //TblCompanyMenuOpenService menuOpenService = applicationContext.getBean(TblCompanyMenuOpenService.class);

        //List<TblCompanyMenuOpenDto> menuOpenDtos = menuOpenService.queryAllMenuResource();
        //menuOpenDtos.stream().collect(Collectors.toMap(TblCompanyMenuOpenDto::getCode,dto->dto));

        Class<AbstractHandlerMethodMapping> pcls = AbstractHandlerMethodMapping.class;
        try {
            Field field = pcls.getDeclaredField("mappingRegistry");
            field.setAccessible(true);

            Object o = field.get(bean);

            //MappingRegistry
            Class<?> registryCls = o.getClass();
            Field mappingLookup = registryCls.getDeclaredField("mappingLookup");
            mappingLookup.setAccessible(true);
            Map<RequestMappingInfo, HandlerMethod> map = (Map<RequestMappingInfo, HandlerMethod>) mappingLookup.get(o);

            for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
                RequestMappingInfo info = entry.getKey();
                //String name = info.getName();
                Set<String> patterns = null;
                PatternsRequestCondition patternsCondition = info.getPatternsCondition();
                if (patternsCondition != null) {
                    patterns = patternsCondition.getPatterns();
                }

                HandlerMethod value = entry.getValue();

                RequireResource methodAnnotation = value.getMethodAnnotation(RequireResource.class);
                if (methodAnnotation != null && methodAnnotation.isMainPage()) {
                    System.out.println(methodAnnotation);

                    for (String pattern : patterns) {
                        urlToResourceMap.put(pattern, methodAnnotation);
                    }

                    for (String resource : methodAnnotation.value()) {
                        Set<String> strings = resourceToUrlMap.get(resource);
                        if (strings == null) {
                            strings = new LinkedHashSet<>();
                            resourceToUrlMap.put(resource, strings);
                        }

                        strings.addAll(patterns);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
