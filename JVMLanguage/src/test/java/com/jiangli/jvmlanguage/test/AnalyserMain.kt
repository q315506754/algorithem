package com.jiangli.jvmlanguage.test

import com.jiangli.jvmlanguage.analyseAll
import com.jiangli.jvmlanguage.analyseClear
import com.jiangli.jvmlanguage.clearMobileTempDir

fun main(args: Array<String>) {
    clearMobileTempDir()
    analyseClear()
    analyseAll()
}