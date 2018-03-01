package com.jiangli.doc.mybatis

import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.sql.DriverManager

/**
 *
 *
 * out/dao/mapper,xml,model,test
 * out/service/service,dto,test
 * out/openapi/openservice,opendto,test
 *
 *
 * @author Jiangli
 * @date 2018/3/1 9:32
 */
data class JavaField(val columnName: String,val columType: String) {
     var initVal: Any?=null
    var isPk: Boolean=false
     lateinit var remark: String
     lateinit var fieldName: String
     lateinit var fieldCls: String
     var fieldClsImport: String?=null //import

    override fun toString(): String {
        return "JavaField(columnName='$columnName', columType='$columType', initVal=$initVal, isPk=$isPk, remark='$remark', fieldName='$fieldName', fieldCls='$fieldCls', fieldClsImport=$fieldClsImport)"
    }


}

fun calcField( f:JavaField) {
    val columType = f.columType

    f.fieldCls = when (columType.trim()) {
        "INT" -> "Long"
        "TINYINT" -> "Integer"
        "VARCHAR" -> "String"
        "TIMESTAMP" -> "Date"
        else -> throw Exception("未识别的type $columType")
    }
    f.fieldClsImport = when (columType) {
        "TIMESTAMP" -> "java.util.Date"
        else -> null
    }

    f.fieldName = colNameToCamel(f.columnName)

}

fun colNameToCamel(f:String):String {
    var lowerCase = f.toLowerCase()

    while (lowerCase.contains("_")) {
        val first = lowerCase.indexOfFirst { it == '_' }
        lowerCase=lowerCase.substring(0,first) + lowerCase.get(first+1).toUpperCase() + lowerCase.substring(first+2)
    }
    return lowerCase
}
fun nameToCamel(f:String):String {
    return f[0].toLowerCase()+ f.substring(1)
}


fun generateFile(body:String, vararg path:String) {
    val sb = StringBuilder()
    path.forEach {
        sb.append(it)
        sb.append(System.getProperty("file.separator"))
    }
    sb.deleteCharAt(sb.lastIndex)

    PathUtil.ensureFilePath(sb.toString())

    IOUtils.write(body,FileOutputStream(sb.toString()))
}

fun main(args: Array<String>) {
//    val TBL_NAME = "TBL_OPEN_ADVERTISEMENT"
    val TBL_NAME = "TBL_DAILY_PUSH"
    val JAVA_NAME = "DailyPush"
    val DESC = "每日推送"
    val DATABASE = "db_aries_operation"
    val OUTPUTPATH = "C:\\Users\\DELL-13\\Desktop\\outpath"
    val pkg="com.zhihuishu.aries.operation"

    //fixed
    Class.forName("com.mysql.jdbc.Driver")
    val connection = DriverManager.getConnection("jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava")

    val metaData = connection.metaData

    //列信息
    val list = mutableListOf<JavaField>()
    val colRs = metaData.getColumns(DATABASE, "%", TBL_NAME, "%")
    while (colRs.next()) {
        val columnName = colRs.getString("COLUMN_NAME")
        val columnType = colRs.getString("TYPE_NAME")
        val datasize = colRs.getInt("COLUMN_SIZE")
        val digits = colRs.getInt("DECIMAL_DIGITS")
        val nullable = colRs.getInt("NULLABLE")
        val message = colRs.getString("REMARKS")
        println("$columnName $columnType $datasize $digits $nullable")
//        println(message)
//        println(colRs.getString("IS_AUTOINCREMENT"))

        val javaField = JavaField(columnName, columnType)
        javaField.remark = message ?: ""
        calcField(javaField)
        list.add(javaField)
    }

    //表主键
    val pkRs = metaData.getPrimaryKeys(DATABASE, null, TBL_NAME)
    pkRs.next()
    val pkColName = pkRs.getString("COLUMN_NAME")
    list.filter { it.columnName==pkColName }.forEach { it.isPk=true }
//    println(pkColName)

//    println(list)

    //pojo
    val modelName = JAVA_NAME
    generateFile(generateCls("$DESC model", modelName,list),OUTPUTPATH,"dao","$modelName.java")
    val dtoName = "${JAVA_NAME}Dto"
    generateFile(generateCls("$DESC dto", dtoName,list),OUTPUTPATH,"service","$dtoName.java")
    val openDtoName = "${JAVA_NAME}OpenDto"
    generateFile(generateCls("$DESC open dto", openDtoName,list, FIELD_SERIAL, IMPL_SERIAL),OUTPUTPATH,"openapi","$openDtoName.java")

    //interface
    //mapper
    generateFile(generateInterface(DESC,"${JAVA_NAME}Mapper",false,modelName,IMPORT_MAPPER, ANNO_MAPPER),OUTPUTPATH,"dao","${JAVA_NAME}Mapper.java")
    //service
    generateFile(generateInterface(DESC,"${JAVA_NAME}Service",true,dtoName,IMPORT_SERVICE),OUTPUTPATH,"service","${JAVA_NAME}Service.java")
    //openapi
    generateFile(generateInterface(DESC,"${JAVA_NAME}OpenService",true,openDtoName,IMPORT_SERVICE),OUTPUTPATH,"openapi","${JAVA_NAME}OpenService.java")

    //impl
    //dao
    println(generateMapperXml(TBL_NAME,pkg,JAVA_NAME,list))
    //service
    generateFile(generateCls("$DESC Service实现", "${JAVA_NAME}ServiceImpl",null,null, arrayListOf("${JAVA_NAME}Service")),OUTPUTPATH,"service","${JAVA_NAME}ServiceImpl.java")
    //openapi
    generateFile(generateCls("$DESC OpenService实现", "${JAVA_NAME}OpenServiceImpl",null,null, arrayListOf("${JAVA_NAME}OpenService")),OUTPUTPATH,"openapi","${JAVA_NAME}OpenServiceImpl.java")
}