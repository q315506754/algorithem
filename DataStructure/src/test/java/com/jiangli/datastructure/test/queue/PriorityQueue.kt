package com.jiangli.datastructure.test.queue

import com.jiangli.datastructure.test.BaseTest
import org.junit.Test
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2018/5/17 14:33
 */
open class PriorityQueueKt: BaseTest() {

    @Test
    fun queryByUserId() {
        //二叉最小堆
//        小根堆性质
//        一棵完全二叉树
        val q = PriorityQueue<Any>()
        q.offer("3")
        println(q)
        q.offer("1")
        println(q)
        q.offer("4")
        println(q)
        q.offer("2")
//        q.offer(4)
//        q.offer(2)
        println(q)
        println(q.toString())
        q.poll()
//        q.offer(4)
//        q.offer(2)
        println(q)
    }
}