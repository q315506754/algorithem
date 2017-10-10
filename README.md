[![Build Status运行状态](https://travis-ci.org/q315506754/algorithem.svg?branch=master)](https://travis-ci.org/q315506754/algorithem)
# algorithe m< 和 &    &lt; 和 &amp;。  &copy;
#段落、标题、区块代码
    1 > 2?


## 标题 选择性闭合######




```html
<html>
```

* 区块元素
 1. 段落和换行 Setext 和类 atx
 1. 标题
 1. 区块引用 Blockquotes >
 1. 列表
 1. 代码区块
 1. 分隔线
* 区段元素
 1. 链接 [example link](http://example.com/).
 1. 强调 *sdfsdf* _double underscores啊啊_ **sdfsdf** __double underscores啊啊__
 1. 代码    sdfsdf `asdasdas`
 1. 图片 ![alt text](/path/to/img.jpg "Title")
* 其它
 1. 自动链接 <http://example.com/>  <address@example.com>
 1. 反斜杠 \*literal asterisks\*


转自<http://www.appinn.com/markdown/index.html>

* * *

***

*****

- - -

---------------------------------------
Setext A First Level Header
====================
A Second Level Header
---------------------

gfdgddf后面有2空格=换行
gfdgfdg后面有n空格
dgfdfg



##区块
> This is a blockquote.
>
> This is the second paragraph in the blockquote.
>
> ## This is an H2 in a blockquote

>sdf
>sd
>fsdfsd
>f

>dfgfdgdfgfdgdfgdfgdfgdfgdfgfddfgg
fdgdfgdfgdfgdfgfdgfd  d
dfgdfgfdgfdgf
fdgdfgdf

> This is the first level of quoting.
>
> > This is nested blockquote.
>>This is nested blockquote.
>
> Back to the first level.

>
>
>>
>>
>>>
>>>1L
>>>
>>
>>2L
>>
>
>3L
>

>>>1L
>>
>>2L
>
>3L

haha

> ## 这是一个标题。
>
> 1.   这是第一行列表项。
> 2.   这是第二行列表项。
>
> 给出一些例子代码：
>
>     return shell_exec("echo $input | $markdown_script");


### Header 3 atx
标题

#修辞和强调
Some of these words *are emphasized*.
Some of these words _are emphasized also_.
Use two asterisks for **strong emphasis**.
Or, if you prefer, __use two underscores instead__.

#列表
* Candy.
* Gum.
* Booze.
+ Candy.
+ Gum.
+ Booze.
- Candy.
- Gum.
- Booze.

* A list item.
With multiple paragraphs.

* Another item in the list.

1.  1Bird
1.  1McHale
1.  1Parish

3. 3Bird
1. 1McHale
8. 8Parish

*   Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
    Aliquam hendrerit mi posuere lectus. Vestibulum enim wisi,
    viverra nec, fringilla in, laoreet vitae, risus.
*   Donec sit amet nisl. Aliquam semper ipsum sit amet velit.
    Suspendisse id sem consectetuer libero luctus adipiscing.

*Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
    Aliquam hendrerit mi posuere lectus. Vestibulum enim wisi,
    viverra nec, fringilla in, laoreet vitae, risus.
*Donec sit amet nisl. Aliquam semper ipsum sit amet velit.
    Suspendisse id sem consectetuer libero luctus adipiscing.

*   Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
Aliquam hendrerit mi posuere lectus. Vestibulum enim wisi,
viverra nec, fringilla in, laoreet vitae, risus.
*   Donec sit amet nisl. Aliquam semper ipsum sit amet velit.
Suspendisse id sem consectetuer libero luctus adipiscing.

* Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
Aliquam hendrerit mi posuere lectus. Vestibulum enim wisi,
viverra nec, fringilla in, laoreet vitae, risus.
* Donec sit amet nisl. Aliquam semper ipsum sit amet velit.
Suspendisse id sem consectetuer libero luctus adipiscing.

*   Bird
*   Magic

*   Bird

*   Magic

1.  This is a list item with two paragraphs. Lorem ipsum dolor
    sit amet, consectetuer adipiscing elit. Aliquam hendrerit
    mi posuere lectus.

    Vestibulum enim wisi, viverra nec, fringilla in, laoreet
    vitae, risus. Donec sit amet nisl. Aliquam semper ipsum
    sit amet velit.

2.  Suspendisse id sem consectetuer libero luctus adipiscing.

*   This is a list item with two paragraphs.

    This is the second paragraph in the list item. You're
only required to indent the first line. Lorem ipsum dolor
sit amet, consectetuer adipiscing elit.

*   Another item in the same list.


*   A list item with a blockquote:

    > This is a blockquote
    > inside a list item.

*   A list item with a blockquote:

    > This is a blockquote
    >dsfsdfsdfsdfsdfsdfsdf
    > inside a list item.

*   一列表项包含一个列表区块：

        <代码写在这>
        fxgdfgfdg
        dfgdfgdfgfd

Here is an example of AppleScript:

    tell application "Foo"
        beep
    end tell


Here is an example of AppleScript:

    tell application "Foo"
        beep
    end tell


#行内形式是直接在后面用括号直接接上链接：
This is an [example link](http://example.com/).

This is an [example link](http://example.com/ "With a Title").

#参考形式的链接让你可以为链接定一个名称，之后你可以在文件的其他地方定义该链接的内容：


I get 10 times more traffic from [Google][1] than from
[Yahoo][2] or [MSN][3].

[1]: http://google.com/ "Google"
[2]: http://search.yahoo.com/ "Yahoo Search"
[3]: http://search.msn.com/ "MSN Search"

#title 属性是选择性的，链接名称可以用字母、数字和空格，但是不分大小写：
I start my morning with a cup of coffee and
[The New York Times][NY Times].

[ny times]: http://www.nytimes.com/

#图片
##图片的语法和链接很像。
##行内形式（title 是选择性的）：
![alt text](/path/to/img.jpg "Title")

##参考形式：

![alt text][id]

[id]: /path/to/img.jpg "Title"

#Code
I strongly recommend against using any `<blink>` tags.

I wish SmartyPants used named entities like `&mdash;`
instead of decimal-encoded entites like `&#8212;`.

I strongly recommend against using any <blink> tags.
I wish SmartyPants used named entities like &mdash;
instead of decimal-encoded entites like &#8212;.
<h2>aaaaa</h2>
<script>
    alert('[mode]Debug enabled...');
</script>

If you want your page to validate under XHTML 1.0 Strict,
you've got to put paragraph tags in your blockquotes:

<blockquote>
<p>For example.</p>
</blockquote>

#tabtab
  x1If you want your page to validate under XHTML 1.0 Strict,
    x2you've got to put paragraph tags in your blockquotes:
        x4you've got to put paragraph tags in your blockquotes:

    至少2个tab才会认为是代码,还需要前面的空行
    public RemoteException setMessage(String message) {
            this.message = message;
            return this ;
    }
      3个tab 前面没空行
      public RemoteException setMessage(String message) {
              this.message = message;
              return this ;
      }

      4个tab 前面有空行
      public RemoteException setMessage(String message) {
              this.message = message;
              return this ;
      }


        5个tab 前面有2空行
        public RemoteException setMessage(String message) {
                this.message = message;
                return this ;
        }


#反引号
`If you want your page to validate under XHTML 1.0 Strict,`
`you've got to put paragraph tags in your blockquotes:`

`If you want your page to validate under XHTML 1.0 Strict,
you've got to put paragraph tags in your blockquotes:`

#space
    x4If you want your page to validate under XHTML 1.0 Strict,
    x4you've got to put paragraph tags in your blockquotes:
        x8you've got to put paragraph tags in your blockquotes:
