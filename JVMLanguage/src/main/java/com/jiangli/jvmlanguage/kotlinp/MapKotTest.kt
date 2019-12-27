package com.jiangli.jvmlanguage.kotlinp

import com.jiangli.common.model.Person
import com.jiangli.common.utils.ModelUtil

/**
 *
 *
 * @author Jiangli
 * @date 2019/6/18 10:47
 */

fun main(args: Array<String>) {

    val mp = mapOf(
            "a" to 100
            ,"b" to 200
            ,"c" to 200
    )

    var list = ModelUtil.generatePersons(10)

    println("list $list")
    println("reduce ${list.reduce { acc, person ->
        acc.age += person.age
        acc.name+=person.age
        acc
      }
     }")

//    方向不同
    list = ModelUtil.generatePersons(10)
    println("list $list")
    println("list reversed ${list.reversed()}") //不影响原数组
    println("list org $list")

//    --查找 single  find findLast [last]indexOf all ,any,count
    println("list binarySearch ${list.binarySearch{ person -> person.age.compareTo(5)}}") //4


//    --截取  remove[If,All] retain[All]
//  不影响原数组
    println("list dropWhile ${list.dropWhile{ person -> person.age > 5}}") //4
    println("list slice ${list.slice(2..4)}") //取 2,3,4 共3个
//    println("list $list")


    //    流处理
    //    forEach  filter->list distinct->list flatMap&map->list  reduce,fold->one   associate,[By]->map  groupBy->map partition->Pair<List,List>
    println("associate ${list.associate {person: Person? -> person?.name to person?.age } }")
    println("associateBy ${list.associateBy {person: Person? -> person?.name} }")
    println("associateBy ${list.associateBy(Person::getName) }")
    println("partition ${list.partition{it.age%2==0} }")
    println("groupBy ${list.groupBy{it.age} }")

    println("fold ${list.fold(0) { acc, person ->
        acc+1
    }
    }")

    println("reduce ${list.reduceRight { acc, person ->
        person.age += acc.age
        person.name+=acc.age
        person
      }
     }")



    //    合并
//    zip->list zip->Pair<T,T>
    println("zip ${ModelUtil.generatePersons(10).zip(ModelUtil.generatePersons(10))}")
    println("zip ${ModelUtil.generatePersons(10).zip(ModelUtil.generatePersons(10)){a, b ->
        val ret = Person()
        ret.name = a.name + b.name
        ret.age = a.age + b.age
        ret.state = a.state + b.state
        ret
    } }")

}