package com.jiangli.jvmlanguage.test

import com.jiangli.jvmlanguage.Consts.analysePath
import com.jiangli.jvmlanguage.screenshot

/**
 *
 *
 * @author Jiangli
 * @date 2018/1/4 14:20
 */
fun main(args: Array<String>) {
    val screenshot = screenshot(analysePath)
    println(screenshot)
}