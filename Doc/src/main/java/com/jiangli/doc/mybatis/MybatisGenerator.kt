package com.jiangli.doc.mybatis

import com.jiangli.common.utils.FileUtil
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
        "SMALLINT" -> "Integer"
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
//    val PKG="com.zhihuishu.aries.operation"
//    val DATABASE = "db_aries_operation"
//    val TBL_NAME = "TBL_DAILY_PUSH"
//    val JAVA_NAME = "DailyPush"
//    val DESC = "每日推送"

    val PKG="com.zhihuishu.aries.course"
    val DATABASE = "db_aries_course"
    val TBL_NAME = "TBL_RELEVANCE_COURSE"
    val JAVA_NAME = "CourseRelevance"
    val DESC = "课程权限"

    val OUTPUTPATH = "C:\\Users\\DELL-13\\Desktop\\outpath"
    val DB_URL = "jdbc:mysql://192.168.222.8:3306?user=root&password=ablejava"

    //fixed
    Class.forName("com.mysql.jdbc.Driver")
    val connection = DriverManager.getConnection(DB_URL)

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

    //remove
    FileUtil.deleteUnderDir(OUTPUTPATH)

    //pojo
    val modelName = JAVA_NAME
    val modelPkg = "${PKG}.model"
    val modelCls = "$modelPkg.$modelName"
    generateFile(generateCls(modelPkg,"$DESC model", modelName,list),OUTPUTPATH,"model","$modelName.java")
    val dtoName = "${JAVA_NAME}Dto"
    val dtoPkg = "${PKG}.dto"
    val dtoCls = "$dtoPkg.$dtoName"
    generateFile(generateCls(dtoPkg,"$DESC dto", dtoName,list),OUTPUTPATH,"dto","$dtoName.java")
    val openDtoName = "${JAVA_NAME}OpenDto"
    val openDtoPkg = "${PKG}.openapi.dto"
    val openDtoCls = "$openDtoPkg.$openDtoName"
    generateFile(generateCls(openDtoPkg,"$DESC open dto", openDtoName,list, null,FIELD_SERIAL, IMPL_SERIAL),OUTPUTPATH,"openapi","dto","$openDtoName.java")

    //interface
    //mapper
    val mapperClsName = "${JAVA_NAME}Mapper"
    val mapperPkg = "${PKG}.mapper"
    val mapperCls = "$mapperPkg.$mapperClsName"
    generateFile(generateInterface(mapperPkg,DESC, mapperClsName,false,modelName,pend(IMPORT_MAPPER,modelCls), ANNO_MAPPER),OUTPUTPATH,"mapper","$mapperClsName.java")
    //service
    val serviceClsName = "${JAVA_NAME}Service"
    val servicePkg = "${PKG}.service"
    val serviceCls = "$servicePkg.$serviceClsName"
    generateFile(generateInterface(servicePkg,DESC, serviceClsName,true,dtoName,pend(IMPORT_SERVICE,dtoCls)),OUTPUTPATH,"service","$serviceClsName.java")
    //openapi
    val openServiceName = "${JAVA_NAME}OpenService"
    val openapiPkg = "${PKG}.openapi"
    val openapiCls = "$openapiPkg.$openServiceName"
    generateFile(generateInterface(openapiPkg,DESC, openServiceName,true,openDtoName,pend(IMPORT_OPENAPI,openDtoCls)),OUTPUTPATH,"openapi","$openServiceName.java")

    //impl
    //dao
    generateFile(generateMapperXml(TBL_NAME,PKG,JAVA_NAME,list),OUTPUTPATH,"${JAVA_NAME.toLowerCase()}.xml")
    //service
    val serviceImplPkg = "${PKG}.service.impl"
    generateFile(generateCls(serviceImplPkg,"$DESC Service实现", "${serviceClsName}Impl",null,pend(IMPORT_SERVICE_IMPL,serviceCls,dtoCls,mapperCls), pend(mutableListOf(),autowiredField(mapperClsName)), arrayListOf(serviceClsName)),OUTPUTPATH,"service","impl","${serviceClsName}Impl.java")
    //openapi
    val openserviceImplPkg = "${PKG}.openapi.impl"
    generateFile(generateCls(openserviceImplPkg,"$DESC OpenService实现", "${openServiceName}Impl",null,pend(IMPORT_OPENAPI_IMPL,openapiCls,openDtoCls,serviceCls),pend(mutableListOf(),autowiredField(serviceClsName)), arrayListOf(openServiceName)),OUTPUTPATH,"openapi","impl","${openServiceName}Impl.java")
}