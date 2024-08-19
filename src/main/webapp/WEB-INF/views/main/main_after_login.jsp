<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>OO 게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 30px 50px;
            border-radius: 10px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            font-size: 16px;
            text-decoration: none;
            color: #fff;
            background-color: #5cb85c;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
<div class="container">
    <h1><%=request.getAttribute("login")%>님 환영합니다.</h1>
    <a href="<%=request.getContextPath()%>/boards" class="btn">목록 보기</a>
    <a href="${pageContext.request.contextPath}/boards/write" class="btn">글 작성하기</a>
    <a href="${pageContext.request.contextPath}/logout" class="btn">로그아웃 하기</a>
</div>
</body>
</html>
