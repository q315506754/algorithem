package com.jiangli.springMVC.controller.kotl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 *
 *
 * @author Jiangli
 * @date 2017/7/11 16:22
 */
@Controller
@RequestMapping(value = "/kotlin2")
class KotlinController2 {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    constructor(){
        logger.debug("KotlinController2 constructor!!!")
    }

    @Autowired
    private val session: HttpSession? = null

    @Autowired
    private val servletContext: ServletContext? = null

    @Autowired
    private val request: HttpServletRequest? = null



    //http://localhost:8080/kotlin2/home1
    @RequestMapping("/home1")
    internal fun home(request: HttpServletRequest): String {
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        println(Thread.currentThread())
        println(request)
        return "Hello World!Example~"
    }

    //http://localhost:8080/kotlin2/home2
    @RequestMapping("/home2")
    internal fun home2(): String {
        println(Thread.currentThread())
        println(request)
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        return "Hello World!Example~"
    }

    //http://localhost:8080/kotlin2/home3
    @RequestMapping("/home3")
    internal fun home3(): String {
        println(Thread.currentThread())
        println(request)
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        return "Hello World!Example~"
    }
}