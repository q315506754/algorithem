package com.jiangli.doc.sql

import com.jiangli.common.utils.DateUtil
import com.jiangli.doc.sql.BaseConfig.printSql
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/26 13:40
 */
object BaseConfig {
    var printSql = true

    fun injectTest(jdbc: JdbcTemplate, id: Any?, inf: NamedSimpleCachedTableQueryer): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        val oldField = inf.fields
        inf.fields = "*"
        val idName = inf.props[0]
        map.put("$idName", id)
        injectData(jdbc, map, inf)
        inf.fields = oldField

        val first = map.keys.filter { d -> d != idName }.first()

//        println(map[first])
        return map[first] as Map<String, Any?>
    }

    fun interceptValue(rsFinal: Any?) {
        rsFinal?.let {
            if (rsFinal is MutableMap<*, *>) {
                rsFinal.entries.forEach {
                    t: Map.Entry<Any?, Any?>? ->
                    val key = t!!.key
                    val valx = t?.value

//                    日期美化
                    if (valx is java.sql.Timestamp) {
                        (rsFinal as MutableMap<Any?, Any?>).put(key, DateUtil.getDate_yyyyMMddHHmmssEx(valx.time))
                    }

                    interceptValue(t?.value)
                }
            }else if (rsFinal is Iterable<*>) {
                rsFinal.forEach {
                    interceptValue(it)
                }
            } else {

            }
        }
    }

    fun injectData(jdbc: JdbcTemplate, map: MutableMap<String, Any?>, vararg infs: DataQueryer) {
        infs.forEach {
            val inf = it

            val nMap = mutableMapOf<String, Any?>()
            nMap.putAll(map)

            map.forEach { entry ->
                val key = entry.key
                if (inf.intercept(key)) {
                    val rs = inf.query(jdbc, entry.value)
//                    val keyProp = inf.javaClass.simpleName
//                    val keyProp = key+"_Info_"+inf.javaClass.simpleName
                    val keyProp = inf.name(key).replace("${'$'}{params}", entry.value?.toString() ?: "null")

                    var rsFinal: Any? = null

                    if (rs.size == 0) {
                        rsFinal = mutableMapOf<String, Any?>()
                    } else if (rs.size == 1) {
                        rsFinal = rs[0]
                    } else {
                        rsFinal = rs
                    }

                    //intercept map values
                    interceptValue(rsFinal)


                    nMap.put(keyProp, rsFinal)

                    rsFinal?.let {
                        if (rsFinal is Map<*, *>) {
                            injectData(jdbc, rsFinal as MutableMap<String, Any?>, *infs)
                        } else if (rsFinal is List<*>) {
                            (rsFinal as List<*>).forEach {
                                injectData(jdbc, it as MutableMap<String, Any?>, *infs)
                            }
                        }
                    }

                }
            }

            map.putAll(nMap)
        }
    }

}

interface DataQueryer {
    fun name(key: String): String

    fun intercept(prop: String): Boolean

    fun query(jdbc: JdbcTemplate, params: Any?): List<MutableMap<String, Any?>>
}

abstract class BaseDataQueryer : DataQueryer {
    override fun name(key: String): String {
        return key + "_" + this.javaClass.simpleName + "_info"
    }
}

abstract class IdQueryer(val prop: String) : BaseDataQueryer() {
    override fun intercept(prop: String): Boolean {
        return this.prop.equals(prop, true)
    }
}

abstract class MultiIdQueryer(val props: Array<String>) : BaseDataQueryer() {
    override fun intercept(prop: String): Boolean {
        return this.props.contains(prop)
    }
}

abstract class SimpleCachedTableQueryer(props: Array<String>, val db: String, var fields: String?, val criteria: String?) : MultiIdQueryer(props) {
    val cache = mutableMapOf<String, Any?>()

    override fun query(jdbc: JdbcTemplate, params: Any?): List<MutableMap<String, Any?>> {
        val queryExistsId = """SELECT ${fields ?: "*"} FROM $db WHERE  ${criteria ?: "1=1"} ;""".replace("${'$'}{params}", params.toString())

//        println("generate sql: "+queryExistsId)
        var qIdList: List<MutableMap<String, Any?>>? = null

        val pl:(Any?)->Unit = if(printSql) ::println else {any->}

        if (cache.containsKey(queryExistsId)) {
            pl("hit cache: " + queryExistsId)
            qIdList = cache[queryExistsId] as List<MutableMap<String, Any?>>?
        } else {
            pl("execute sql: " + queryExistsId)
            qIdList = jdbc.query(queryExistsId, ColumnMapRowMapper())
            cache[queryExistsId] = qIdList
        }
        return qIdList!!
    }
}

abstract class NamedSimpleCachedTableQueryer(val name: String, props: Array<String>, db: String, fields: String?, criteria: String?)
    : SimpleCachedTableQueryer(props, db, fields, criteria) {
    override fun name(key: String): String {
        return name
    }
}