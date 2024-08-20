<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #fff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-container input[type="text"], .form-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .form-container .remember-me {
            display: flex;
            align-items: center;
            margin: 10px 0;
        }
        .form-container .remember-me input[type="checkbox"] {
            margin-right: 10px;
        }
        .form-container input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .form-container input[type="submit"]:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    String default_id = "";
    for(Cookie cookie: cookies) {
        if(cookie.getName().equals("rememberId")) {
            default_id = cookie.getValue();
            break;
        }
    }
%>
<div class="form-container">
    <h2>로그인</h2>
    <form action="<%=request.getContextPath()%>/members/login" method="post">
        <input type="text" name="memberid" placeholder="아이디" value="<%=default_id%>" required>
        <input type="password" name="password" placeholder="비밀번호" required>
        <div class="remember-me">
            <input type="checkbox" id="rememberMe" name="rememberMe">
            <label for="rememberMe">아이디 기억하기</label>
        </div>
        <input type="submit" value="로그인">
    </form>
</div>
</body>
</html>
