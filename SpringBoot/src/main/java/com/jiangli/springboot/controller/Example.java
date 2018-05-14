package com.jiangli.springboot.controller;

import com.jiangli.springboot.configs.RunConfigure;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController

//@SpringBootApplication = as follows
//@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
// -Dspring.autoconfigure.exclude
@PropertySource({
        "classpath:temp/jdbc.properties"
        ,"classpath:temp/mysql.properties"
})
@Import(RunConfigure.class)
public class Example implements ApplicationContextAware{
    private ApplicationContext applicationContext;

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

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        System.out.println("corsConfigurer");
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }

    //or define your EmbeddedServletContainerCustomizer bean
//        @Bean
//        public EmbeddedServletContainerFactory servletContainer() {
//            TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
//            factory.setPort(9000);
//            factory.setSessionTimeout(10, TimeUnit.MINUTES);
////            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/src/main/resources/public/error/404.html"));
////            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
////            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"));
////            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/555.html"));
//            return factory;
//        }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("applicationContext:"+applicationContext);
        System.out.println("arguments:"+arguments);
        System.out.println("arguments.getNonOptionArgs:"+arguments.getNonOptionArgs());
        System.out.println("arguments.getOptionNames:"+arguments.getOptionNames());
        System.out.println("randomIntLt10:"+randomIntLt10);
        System.out.println("randomIntRange:"+randomIntRange);
        System.out.println("randomStr:"+randomStr);

    }

//    http://localhost:8080
    @RequestMapping("/")
    @CrossOrigin
    String home(HttpServletRequest request) {
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        System.out.println(Thread.currentThread());
        System.out.println(request);
        return "Hello World!Example~"+applicationContext;
    }

    //http://localhost:8080/home2
    @RequestMapping("/home2")
    @CrossOrigin
    String home2(HttpServletRequest request) {
        System.out.println(Thread.currentThread());
        System.out.println(request);
        //AnnotationConfigEmbeddedWebApplicationContext
//        or AnnotationConfigApplicationContext
        return "Hello World!Example~"+applicationContext;
    }

    public static void main(String[] args) throws Exception {
        //disable  restart for DevTool
//        System.setProperty("spring.devtools.restart.enabled", "false");
//        System.setProperty("logging.path", "false");
//        System.setProperty("logging.file", "drewdrew.log");


//        enable MBean
        System.setProperty("spring.application.admin.enabled", "true");

//        Excluding resources which don’t necessarily need to trigger a restart
//        spring.devtools.restart.exclude=static/**,public/**
        //default /META-INF/maven, /META-INF/resources ,/resources ,/static ,/public or /templates

        //spring.devtools.restart.trigger-file
        //
//        System.setProperty("spring.devtools.restart.trigger-file", "trigger");
//        System.setProperty("spring.devtools.restart.trigger-file", "applicationContext.xml");


        //Customizing the restart classloader
//        META-INF/spring-devtools.properties file.

//        SpringApplication.addListener


        //static invoke
//        SpringApplication.run(Example.class, "--debug");

//        SpringApplication.run(Example.class, args);


        //custom
//        SpringApplication app = new SpringApplication(Example.class);
//        app.setBannerMode(Banner.Mode.OFF);
//        app.run(args);

        //builder Pattern
//        new SpringApplicationBuilder()
//                .sources(Example.class)
////                .child(Application.class)
//                .bannerMode(Banner.Mode.OFF)
//                .run(args);


        SpringApplication app = new SpringApplication(Example.class);

        app.addListeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.println(event);

            }
        });

        app.addListeners(new ApplicationListener<ApplicationStartingEvent>() {
            @Override
            public void onApplicationEvent(ApplicationStartingEvent  event) {
                System.out.println("!!~special~~"+event);
            }
        });

//        app.setWebEnvironment(true);//override

        app.run(args);
//        app.run("--debug");//log debug level (just for a selection of core loggers ) --trace for trace
//


    }

}