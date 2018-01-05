package com.jiangli.jvmlanguage.cmd

import com.jiangli.jvmlanguage.Consts.pixelFactor
import com.jiangli.jvmlanguage.GlobalContext
import com.jiangli.jvmlanguage.analyseClear
import com.jiangli.jvmlanguage.clearMobileTempDir
import com.jiangli.jvmlanguage.loopTimes
import java.io.BufferedReader
import java.io.InputStreamReader

class CmdThread : Runnable{
    override fun run() {
        val reader = BufferedReader(InputStreamReader(System.`in`))

        val mp = mutableMapOf<String, Runnable>()
        mp.put("clear", Runnable {
            clearMobileTempDir()
            analyseClear()
        })
        mp.put("stop", Runnable { loopTimes=0 })
        mp.put("log", Runnable { GlobalContext.print = !GlobalContext.print })

        while (true) {
            try {
                val readLine = reader.readLine().trim()

                val kFunction = mp[readLine]
//                kFunction?.call() //执行
                kFunction?.run() //执行

                //special cmd
                when {
                    readLine.startsWith("-") -> pixelFactor = pixelFactor - readLine.substring(1).toDouble()
                    readLine.startsWith("+") -> pixelFactor = pixelFactor + readLine.substring(1).toDouble()
                    readLine.startsWith("=") -> pixelFactor=(readLine.substring(1).toDouble())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}