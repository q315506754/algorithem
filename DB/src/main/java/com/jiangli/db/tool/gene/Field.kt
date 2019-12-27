
import com.jiangli.common.lib.Rnd
import com.jiangli.common.utils.DateUtil
import java.sql.DriverManager

/**
 * out/dao/mapper,xml,model,test
 * out/service/service,dto,test
 * out/openapi/openservice,opendto,test
 *
 *
 * @author Jiangli
 * @date 2018/3/1 9:32
 */
fun shouldInputFieldValue(it: JavaField) = !it.nullable && it.defaultValue == null

fun List<JavaField>.getPkField() : JavaField{
   return  this.first { it.isPk==true}
}
fun List<JavaField>.getPkFieldColumn() : String {
   return  getPkField().columnName
}
fun List<JavaField>.getPkFieldName() : String {
   return  getPkField().fieldName
}

data class JavaField(val columnName: String,val columType: String) {
    var initVal: Any?=null
    var isPk: Boolean=false
    lateinit var remark: String
    lateinit var fieldName: String
    lateinit var fieldCls: String
    var nullable: Boolean=false
    var defaultValue: String?=null
    var fieldClsImport: String?=null //import

    override fun toString(): String {
        return "JavaField(columnName='$columnName', columType='$columType', initVal=$initVal, isPk=$isPk, remark='$remark', fieldName='$fieldName', fieldCls='$fieldCls', nullable=$nullable, defaultValue='$defaultValue', fieldClsImport=$fieldClsImport)"
    }

}

fun calcField( f:JavaField) {
    val columType = f.columType

    f.fieldCls = when (columType.trim()) {
        "INT" -> "Long"
        "INT UNSIGNED" -> "Long"
        "TINYINT" -> "Integer"
        "TINYINT UNSIGNED" -> "Integer"
        "BIGINT" -> "Long"
        "BIT" -> "Integer"
        "SMALLINT" -> "Integer"
        "VARCHAR" -> "String"
        "CHAR" -> "String"
        "TIMESTAMP" -> "Date"
        "DATETIME" -> "Date"
        "TEXT" -> "String"
        "DECIMAL" -> "Double"
        else -> throw Exception("未识别的type $columType")
    }
    f.fieldClsImport = when (columType) {
        "TIMESTAMP" -> "java.util.Date"
        "DATETIME" -> "java.util.Date"
        else -> null
    }

    f.fieldName = colNameToCamel(f.columnName)

}

fun dbFieldsExists(fields: List<JavaField>, dbFieldName: String): Boolean {
    return fields.any {
        it.columnName == dbFieldName
    }
}


//设置随机值
fun generateFieldRndSet(indent: String, varName: String, field: JavaField): String {
    val value=
            if(field.isPk) "1L"
            else
                when(field.fieldCls){
                    "Long"->"${Rnd.getRandomNum(100,1000000)}L"
                    "Double"->"${Rnd.getRandomNum(0,100)}D"
                    "Integer"->"${Rnd.getRandomNum(1,4)}"
                    "String"->""""abcd""""
                    "Date"->"""new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("${DateUtil.getCurrentDate()} 00:00:00")"""
                    else -> ""
                }

    return """${indent}${varName}.${generatePrefixMethod("set",field.fieldName)}($value);"""
}


fun fieldExclude(fields: List<JavaField>, vararg exList:String):List<JavaField> {
    return fields.filter { !exList.contains(it.fieldName) }
}
fun fieldExcludeByColumnName(fields: List<JavaField>, vararg exList:String):List<JavaField> {
    return fields.filter { !exList.contains(it.columnName) }
}

//设置随机值
fun generateFieldsRndSetExclude(indent:String, varName:String, fields: MutableList<JavaField>, vararg exList:String): String {
    val filtered = fields.filter { !exList.contains(it.fieldName) }
    return iterAndAppend(filtered){
        idx, javaField ->
        generateFieldRndSet(indent,varName,javaField)
    }
}

//生成@RequestParam
fun generateFieldsRequestParamExclude(indent:String,  fields: MutableList<JavaField>, vararg exList:String): String {
    val filtered = fields.filter { !exList.contains(it.fieldName) }
    return iterAndAppend(filtered){
        idx, javaField ->
        val hasNext = idx!=filtered.lastIndex
        val required = javaField.nullable.toString()
        """${indent}@RequestParam(value = "${javaField.fieldName}", required = $required) ${javaField.fieldCls} ${javaField.fieldName}${if(hasNext) "," else ""}"""
    }
}
//生成showdoc
fun generateFieldsShowdocExclude(indent:String,  fields: MutableList<JavaField>, vararg exList:String): String {
    val filtered = fields.filter { !exList.contains(it.fieldName) }
    return iterAndAppend(filtered){
        idx, javaField ->
        val hasNext = idx!=filtered.lastIndex
        val required = if(javaField.nullable) "否" else "是"
        """${indent}|${javaField.fieldName} |$required  |${javaField.fieldCls} | ${javaField.remark}    |"""
    }
}
//生成set
fun generateFieldsSetExclude(indent:String, varName:String, fields: MutableList<JavaField>, vararg exList:String): String {
    val filtered = fields.filter { !exList.contains(it.fieldName) }
    return iterAndAppend(filtered){
        idx, javaField ->
        val hasNext = idx!=filtered.lastIndex
        val required = javaField.nullable.toString()
        val setMethodName = generatePrefixMethod("set", javaField.fieldName)
        """${indent}$varName.$setMethodName(${javaField.fieldName});"""
    }
}

fun <T> iterAndAppend(fields: List<T>,con:(idx:Int,T)->String):String {
    val sb = StringBuilder()

    fields.forEachIndexed { index, it ->
        sb.append(con(index, it))
        if(index!=fields.lastIndex)
            sb.append("\r\n")
    }

    return sb.toString()
}

//解析表元数据 获得list
fun queryFieldList(DB_URL: String, DATABASE: String, TBL_NAME: String): MutableList<JavaField> {
    //下面别看了  具体逻辑
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
        val nullable = colRs.getInt("NULLABLE") //1:可
        val message = colRs.getString("REMARKS")
        val columnDef  = colRs.getString("COLUMN_DEF") // 该列的默认值 当值在单引号内时应被解释为一个字符串
        //        println("$columnName $columnType $datasize $digits $nullable $columnDef")
        //        println(message)
        //        println(colRs.getString("IS_AUTOINCREMENT"))

        val javaField = JavaField(columnName, columnType)
        javaField.remark = message ?: ""
        javaField.nullable = nullable == 1
        javaField.defaultValue = columnDef

        calcField(javaField)
        list.add(javaField)
    }

    //表主键
    val pkRs = metaData.getPrimaryKeys(DATABASE, null, TBL_NAME)
    pkRs.next()
    val pkColName = pkRs.getString("COLUMN_NAME")
    val pkColJavaName = colNameToCamel(pkColName)

    list.filter { it.columnName==pkColName }.forEach { it.isPk=true }
    return list
}


fun generateStringBodyOfField(fieldExclude: List<JavaField>,split:String? = "", function: (JavaField) -> String): String {
    var ret = ""

    fieldExclude.forEachIndexed { index, javaField ->
        ret+=function(javaField)

        if (index< fieldExclude.lastIndex) {
            ret +=split
        }
    }

    return ret
}

