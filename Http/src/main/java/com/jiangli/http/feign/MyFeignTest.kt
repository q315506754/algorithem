package com.jiangli.http.feign

import com.jiangli.http.FeignService
import feign.Feign
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.gson.GsonDecoder
import feign.jaxrs.JAXRSContract




/**
 * Created by Jiangli on 2017/8/13.
 */

fun main(args: Array<String>) {
    val builder = Feign.builder()
    val github = builder
            //Gson
            .decoder(GsonDecoder())
//            .encoder(GsonEncoder())

            //Jackson   a JSON API.
//            .encoder( JacksonEncoder())
//            .decoder( JacksonDecoder())


            // decode XML in a way that is compatible with normal JVM and also Android environments
//            .decoder(SAXDecoder.builder()
//                    .registerContentHandler(UserIdHandler.class)
//                            .build())

            //JAXB an XML API.
//            .encoder( JAXBEncoder())
//            .decoder( JAXBDecoder())

//    java.lang.IllegalStateException: Method contributorsJAXRS not annotated with HTTP method type (ex. GET, POST)
//            java.lang.IllegalStateException: Method contributors not annotated with HTTP method type (ex. GET, POST)
//            .contract( JAXRSContract())
            .target<FeignService.GitHub>(FeignService.GitHub::class.java!!, "https://api.github.com")

    // Fetch and print a list of the contributors to this library.
    val contributors = github.contributors("OpenFeign", "feign")
    for (contributor in contributors) {
        println(contributor.login + " (" + contributor.contributions + ")")
    }

//    System.err.println("-----------------------")
    System.out.println("-----------------------")

    val gitHubJAXRS = Feign.builder()
//            .mapAndDecode({ response, type -> jsopUnwrap(response, type) }, GsonDecoder())
            .decoder(GsonDecoder())
            .contract(JAXRSContract())
            .requestInterceptor( ForwardedForInterceptor())
            .requestInterceptor( ForwardedForInterceptor())
//            requestInterceptor(new BasicAuthRequestInterceptor(username, password))
//            .logger(Logger.JavaLogger().appendToFile("logs/http.log"))
//            .logLevel(Logger.Level.FULL)
            .target<FeignService.GitHubJAXRS>(FeignService.GitHubJAXRS::class.java!!, "https://api.github.com")
    val contributorsJAXRS = gitHubJAXRS.contributorsJAXRS("OpenFeign", "feign")
    for (contributor in contributorsJAXRS) {
        println(contributor.login + " (" + contributor.contributions + ")")
    }
}

class ForwardedForInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        println("aaa")
        template.header("X-Forwarded-For", "origin.host.com")
    }
}
