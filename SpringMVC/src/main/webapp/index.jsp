<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/7/29 0029
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <script>
      
  </script>
  <body>
  $END$ sdsdsdxxxxsf地方

  #emmet (zen coding) 用tab展开<br>
  ul#nav>li.item$*4>a{Item $} <br>


  <ul id="nav">
    <li class="item1"><a href="">Item 1</a></li>
    <li class="item2"><a href="">Item 2</a></li>
    <li class="item3"><a href="">Item 3</a></li>
    <li class="item4"><a href="">Item 4</a></li>
  </ul>
  <br>

  ul#nav > li.item$*4 > a{Item $} 中间不能有空格，否则展开中断 <br>


  multi <br>
  ul>li*5 <br>
  ul>li.item$$$*5 <br>
  <ul>
    <li class="item001"></li>
    <li class="item002"></li>
    <li class="item003"></li>
    <li class="item004"></li>
    <li class="item005"></li>
  </ul>

  正序 <br>
  ul>li.item$*5 <br>
  <ul>
    <li class="item1"></li>
    <li class="item2"></li>
    <li class="item3"></li>
    <li class="item4"></li>
    <li class="item5"></li>
  </ul>

  倒序 <br>
  ul>li.item$@-*5 <br>
  <ul>
    <li class="item5"></li>
    <li class="item4"></li>
    <li class="item3"></li>
    <li class="item2"></li>
    <li class="item1"></li>
  </ul>

  初始 <br>
  ul>li.item$@3*5 <br>
  <ul>
    <li class="item3"></li>
    <li class="item4"></li>
    <li class="item5"></li>
    <li class="item6"></li>
    <li class="item7"></li>
  </ul>


  table <br>
  table>(thead>th>td)+(tbody>tr>td) <br>


  link
  link:css
  script
  script:src
  meta
  meta:utf
  input
  input:email

  </body>
</html>
