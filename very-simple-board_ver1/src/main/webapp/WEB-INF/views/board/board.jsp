    <%@ page import="org.example.day0819_board_project.domain.Board" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>게시글 상세보기</title>
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
            .info {
                margin-bottom: 20px;
            }
            .info span {
                display: block;
                margin-bottom: 5px;
                color: #555;
            }
            .content {
                margin-bottom: 30px;
                line-height: 1.6;
                color: #444;
            }
            .actions {
                display: flex;
                justify-content: space-between;
            }
            .btn {
                padding: 10px 20px;
                color: #fff;
                text-decoration: none;
                border-radius: 5px;
                background-color: #5cb85c;
                transition: background-color 0.3s ease;
                text-align: center;
                cursor: pointer; /* 손 모양 커서 */
                border: none; /* 검정 테두리 제거 */
            }
            .btn:hover {
                background-color: #4cae4c;
            }
            .btn-danger {
                background-color: #d9534f;
            }
            .btn-danger:hover {
                background-color: #c9302c;
            }
        </style>
    </head>
    <body>
    <%
        Board board = (Board) request.getAttribute("board");
    %>
    <div class="container">
        <h2>게시글 상세보기</h2>
        <div class="info">
            <span><strong>제목:</strong> <%= board.getTitle() %></span>
            <span><strong>작성자:</strong> <%= board.getWriter() %></span>
            <span><strong>작성시간:</strong> <%= board.getCreatedAt() %></span>
            <span><strong>조회수:</strong> <%= board.getReadCount() %></span>
        </div>
        <div class="content">
            <p><%= board.getContent() %></p>
        </div>
        <div class="actions">
            <%
                // 만약 세션에 저장된 아이디와 글쓴 사람과 같으면 수정, 삭제버튼 노출
                String login = (String) session.getAttribute("login");
                if(login != null && login.equals(board.getWriter())) {
            %>
                <form action="<%=request.getContextPath()%>/boards/edit/<%=board.getId()%>">
                    <button type="submit" class="btn">수정하기</button>
                </form>
                <form action="<%=request.getContextPath()%>/boards/delete/<%= board.getId() %>" method="post" onsubmit="return confirm('정말로 삭제하시겠습니까?');">
                    <button type="submit" class="btn btn-danger">삭제하기</button>
                </form>

            <%
                }
            %>
        </div>
    </div>
    </body>
    </html>
