package com.jiangli.doc.mybatis

import java.lang.StringBuilder

val FIELD_SERIAL = arrayListOf("private static final long serialVersionUID = 1L", "")
val IMPL_SERIAL = arrayListOf("java.io.Serializable")

val ANNO_MAPPER = arrayListOf("@Repository")

val IMPORT_MAPPER = arrayListOf("java.util.List","org.apache.ibatis.annotations.Param","org.springframework.stereotype.Repository")
val IMPORT_SERVICE = arrayListOf("java.util.List")
val IMPORT_OPENAPI = arrayListOf("java.util.List")

val IMPORT_SERVICE_IMPL = arrayListOf("java.util.List","org.springframework.beans.factory.annotation.Autowired")
val IMPORT_OPENAPI_IMPL = arrayListOf("java.util.List","org.springframework.beans.factory.annotation.Autowired")


val SPACE = "    "

fun pend(list: MutableList<String>, vararg str: String):List<String> {
    str.forEach {
        list.add(it)
    }
    return list
}
fun autowiredField(clsName: String):String {
    val varName = nameToCamel(clsName)

    return "@Autowired\r\n${SPACE}private $clsName $varName"
}

fun generateCls(pkg:String,desc:String,clsName:String,fields:List<JavaField>?,extraImports:List<String>?= arrayListOf(),extraField:List<String>?= arrayListOf(),implClses:List<String>?= arrayListOf()):String {
    val fieldList =  StringBuilder()
    val importList = StringBuilder()
    val implClsList = StringBuilder()
    val methodsList = StringBuilder()
    val totalImport = mutableSetOf<String>()

    extraField?.forEach {
        fieldList.append("${SPACE}${it}${if (it.isNotEmpty()) ";" else ""}\r\n")
    }

    fields?.forEach {
        fieldList.append("${SPACE}private ${it.fieldCls} ${it.fieldName};//${it.remark}\r\n")

        it.fieldClsImport?.let {
            totalImport.add(it)
        }
    }

    extraImports?.forEach { totalImport.add(it) }
    totalImport.forEach {
        importList.append("import $it;\r\n")
    }

    implClses?.let {
        if (implClses.isNotEmpty()) {
            implClsList.append("implements ")

            it.forEachIndexed { index, s ->
                implClsList.append(s)
                if (index != it.lastIndex) {
                    implClsList.append(",")
                }
            }
        }
    }


    //getter & setter
    fields?.let {

    }

    //toString
    fields?.let {

    }

    return """
package $pkg;
$importList

/**
 * $desc
 */
public class $clsName $implClsList{
$fieldList
}
"""
}

fun appendComment(sb:StringBuilder,txt:String) {
    sb.append("${SPACE}/**\r\n")
    sb.append("${SPACE} * $txt\r\n")
    sb.append("${SPACE} */\r\n")
}
fun appendEnter(sb:StringBuilder) {
    sb.append("${SPACE}\r\n")
}

fun generateInterface(pkg:String,desc:String,clsName:String,useWrap:Boolean?=false,objName:String,extraImports:List<String>?= arrayListOf(),extraAnnos:List<String>?= arrayListOf(),implClses:List<String>?= arrayListOf()):String {
    val annoList =  StringBuilder()
    val importList = StringBuilder()
    val methodsList =  StringBuilder()
    val implClsList = StringBuilder()
    val imported = hashSetOf<String>()

    extraAnnos?.forEach {
        annoList.append("\r\n${it}")
    }

    extraImports?.forEach {
        if (!imported.contains(it)) {
            importList.append("import $it;\r\n")
            imported.add(it)
        }
    }

//    extraMethods?.forEach {
        val variableName = nameToCamel(objName)
        appendEnter(methodsList)
        appendComment(methodsList,"$desc 新增")
        methodsList.append("${SPACE}void save($objName $variableName);\r\n")

        appendEnter(methodsList)
        appendComment(methodsList,"$desc 删除")
        methodsList.append("${SPACE}void delete($objName $variableName);\r\n")

        appendEnter(methodsList)
        appendComment(methodsList,"$desc 更新")
        methodsList.append("${SPACE}void update($objName $variableName);\r\n")

        appendEnter(methodsList)
        appendComment(methodsList,"$desc 查询单个")
        methodsList.append("${SPACE}$objName get(${if(useWrap!!) "Long" else "long"} ${variableName}Id);\r\n")

        appendEnter(methodsList)
        appendComment(methodsList,"$desc 查询列表")
        methodsList.append("${SPACE}List<$objName> list(${if(useWrap!!) "Long" else "long"} courseId);\r\n")

        appendEnter(methodsList)
//    }

    implClses?.let {
        if (implClsList.isNotEmpty()) {
            implClsList.append("extends ")

            it.forEachIndexed { index, s ->
                implClsList.append(s)
                if (index != it.lastIndex) {
                    implClsList.append(",")
                }
            }
        }
    }

    return """
package $pkg;
$importList

/**
 * $desc
 */$annoList
public interface $clsName $implClsList{
$methodsList
}
"""
}

fun generateMapperXml(tableName:String,pkg:String,javaName:String,fields:List<JavaField>):String{
    val includes = fields.joinToString(",\r\n") { javaField -> SPACE +SPACE + javaField.columnName }
    val variableName = nameToCamel(javaName)

    val idField = fields.first { it.isPk }.fieldName
    val idColumn = fields.first { it.isPk }.columnName

    val updateList = StringBuilder()
    fields.filter { !it.isPk } .forEach {
        updateList.append("\r\n$SPACE$SPACE$SPACE<if test=\"${it.fieldName} != null\">${it.columnName}= #{${it.fieldName}}, </if>")
    }

    val saveColList = StringBuilder()
    val saveValList = StringBuilder()
    fields.filter { !it.isPk } .forEach {
        saveColList.append("\r\n$SPACE$SPACE$SPACE<if test=\"${it.fieldName} != null\">${it.columnName}, </if>")
        saveValList.append("\r\n$SPACE$SPACE$SPACE<if test=\"${it.fieldName} != null\">#{${it.fieldName}}, </if>")
    }


    return """<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${pkg}.mapper.${javaName}Mapper">

    <!-- 表字段 -->
    <sql id="fields">
$includes
    </sql>

    <!-- 查列表 -->
    <select id="list" resultType="${pkg}.model.${javaName}">
        SELECT <include refid="fields"/>  FROM $tableName WHERE IS_DELETED=0  AND COURSE_ID = #{courseId}
    </select>

    <!-- 查单个 -->
    <select id="get"  resultType="${pkg}.model.${javaName}">
        SELECT <include refid="fields"/>  FROM $tableName WHERE IS_DELETED=0  AND ID = #{${variableName}Id}
    </select>

    <!-- 删除 -->
    <update id="delete" parameterType="${pkg}.model.${javaName}">
        UPDATE $tableName
        <set>
            <if test="deletePerson != null">DELETE_PERSON = #{deletePerson}, </if>
            IS_DELETED = 1
        </set>
        WHERE $idColumn=#{$idField} AND IS_DELETED=0
    </update>

    <!-- 修改 -->
    <update id="update" parameterType="${pkg}.model.${javaName}">
        UPDATE $tableName
        <set>$updateList
            UPDATE_TIME = CURRENT_TIMESTAMP
        </set>
        WHERE $idColumn=#{$idField} AND IS_DELETED=0
    </update>

    <!-- 保存 -->
    <insert id="save" parameterType="${pkg}.model.${javaName}" keyProperty="$idField" useGeneratedKeys="true">
        INSERT INTO $tableName($saveColList
        ) values ($saveValList
        )
    </insert>
</mapper>
"""

}
