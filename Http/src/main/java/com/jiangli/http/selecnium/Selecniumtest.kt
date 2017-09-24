package com.jiangli.http.selecnium

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import us.codecraft.webmagic.selector.Html

/**
 * Created by Jiangli on 2017/9/23.
 */
fun main(args: Array<String>) {

    val url = "http://item.mi.com/product/10000070.html"
//    val url = "http://angularjs.cn/"

    val sCaps = DesiredCapabilities()

    var start = System.currentTimeMillis()

    sCaps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "D:\\phantomjs-1.9.7-windows\\phantomjs.exe")
    System.getProperties().setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe")

//    val cliArgsCap = ArrayList<String>()
//    cliArgsCap.add("--web-security=false")
//    cliArgsCap.add("--ssl-protocol=any")
//    cliArgsCap.add("--ignore-ssl-errors=true")
//    sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
//            cliArgsCap)

    // Control LogLevel for GhostDriver, via CLI arguments
//    sCaps.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,"INFO")

//     val webDriver: WebDriver = PhantomJSDriver(sCaps)
     val webDriver: WebDriver = ChromeDriver(sCaps)

    webDriver.get(url)

    val manage = webDriver.manage()
    //manage.addCookie(cookie);
    manage.timeouts()

//    Thread.sleep(3000)

    val webElement = webDriver.findElement(By.xpath("/html"))
    val content = webElement.getAttribute("outerHTML")

    //# J_list
    println(content)

    var cost = System.currentTimeMillis() - start
    start = System.currentTimeMillis()
    println("cost $cost")

//    val page = Page()
//    page.rawText = content
    println(Html(content, url).`$`("#J_list"))
//    println(Html(content, url).`$`("#main > div > div.pure-u-2-3 > div.panel.ng-scope > ul.list-inline.inner.ng-scope"))

    cost = System.currentTimeMillis() - start
    start = System.currentTimeMillis()
    println("cost $cost")
}