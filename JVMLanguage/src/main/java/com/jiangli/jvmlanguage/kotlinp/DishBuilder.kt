package deskzxcv

import java.math.BigDecimal
import java.util.*

fun main(args: Array<String>) {
    val dsk = desk{
        f(c_rice) //米饭
        f(c_vegetable) //蔬菜

        f(c_mpdf) //麻婆豆腐

        f(c_xcr) //农家小炒肉
    }

    dsk.consistOf()
}


///////////////////菜/////////////////////////////////
internal val c_rice=Food("米饭").consistOf(
        Material("米", 500.0,MassUnit.G)
)
internal val c_vegetable=Food("蔬菜").consistOf(
        Material("蔬菜", 1.0,MassUnit.Ba)
)
internal val c_mpdf=Food("麻婆豆腐").consistOf(
        Material("蒜末", 3.0,MassUnit.Ge),
        Material("姜末", 4.0,MassUnit.Pian),
        Material("葱粒", 20.0,MassUnit.Li),
        Material("勾芡", 20.0,MassUnit.mL),
        Material("卤水盐豆腐", 2.0,MassUnit.Kuai),

        Material("牛肉酱", 20.0,MassUnit.mL),
        Material("花椒粉", 1.0,MassUnit.little),
        Material("红油", 5.0,MassUnit.mL)
)
internal val c_xcr=Food("农家小炒肉").consistOf(
        Material("蒜末", 3.0,MassUnit.Ge),
        Material("姜末", 4.0,MassUnit.Pian),
        Material("勾芡", 20.0,MassUnit.mL),
        Material("腌制猪肉片", 300.0,MassUnit.G),
        Material("青椒段", 3.0,MassUnit.Ge),

        Material("老抽", 3.0,MassUnit.mL)
)


///////////////////Facade/////////////////////////////////

internal fun desk(fc: Desk.() -> kotlin.Unit): Desk {
    val desk = Desk(ArrayList())
    desk.apply (fc)
    return desk
}

///////////////////Definition/////////////////////////////////
internal class Desk(val foods: MutableList<Food>) {
    var weight:Int = 2

    fun f(food:Food) = foods.add(food)

    fun consistOf(){
        foods.flatMap { it.materials }.groupBy { it.name }.forEach { n, li ->
            run {
                fun getNumStr(d:Double) = "${BigDecimal(d).setScale(0)}"
                fun getNum(m:Material) = "${getNumStr(m.num)} ${m.unit.nameCn} (${m.food.name})"

                if (li.size == 1)
                    println("$n:${getNum(li[0])}")
                else {
                    var c =0
                    println("$n:${getNumStr(li.sumByDouble { it.num })} ${li[0].unit.nameCn} = [${li.size}次, ${li.fold("",{str,m ->
                        val s = str+getNum(m)+if(c<li.size-1) ", " else ""
                        c++
                        s
                    })}]")
                }
            }
        }
    }
}
internal class Food(val name: String) {
    val materials: MutableList<Material> = ArrayList()
    fun consistOf(vararg ms:Material) : Food {
        ms.forEach { it.food = this }
        materials.addAll(ms)
        return this
    }
}
internal class Material(val name: String,val num:Double,val unit: MassUnit) {
    lateinit var food:Food

    init {
//        println("adasd,${this.name}")
    }
}
internal class BaseMaterial(val name: String) {
}
internal enum class MassUnit(val nameCn:String) {
    G("克"),Kg("千克"),Shao("勺"),little("少许"),mL("mL"),L("L"),Ge("个"),Pian("片"),Ke("颗"),Li("粒"),Kuai("块"),Ba("把")
}