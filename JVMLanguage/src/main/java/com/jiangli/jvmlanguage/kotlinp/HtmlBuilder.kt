package com.jiangli.jvmlanguage.kotlinp

/**
 * Created by Jiangli on 2017/5/27.
 */
import java.util.*

fun main(args: Array<String>) {
    println(Tag("aaa"))
    println(Table())
    println(Tag("aaa").apply { this.attributes.add(Attribute("css","12332")) })
    println(html {
        this.children.add(Tag("head"))
        text("aaa")
    })

    println(html {
        this.children.add(Tag("head"))
    }.table {tr("#aaa"){}})

    println(html {
        table {
            tr("#aaa"){
                td("#bbb"){}
            }
        }
    })
}
fun renderProductTable(): String {
    //应该禁止下列调用
    html{
        table {
            // 错误：外部接收者的成员
//            table {
//                table {
//
//                }
//            }
            this@html.table {
                this@html.table {
                    this@html.table {

                    } // 可能
                } // 可能
            } // 可能
        }
    }
    return html {
        table {
            tr(getTitleColor()) {
                td {
                    text("Product")
                }
                td {
                    text("Price")
                }
                td {
                    text("Popularity")
                }
            }
            val products = getProducts()
            for ((index, value) in products.withIndex()) {
                tr {
                    td(getCellColor(index,0)) {
                        text(value.description)
                    }
                    td(getCellColor(index,1)) {
                        text(value.price)
                    }
                    td(getCellColor(index,2)) {
                        text(value.popularity)
                    }
                }
            }

        }
    }.toString()
}

data class Product2(val description: String, val price: Double, val popularity: Int)
fun getProducts() = listOf( Product2("cactus", 11.2, 13))

////////////////////////////////////////////////////
fun getTitleColor() = "#b9c9fe"
fun getCellColor(row: Int, column: Int) = if ((row + column) %2 == 0) "#dce4ff" else "#eff2ff"
/////////////////////////////////
@HtmlTagMarker
//我们不必用 @HtmlTagMarker 标注 HTML 或 Head 类，因为它们的超类已标注过：
//在添加了这个注解之后，Kotlin 编译器就知道哪些隐式接收者是同一个 DSL 的一部分，并且只允许调用最近层的接收者的成员：
open class Tag(val name: String) {
    val children: MutableList<Tag> = ArrayList()
    val attributes: MutableList<Attribute> = ArrayList()

    override fun toString(): String {
        return "<$name" +
                (if (attributes.isEmpty()) "" else attributes.joinToString(separator = "", prefix = " ")) + ">" +
                (if (children.isEmpty()) "" else children.joinToString(separator = "")) +
                "</$name>"
    }
}

class Attribute(val name : String, val value : String) {
    override fun toString() = """$name="$value" """
}

fun <T: Tag> T.set(name: String, value: String?): T {
    if (value != null) {
        attributes.add(Attribute(name, value))
    }
    return this
}

fun <T: Tag> Tag.doInit(tag: T, init: T.() -> Unit): T {
    tag.init()
    children.add(tag)
    return tag
}


//请注意，@DslMarker 注解在 Kotlin 1.1 起才可用。
@DslMarker
annotation class HtmlTagMarker
//如果一个注解类使用 @DslMarker 注解标注，那么该注解类称为 DSL 标记。

class Html: Tag("html")
class Table: Tag("table")
class Center: Tag("center")
class TR: Tag("tr")
class TD: Tag("td")
class Text(val text: String): Tag("b") {
    override fun toString() = text
}

fun html(init: Html.() -> Unit): Html = Html().apply(init)

fun Html.table(init : Table.() -> Unit) = doInit(Table(), init)
fun Html.center(init : Center.() -> Unit) = doInit(Center(), init)

fun Table.tr(color: String? = null, init : TR.() -> Unit) = doInit(TR(), init).set("bgcolor", color)

fun TR.td(color: String? = null, align : String = "left", init : TD.() -> Unit) = doInit(TD(), init).set("align", align).set("bgcolor", color)

fun Tag.text(s : Any?) = doInit(Text(s.toString()), {})
