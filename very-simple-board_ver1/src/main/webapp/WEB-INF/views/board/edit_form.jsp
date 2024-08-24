<%@ page import="org.example.day0819_board_project.domain.Board" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 수정하기</title>
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
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin: 20px auto;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        .form-group input[type="text"], .form-group textarea {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 16px;
            color: #333;
        }
        .form-group textarea {
            height: 200px;
            resize: vertical;
        }
        .actions {
            text-align: center;
            margin-top: 20px;
        }
        .btn {
            padding: 10px 20px;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
            background-color: #5cb85c;
            transition: background-color 0.3s ease;
            text-align: center;
            cursor: pointer;
            font-size: 16px;
            border: none;
        }
        .btn:hover {
            background-color: #4cae4c;
        }
    </style>
    <script type="text/javascript">
        function confirmUpdate() {
            return confirm('수정하시겠습니까?');
        }
    </script>
</head>
<body>
<%
    Board board = (Board) request.getAttribute("edit");
%>
<div class="container">
    <h2>글 수정하기</h2>
    <form action="<%=request.getContextPath()%>/boards/edit/<%=board.getId()%>" method="post" onsubmit="return confirmUpdate();">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" value="<%= board.getTitle() %>" required>
        </div>
        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="content" required><%= board.getContent() %></textarea>
        </div>
        <div class="actions">
            <button type="submit" class="btn">수정</button>
        </div>
    </form>
</div>
</body>
</html>
