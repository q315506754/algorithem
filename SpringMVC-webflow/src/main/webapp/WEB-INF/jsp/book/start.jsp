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
    <title>enterBookingDetails</title>
</head>
<body>
start.jsp

<form>
    <input type="hidden" name="execution" value="${flowExecutionKey}" />

    <select name="_eventId">
        <option value="next">next</option>
        <option value="previous">previous</option>
        <option value="event">event</option>
        <option value="submit">submit</option>
        <option value="cancel">cancel</option>
    </select>

    <input type="submit" value="submit"/>
</form>


</body>
</html>
