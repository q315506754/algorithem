package com.jiangli.doc.json

import java.util.*

/**
 *
 *
/usr/bin/ansible all2 -m shell -a "df -h"
/usr/bin/ansible all2 -m shell -a "echo \"#!/bin/bash\" > /root/shell/log-clear.sh"
/usr/bin/ansible all2 -m shell -a "echo "" > /root/shell/log-clear.sh"
/usr/bin/ansible all2 -m shell -a "echo 'echo 暂未实现逻辑' >> /root/shell/log-clear.sh"
/usr/bin/ansible all2 -m shell -a "chmod +x /root/shell/log-clear.sh"
/usr/bin/ansible all2 -m shell -a "/root/shell/log-clear.sh"

/usr/bin/ansible cas -m shell -a "echo \#!/bin/bash > /root/shell/log-clear.sh"

vim /root/shell/log-clear.sh
 /root/shell/log-clear.sh

echo '清理 dubboadmin 日志'
echo '' > /usr/local/dubbo-tomcat-8.5.16/logs/catalina.out
echo '未实现 aries-commons 清理'

echo '清理 aries-cas 日志'
echo '' > /opt/aries-cas/logs/catalina.out
echo '清理 aries-server 日志'
echo '' > /opt/logs/aries-server.log
echo '清理 aries-user 日志'
echo '' > /opt/logs/aries-user.log

echo '清理 xxx 日志'
echo '' > /opt/logs/xxx.log
echo '' > /opt/logs/xxx-1.0.0.log

aries-erp-server
aries-forum-server
aries-survey
org-app-server
org-server
org-manage w

echo '清理 xxx 日志'
echo '' > /usr/local/logs/xxx.log
echo '' > /usr/local/logs/xxx-1.0.0.log

cd /opt/logs
ll -h

172.16.51.249 1
172.16.51.250 1
172.16.51.251 1
172.16.51.252 1
172.16.51.253 1
172.16.51.254 1
172.16.51.255 1
172.16.52.0 1
172.16.52.1 1
172.16.52.10 1
172.16.52.11 1
172.16.52.12 1
172.16.52.13 1
172.16.52.14 1
172.16.52.17 1
172.16.52.18 1
172.16.52.19 1
172.16.52.2 1
172.16.52.22 1
172.16.52.27 1
172.16.52.28 1
172.16.52.3 1
172.16.52.4 1
172.16.52.5 1
172.16.52.7
172.16.52.8
172.16.52.9 1

 * @author Jiangli
 * @date 2020/9/27 9:20
 */
fun main(args: Array<String>) {

    var str  = """
        内网ip
172.16.51.252

172.16.51.255

172.16.51.249


172.16.52.1
172.16.52.11

172.16.52.0


172.16.51.254





172.16.52.3





172.16.51.251




172.16.52.4




172.16.51.250



172.16.52.5



172.16.52.8








172.16.52.7








172.16.52.9






172.16.52.10






172.16.51.253



　172.16.52.22

　172.16.52.23
　172.16.52.24
　172.16.52.25
　172.16.52.26
172.16.52.17
172.16.52.18

172.16.52.19

172.16.52.20
172.16.52.21
172.16.52.12

172.16.52.13
172.16.52.14
172.16.52.15
172.16.52.16
172.16.52.27
172.16.52.28
172.16.52.28
172.16.52.29
172.16.52.30
172.16.52.31

172.16.52.2











vpc网络
vpc网络

vpc网络
    """.trimIndent()

//    var set = mutableSetOf<String>()
    var set = TreeSet<String>()
    var disabled = """
        172.16.52.1
        　172.16.52.23
　172.16.52.24
　172.16.52.25
　172.16.52.26
172.16.52.20
172.16.52.21
172.16.52.15
172.16.52.16
172.16.52.29
172.16.52.30
172.16.52.31
    """.trimIndent().split("\n").map { it.trim() }.filter { it.isNotBlank() }.toSet()

    str.split("\n").forEach {
        var itX = it.trim()
        if (itX.isBlank()) {
            return@forEach
        }
        if (!itX.contains(".")) {
            return@forEach
        }
        if (disabled.contains(itX)) {
            return@forEach
        }
//        println(itX)
        set.add(itX)
    }
    set.forEach {
        println(it)
    }
}