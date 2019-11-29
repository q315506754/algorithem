
import org.springframework.util.PropertyPlaceholderHelper
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2019/11/6 11:04
 */
fun changePkg(body: String, pkg: String): String {
    val split = body.split("\r\n") as MutableList
    split[0] = pkg
    return  split.joinToString("\r\n")
}
fun colNameToCamel(f:String):String {
    var lowerCase = f.toLowerCase()

    while (lowerCase.contains("_")) {
        val first = lowerCase.indexOfFirst { it == '_' }
        lowerCase=lowerCase.substring(0,first) + lowerCase.get(first+1).toUpperCase() + lowerCase.substring(first+2)
    }
    return lowerCase
}
fun tblNameToCamel(f:String):String {
    var ret = colNameToCamel(f)
    return nameToMethod(ret)
}
fun nameToCamel(f:String):String {
    return f[0].toLowerCase()+ f.substring(1)
}
fun nameToMethod(f:String):String {
    return f[0].toUpperCase()+ f.substring(1)
}
fun generatePrefixMethod(prefix: String, fieldName: String): String {
    return """${prefix}${nameToMethod(fieldName)}"""
}

fun concatPath(vararg path:String): String {
    val sb = StringBuilder()
    path.forEach {
        sb.append(it)
        sb.append(System.getProperty("file.separator"))
    }
    sb.deleteCharAt(sb.lastIndex)
    val absPath = sb.toString()
    return absPath
}


fun pend(list: MutableList<String>, vararg str: String):List<String> {
    str.forEach {
        list.add(it)
    }
    return list
}
fun pendNew(list: MutableList<String>, vararg str: String):List<String> {
    var allList = mutableListOf<String>()
    allList.addAll(list)

    return pend(allList,*str)
}

fun resolveBodyBySpring(body:String,map:Map<out Any,out Any>): String {
    val x =  PropertyPlaceholderHelper("\${","}",":",true)
    val props= Properties()
    props.putAll(map)
    val realBody  = x.replacePlaceholders(body, props)
    return realBody
}

