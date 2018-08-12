package com.jiangli.springMVC

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.util.WebUtils
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
@RequestMapping(value = "/kotlin")
class KotlinController{
    private val logger = LoggerFactory.getLogger(this.javaClass)

    constructor(){
        logger.debug("KotliyyyxnController constructor!!!")
    }

    @Autowired
    private val session: HttpSession? = null

    @Autowired
    private val servletContext: ServletContext? = null


    /**
    * http://localhost:8080/kotlin/rmi.go
    *
    * @param id
    *
    * @ return
    */
    @RequestMapping(value = "/rmi", method = arrayOf(RequestMethod.POST, RequestMethod.GET), produces = arrayOf("application/json;charset=utf-8"))
    @ResponseBody
    fun rmi(id:String?, request: HttpServletRequest):String
    {
        var guguday = WebUtils.getSessionAttribute (request, "guguday")
        logger.debug("guguday is:{}", guguday);

        try {
            logger.debug("servletContext {}", servletContext);
            logger.debug("web getSessionId {}", WebUtils.getSessionId(request));
            logger.debug("web getSessionAttribute-aaa {}", request.getAttribute("aaa"));
            logger.debug("web getSessionAttribute-bbb {}", request.getAttribute("bbb"));
        } catch (e:Exception) {
            e.printStackTrace()
        }

        return "execute..." + java.lang.String.valueOf("ddd")
    }

    //http://localhost:8080/kotlin/home1
    @RequestMapping("/home1")
    internal fun home(request: HttpServletRequest): String {
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        println(Thread.currentThread())
        println(request)
        return "Hello World!Example~"
    }

    //http://localhost:8080/kotlin/home2
    @RequestMapping("/home2")
    internal fun home2(request: HttpServletRequest): String {
        println(Thread.currentThread())
        println(request)
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        return "Hello World!Example~"
    }

    //http://localhost:8080/kotlin/home3
    @RequestMapping("/home3")
    internal fun home3(request: HttpServletRequest): String {
        println(Thread.currentThread())
        println(request)
        //AnnotationConfigEmbeddedWebApplicationContext
        //        or AnnotationConfigApplicationContext
        return "Hello World!Example~"
    }
}

//class B{}
