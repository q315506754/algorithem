<%--
  Created by IntelliJ IDEA.
  User: Jiangli
  Date: 2018/12/29
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>reviewBooking.jsp</title>
</head>
<body>
reviewBooking.jsp

start.jsp

<form>
    <input type="hidden" name="execution" value="${flowExecutionKey}" />

    <select name="_eventId">
        <option value="confirm">confirm</option>
        <option value="revise">revise</option>
        <option value="cancel">cancel</option>
    </select>


    <input type="submit" value="submit"/>
</form>

</body>
</html>
