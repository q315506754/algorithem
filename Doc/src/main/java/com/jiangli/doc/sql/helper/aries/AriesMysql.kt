package com.jiangli.doc.sql.helper.aries

import com.jiangli.doc.sql.helper.aries.ArisDBQueryer.arisDB


open  class Repo(val name:String)

open class Tbl(val repo:Repo,val tblName:String, val key:String, val logic_delete_key:String) {
      val refs= mutableListOf<Ref>()

    init {
        initFunc()
    }

     open fun  initFunc() {
     }
}

open class Ref(open val higherLayer:Tbl,open val lowerKey:String,val higherKey:String)
open class MainKeyRef(override val higherLayer:Tbl,override val lowerKey:String) : Ref(higherLayer,lowerKey,"")


class ArisDB{

     val db_aries_user = object :Repo("db_aries_user"){}
     val db_aries_user_TBL_USER = object : Tbl(db_aries_user,"TBL_USER","ID","IS_DELETED") {
    }
    val db_aries_user_TBL_USER_ROLE = object :Tbl(db_aries_user,"TBL_USER_ROLE","ID","IS_DELETED"){
        override fun initFunc() {
            refs.add(MainKeyRef(db_aries_user_TBL_USER,"USER_ID"))
        }
    }



    open val db_aries_course = object :Repo("db_aries_course"){}
    open val db_aries_course_TBL_COURSE = object : Tbl(db_aries_course,"TBL_COURSE","COURSE_ID","IS_DELETED") {
        override fun initFunc() {
            refs.add(MainKeyRef(db_aries_user_TBL_USER,"CREATE_PERSON"))
        }
    }
    open val db_aries_course_TBL_CHAPTER = object : Tbl(db_aries_course,"TBL_CHAPTER","ID","IS_DELETED") {
        override fun initFunc() {
            refs.add(MainKeyRef(db_aries_user_TBL_USER,"CREATE_PERSON"))
            refs.add(MainKeyRef(db_aries_course_TBL_COURSE,"COURSE_ID"))
        }
    }

}

object ArisDBQueryer{
    val java = ArisDB::class.java
    val arisDB = ArisDB()

    fun join(left:Tbl,ref:Ref){

    }

    fun parseTblName(tbl:Tbl):String{
        return "${tbl.repo.name}.${tbl.tblName.toUpperCase()}"
    }

    fun indent(indent:String,layer:Int = 0):String{
        val s = StringBuilder()

        var r = layer
        while (r-- > 0) {
            s.append(indent)
        }
        return s.toString()
    }

    fun printTree(queryTbl:Tbl,indent:String,layer:Int = 0,str:String = ""){
        val tblName = parseTblName(queryTbl)
        println("${indent(indent,layer)}${tblName} $str")

        findRefs(queryTbl){
            tbl, ref ->
//            println(tbl)
//            println(ref)

            var appendStr = " on ${queryTbl.tblName}.${queryTbl.key}=${tbl.tblName}.${ref.lowerKey}"
            printTree(tbl,indent,layer+1,appendStr)
        }
    }

    fun findRefs(queryTbl:Tbl,consumer:(Tbl,Ref)->Unit):Int{
        var count = 0

        java.declaredFields.forEach {
            it.isAccessible = true

            val one = it.get(arisDB)

            if (one is Tbl) {
                val repo = one.repo
                val refs = one.refs

                val ref = refs.find { ref -> ref.higherLayer == queryTbl }
                if (ref!=null) {
                    consumer(one,ref)
                    count++
//                    println(one)
//                    println(ref)
                }
            }

        }

        return count
    }

}

//val
fun main(args: Array<String>) {
//    println(db_aries_user_TBL_USER_ROLE.refs.size)

//    println(Tbl::javaClass)
//    println(ArisDB::class)
//    println(ArisDB::class.java)
//    println(ArisDB::javaClass)
//    println(ArisDB::javaClass.javaClass)

//    val resource = Tbl::javaClass.javaClass.classLoader.getResource("")
//    println(resource)

    val queryTbl = arisDB.db_aries_user_TBL_USER

    ArisDBQueryer.findRefs(queryTbl){
        tbl, ref ->
        println(tbl)
        println(ref)
    }

    ArisDBQueryer.printTree(queryTbl,"  ")
}