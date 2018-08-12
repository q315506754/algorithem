package com.jiangli.datastructure.test.queue

import com.jiangli.datastructure.test.BaseTest
import org.junit.Test
import java.util.*

open class ArrayDequeue : BaseTest() {

//    @RepeatFixedDuration
    @Test
    fun test_add() {
        val deque = ArrayDeque<Int>(2)
        deque.add(10)
        deque.add(11)
        deque.add(12)
        deque.add(13)
        println(deque)
    }

    @Test
    fun test_offer() {
        val deque = ArrayDeque<Int>(2)
        deque.offer(10)
        deque.offer(11)
        deque.offer(12)
        deque.offer(13)
        println(deque)
    }

    @Test
    fun test_peek() {
        val deque = ArrayDeque<Int>(2)
        deque.offer(10)
        deque.offer(11)
        deque.offer(12)
        deque.offer(13)
        println(deque.peek())
        println(deque.element())
    }


    @Test
    fun test_poll() {
        val deque = ArrayDeque<Int>(2)
        deque.offer(10)
        deque.offer(11)
        deque.offer(12)
        deque.offer(13)
        println(deque.poll())
        println(deque.remove())
        println(deque)
    }

    @Test
    fun test_peek2() {
        println(ArrayDeque<Int>(2).peek())
    }

    @Test
    fun test_element() {
        println(ArrayDeque<Int>(2).element())
    }
}