<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getContextPath();
    System.out.println(basePath);
    request.setAttribute("basePath",basePath);
%>
<link href="${basePath}/assets/css/common.css" rel="stylesheet"/>
<link href="${basePath}/assets/css/eleme.css" rel="stylesheet"/>
<script>
    let basePath = "${basePath}";
</script>
