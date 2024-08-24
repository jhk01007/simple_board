<%@ page import="java.util.List" %>
<%@ page import="org.example.board.domain.Board" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            width: 80%;
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .card-list {
            list-style-type: none;
            padding: 0;
        }
        .card-list li {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fafafa;
            margin: 10px 0;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
            font-size: 16px;
            color: #555;
        }
        .card-list .title a {
            text-decoration: none;
            color: #333;
            font-weight: bold;
            transition: color 0.3s ease;
        }
        .card-list .title a:hover {
            color: #5cb85c;
        }
        .card-list .author {
            font-style: italic;
            color: #777;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>게시판 글 목록</h2>
    <ul class='card-list'>
        <%
            // request에서 "boards"라는 이름의 attribute를 가져옴
            List<Board> boards = (List<Board>) request.getAttribute("boards");

            // null 체크 및 리스트 출력
            if (boards != null && !boards.isEmpty()) {
                for (Board board : boards) {
        %>
        <li>
            <span class="title">
                <a href="<%=request.getContextPath()%>/boards/<%=board.getId()%>"><%= board.getTitle() %></a>
            </span>
            <span class="author"><%= board.getWriter() %></span>
        </li>
        <%
            }
        }
        %>
    </ul>
</div>
</body>
</html>
