package com.jiangli.db.tool

/**
 *


http://rap.i.zhihuishu.com/showdoc/index.php?s=/52&page_id=8524

见 [排序类型](http://rap.i.zhihuishu.com/showdoc/index.php?s=/52&page_id=8524 "排序类型")
1正序 -1降序

 * @author Jiangli
 * @date 2021/3/1 16:55
 */
fun main(args: Array<String>) {
    var code = """
  case 1:comparing = MyComparator.getPinYinComparator(pinyinComparator,TblSpecialTrainingCampBaseOpenDto::getTitle);break;//标题
                    case 2:comparing = MyComparator.comparing(TblSpecialTrainingCampBaseOpenDto::getActivityStartTime);break;//上课开始时间
                    case 3:comparing = MyComparator.comparing(TblSpecialTrainingCampBaseOpenDto::getActivityEndTime);break;//上课结束时间
                    case 4:comparing =  MyComparator.getPinYinComparator(pinyinComparator,TblSpecialTrainingCampBaseOpenDto::getCity0Name);break;//省份
                    case 5:comparing =  MyComparator.getPinYinComparator(pinyinComparator,TblSpecialTrainingCampBaseOpenDto::getCity1Name);break;//城市
                    case 6:comparing = MyComparator.comparing(TblSpecialTrainingCampBaseOpenDto::getActivityType);break;//活动类型
                    case 7:comparing =  MyComparator.getPinYinComparator(pinyinComparator,TblSpecialTrainingCampBaseOpenDto::getHostTypeName);break;//主办部门
               
                   """.trimIndent()

    code.split("\n").forEach {
        val line = it.toString().trim()
        if (line.isBlank()) {
            return@forEach
        }

        val s = "case"
        val e = "//"
        val i = line.substring(line.indexOf(s) + s.length,line.indexOf(":")).trim()
        val comment = line.substring(line.indexOf(e) + e.length).trim()
        print("${i}:${comment} ")
//        print("| ${i}  |  ${comment}  | \n")
    }
}