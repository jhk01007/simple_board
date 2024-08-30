<%--
  Created by IntelliJ IDEA.
  User: kimjaehee
  Date: 2024. 8. 14.
  Time: 오후 4:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>message</title>
</head>
<body>
    <script>
        // 해당 부분에서 alert와 함께 context path로 리다이렉션함
        alert('<%=request.getAttribute("msg")%>')
        location.href = "<%=request.getAttribute("path")%>"
    </script>
</body>
</html>
