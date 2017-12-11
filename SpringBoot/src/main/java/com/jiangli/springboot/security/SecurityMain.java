package com.jiangli.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityMain   {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SampleService service;

    //CommandLineRunner interfaces rovides access to application arguments as a simple string array
    //public void run(String... args) {
    @Autowired
    private ApplicationArguments arguments;

    @Value("${random.int(10)}")
    private int randomIntLt10;
    @Value("${random.int[1024,65536]}")//[min,max)
    private int randomIntRange;
    @Value("${random.value}")
    private String randomStr;

    public static void main(String[] args) throws Exception {

        //static invoke
//        SpringApplication.run(SecurityMain.class, "--debug");
        SpringApplication.run(SecurityMain.class);

    }

//    @Bean
//    public UrlBasedViewResolver urlBasedViewResolver()
//    {
//        UrlBasedViewResolver res = new InternalResourceViewResolver();
////        res.setViewClass(JstlView.class);
//        res.setPrefix("/public/common");
////        res.setPrefix("/WEB-INF/jsp");
//        res.setSuffix(".jsp");
//        return res;
//    }

    //    http://localhost:8080
    @RequestMapping("/")
    @CrossOrigin
    String home() {
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext

        SecurityContextHolder.getContext()
//                .setAuthentication(new UsernamePasswordAuthenticationToken("user", "N/A",
                .setAuthentication(new UsernamePasswordAuthenticationToken("user", "1234",
                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
        try {
            System.out.println(this.service.secure());
        }
        finally {
            SecurityContextHolder.clearContext();
        }

        return "Hello World!SecurityMain~" + applicationContext;
    }



}