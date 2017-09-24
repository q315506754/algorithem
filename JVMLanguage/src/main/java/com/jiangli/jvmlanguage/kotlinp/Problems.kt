package com.jiangli.jvmlanguage.kotlinp

import com.jiangli.common.utils.MD5
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/9 14:46
 */
fun indexOfMax(a: IntArray): Int? {
    println(a.lastIndex)
    if(a.lastIndex<0)
        return null

    var maxIdx:Int = 0
    var max:Int = a[maxIdx]

    for(idx in a.indices){
       if(a[idx] >= max){
           max=a[idx]
           maxIdx = idx
       }
    }
    return  maxIdx
}

/*
 * Any array may be viewed as a number of "runs" of equal numbers.
 * For example, the following array has two runs:
 *   1, 1, 1, 2, 2
 * Three 1's in a row form the first run, and two 2's form the second.
 * This array has two runs of length one:
 *   3, 4
 * And this one has five runs:
 *   1, 0, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0
 * Your task is to implement the runs() function so that it returns the number
 * of runs in the given array.
 */
fun runs(a: IntArray): Int {
    // Write your solution here
    var ret=0

    if(a.isNotEmpty()){
        ret++
        var prev=a[0]

        if (a.size>1) {
            var curIdx=1

            while (curIdx<a.size){
                var cur=a[curIdx]

                if(cur != prev) {
                    ret++
                    prev=cur
                }

                curIdx++
            }
        }
    }
    return ret
}

/*
 * Your task is to implement a palindrome test.
 *
 * A string is called a palindrome when it reads the same way left-to-right
 * and right-to-left.
 *
 * See http://en.wikipedia.org/wiki/Palindrome
 */
fun isPalindrome(s: String): Boolean {
    if (s.isNotEmpty()) {
        var idx=0
        var last=s.lastIndex-idx
        while(idx<last){
            if(s[idx]!=s[last]) return false
            idx++
            last=s.lastIndex-idx
        }
        return true
    }
    return true
}

/*
 * Think of a perfect world where everybody has a soulmate.
 * Now, the real world is imperfect: there is exactly one number in the array
 * that does not have a pair. A pair is an element with the same value.
 * For example in this array:
 *   1, 2, 1, 2
 * every number has a pair, but in this one:
 *   1, 1, 1
 * one of the ones is lonely.
 *
 * Your task is to implement the findPairless() function so that it finds the
 * lonely number and returns it.
 *
 * A hint: there's a solution that looks at each element only once and uses no
 * data structures like collections or trees.
 */
fun findPairless(a: IntArray): Int {
    // Write your solution here
//    val map = a.groupBy {}
    val map = a.groupBy { i->i}
    val filterValues = map.filterValues { ls -> ls.size % 2 != 0 }
    if(filterValues.size>0){
        return filterValues.values.iterator().next()[0]
    }
    return  0
}

fun joinToString(
        separator: String = ", ",
        prefix: String? = ".",
        postfix: String = "|"
        /* ... */
): String {
    return prefix+separator+postfix
}

fun joinOptions(options: Collection<String>) = options.joinToString(", ","[","]")

fun main(args: Array<String>) {
    fun p(a:Any?) = println(a)

    p(indexOfMax(intArrayOf(2,4,6,3,2)))
    p(indexOfMax(intArrayOf()))

    p(runs(intArrayOf(2,4,6,3,2)))
    p(runs(intArrayOf( 1, 1, 1, 2, 2)))
    p(runs(intArrayOf( 3,5)))
    p(runs(intArrayOf( 1, 0, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0)))

    p(isPalindrome("abcba"))
    p(isPalindrome("aa"))
    p(isPalindrome("abba"))
    p(isPalindrome("ac"))
    p(isPalindrome("你好好你"))
    p(isPalindrome("你好你"))
    p(isPalindrome("你好"))
    p(isPalindrome(""))
    p("你好好你"[0])
    p("你好好你"[1])
    p("你好好你"[2])
    p("你好好你"[3])

    p(findPairless(intArrayOf( 1, 0, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0)))
    p(findPairless(intArrayOf( 1,2,1,2)))
    p(findPairless(intArrayOf(  1, 1, 1)))
    p(findPairless(intArrayOf(  1, 1, 5)))

    p(joinToString("aaa","xxxx"))
    p(joinToString("aaa",null,""))

    p(joinOptions(listOf("aaa","bbb","ccc")))
    p(joinOptions(listOf("yes", "no", "may be")))

    println(SimpleDateFormat("yyyyMMddHH").format(Date()))
    println(getPwd())
}

private fun getPwd(): String {
    val dateStr = SimpleDateFormat("yyyyMMddHH").format(Date())
    return MD5.getMD5Str(dateStr + "zhihuishuable-elec1232323").toLowerCase()
}


