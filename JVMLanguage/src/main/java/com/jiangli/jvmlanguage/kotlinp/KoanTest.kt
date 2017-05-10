package com.jiangli.jvmlanguage.kotlinp

import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2017/5/10 13:31
 */
fun containsEven(collection: Collection<Int>): Boolean = collection.any { a->a%2==0}

val month = "(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)"

//Using month variable rewrite this pattern in such a way that it
// matches the date in format
// 13 JUN 1992
// (two digits, a whitespace, a month abbreviation, a whitespace, four digits).

// """\d{2}\.\d{2}\.\d{4}"""
//13.06.1992
//fun getPattern(): String = """\d{2} ${month} \d{4}"""
fun getPattern(): String = """\d{2}\s{1}${month} \d{4}"""


data class MyDate(var year: Int, var month: Int, var dayOfMonth: Int) : Comparable<MyDate> {


    override fun compareTo(other: MyDate): Int
//    {
//        var compareTo = year.compareTo(other.year)
//        if(compareTo !=0 )
//            return compareTo
//        compareTo = month.compareTo(other.month)
//        if(compareTo !=0 )
//            return compareTo
//        compareTo = dayOfMonth.compareTo(other.dayOfMonth)
//        return compareTo
//    }
        //opt1
//    {
//        when {
//            year != other.year -> return year.compareTo(other.year)
//            else -> when{
//                month != other.month -> return month.compareTo(other.month)
//                else ->return dayOfMonth.compareTo(other.dayOfMonth)
//                }
//            }

//    }
    //opt2
//    =when {
//        year != other.year -> year - other.year
//        month != other.month -> month - other.month
//        else -> dayOfMonth - other.dayOfMonth
//    }
    //opt3
    =when {
        year != other.year -> year.compareTo(other.year)
        month != other.month -> month.compareTo(other.month)
        else -> dayOfMonth.compareTo(other.dayOfMonth)
    }

    /** Unary operations start **/
    //+a	a.unaryPlus()
//    -a	a.unaryMinus()
//    !a	a.not()
    operator fun unaryPlus() {
        dayOfMonth++
        refactor()
    }

//
    //Expression	Translated to
//    a++	a.inc() + see below
//    a--	a.dec() + see below
    operator fun inc():MyDate {
        dayOfMonth++
//       if(dayOfMonth > 31) {dayOfMonth=1;month++}
//       if(month > 12) {month=1;year++}
       refactor()
        return this
    }

    fun refactor(){
        val c = Calendar.getInstance()
        c.set(year, month-1, dayOfMonth)
        year=c.get(Calendar.YEAR)
        month=c.get(Calendar.MONTH)+1
        dayOfMonth=c.get(Calendar.DAY_OF_MONTH)
    }
    /** Unary operations end **/

    /** Binary operations start **/
//    a + b	a.plus(b)
//    a - b	a.minus(b)
//    a * b	a.times(b)
//    a / b	a.div(b)
//    a % b	a.rem(b), a.mod(b) (deprecated)
//    a..b	a.rangeTo(b)
//    a in b	b.contains(a)
//    a !in b	!b.contains(a)
//    a[i]	a.get(i)
//    a[i, j]	a.get(i, j)
//    a[i_1, ..., i_n]	a.get(i_1, ..., i_n)
//    a[i] = b	a.set(i, b)
//    a[i, j] = b	a.set(i, j, b)
//    a[i_1, ..., i_n] = b	a.set(i_1, ..., i_n, b)
//    a()	a.invoke()
//    a(i)	a.invoke(i)
//    a(i, j)	a.invoke(i, j)
//    a(i_1, ..., i_n)	a.invoke(i_1, ..., i_n)
//    a += b	a.plusAssign(b)
//    a -= b	a.minusAssign(b)
//    a *= b	a.timesAssign(b)
//    a /= b	a.divAssign(b)
//    a %= b	a.modAssign(b)
//    a == b	a?.equals(b) ?: (b === null)
//    a != b	!(a?.equals(b) ?: (b === null))
//    Note: === and !== (identity checks) are not overloadable, so no conventions exist for them
//    a > b	a.compareTo(b) > 0
//    a < b	a.compareTo(b) < 0
//    a >= b	a.compareTo(b) >= 0
//    a <= b	a.compareTo(b) <= 0


    operator fun plus(b:Any):MyDate {
        if (b is Number) {
           dayOfMonth+=b.toInt()
        }

        var ts:TimeIntervalTimes?=null
        if (b is TimeInterval) {
            ts=TimeIntervalTimes(b,1)
        }
        if (b is TimeIntervalTimes) {
            ts=b
        }
        when(ts?.unit){
            TimeInterval.DAY ->  dayOfMonth+=ts.times
            TimeInterval.WEEK -> dayOfMonth+=7*ts.times
            TimeInterval.YEAR -> year+=ts.times
        }
        refactor()
        return this
    }

    /** Binary operations end **/

}

data class DateRange(val start: MyDate, val endInclusive: MyDate) {
    /** Binary operations start **/
    operator fun contains(date: MyDate): Boolean {
        return start<date && date<=endInclusive
    }
}
operator fun MyDate.rangeTo(other: MyDate) = DateRangeClosed(this,other)  //can also be DateRange

fun MyDate.addTimeIntervals(timeInterval: TimeInterval, number: Int): MyDate {
    val c = Calendar.getInstance()
    c.set(year + if (timeInterval == TimeInterval.YEAR) number else 0, month, dayOfMonth)
    var timeInMillis = c.getTimeInMillis()
    val millisecondsInADay = 24 * 60 * 60 * 1000L
    timeInMillis += number * when (timeInterval) {
        TimeInterval.DAY -> millisecondsInADay
        TimeInterval.WEEK -> 7 * millisecondsInADay
        TimeInterval.YEAR -> 0L
    }
    val result = Calendar.getInstance()
    result.setTimeInMillis(timeInMillis)
    return MyDate(result.get(Calendar.YEAR), result.get(Calendar.MONTH), result.get(Calendar.DATE))
}
fun MyDate.nextDay() = addTimeIntervals(TimeInterval.DAY, 1)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

data class TimeIntervalTimes(var  unit:TimeInterval,var times:Int) {
}

operator fun TimeInterval.times(number: Int):TimeIntervalTimes{
    return TimeIntervalTimes(this,number)
}

data class DateRangeClosed(override val start: MyDate, override val endInclusive: MyDate): ClosedRange<MyDate>, Iterable<MyDate>{
    override fun iterator(): Iterator<MyDate> {
        return DateRangeIterator()
    }

    inner class DateRangeIterator:Iterator<MyDate>{
        var cur:MyDate=start

        override fun hasNext(): Boolean {
            return cur<=endInclusive
        }

        override fun next(): MyDate {
            val ret = cur.copy()
            cur++
            return ret
        }
    }
}

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}

fun compare(date1: MyDate, date2: MyDate) = date1 < date2

fun main(args: Array<String>) {
    println(containsEven(arrayListOf(1,5,23)))
    println(containsEven(arrayListOf(2,4,6)))
    println(containsEven(listOf(2,4,6)))

    println("-------------------------")

    println("13.06.1992".matches("""\d{2}\.\d{2}\.\d{4}""".toRegex()))
    println("99.99.1992".matches("""\d{2}\.\d{2}\.\d{4}""".toRegex()))
    println("999.999.1992".matches("""\d{2}\.\d{2}\.\d{4}""".toRegex()))

    println("13 JUN 1992".matches(getPattern().toRegex()))
    println("13JUN1992".matches(getPattern().toRegex()))

    val listWithNulls: List<String?> = listOf("A", null)
    for (item in listWithNulls) {
        val g = item;
        item?.let { println(it) } // prints A and ignores null
    }

    println(compare(MyDate(1990,5,23),MyDate(1990,7,15)))
    println(compare(MyDate(1990,7,15),MyDate(1990,5,23)))
    var dt = MyDate(1990,7,15)
    +dt
    println(dt.dayOfMonth)
    dt++
    println(dt.dayOfMonth)
    dt=dt+5
    println(dt.dayOfMonth)

    println(checkInRange(MyDate(1990,6,16),MyDate(1990,5,23),MyDate(1990,7,15)))
    println(checkInRange(MyDate(1990,5,23),MyDate(1990,5,23),MyDate(1990,7,15)))
    println(checkInRange(MyDate(1990,5,24),MyDate(1990,5,23),MyDate(1990,7,15)))
    println(checkInRange(MyDate(1990,7,15),MyDate(1990,5,23),MyDate(1990,7,15)))
    println(checkInRange(MyDate(1990,7,14),MyDate(1990,5,23),MyDate(1990,7,15)))

    val dtRange = MyDate(1990,5,23)..MyDate(1990,7,15)
    println(dtRange)
    println(dtRange.javaClass)

    for (one in dtRange) {
        println(one)
    }


    val c = Calendar.getInstance()
    println(c.get(Calendar.YEAR))
    println(c.get(Calendar.MONTH))
    println(c.get(Calendar.DAY_OF_MONTH))


    println(MyDate(1990,5,23) + 5)
    println(MyDate(1990,5,23) + TimeInterval.DAY*5)
    println(MyDate(1990,5,23) + TimeInterval.WEEK)
    println(MyDate(1990,5,23) + TimeInterval.YEAR*5)
    println(MyDate(1990,5,23) + TimeInterval.YEAR)
    println(MyDate(2014, 5, 1) + TimeInterval.YEAR+TimeInterval.WEEK)
    println(MyDate(2014, 0, 1) + TimeInterval.DAY*5+TimeInterval.WEEK*3+TimeInterval.YEAR*2)
    println(MyDate(2014, 0, 25) + TimeInterval.DAY*5+TimeInterval.WEEK*3+TimeInterval.YEAR*2)

}