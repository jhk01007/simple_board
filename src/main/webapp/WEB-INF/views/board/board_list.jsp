<%@ page import="java.util.List" %>
<%@ page import="org.example.board.domain.Board" %>
<%@ page import="java.util.Map" %>
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
        }
        .container {
            width: 80%;
            max-width: 800px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-height: calc(100vh - 40px);
            overflow-y: auto;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 10px;
        }
        th, td {
            padding: 15px;
            text-align: center;
            background-color: #fafafa;
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
            border: none;
        }
        th {
            background-color: #f0f0f0;
            color: #333;
        }
        th.title, td.title {
            width: 50%; /* 제목 열의 너비를 넓게 설정 */
            text-align: left; /* 제목 텍스트는 왼쪽 정렬 */
        }
        th.created-at, td.created-at {
            width: 25%; /* 생성일 열의 너비를 좁게 설정  */
        }
        td.title a {
            text-decoration: none;
            color: #333;
            font-weight: bold;
            transition: color 0.3s ease;
        }
        td.title a:hover {
            color: #5cb85c;
        }
        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            padding: 8px 12px;
            text-decoration: none;
            background-color: #f0f0f0;
            color: #333;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .pagination a:hover {
            background-color: #5cb85c;
            color: #fff;
        }
        .pagination .current-page {
            background-color: #5cb85c;
            color: #fff;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>게시판 글 목록</h2>
    <table>
        <thead>
        <tr>
            <th class="title">제목</th>
            <th>작성자</th>
            <th class="created-at">작성일</th>
            <th>조회수</th>
        </tr>
        </thead>
        <tbody>
        <%
            // request에서 "pageData"라는 이름의 attribute를 가져옴
            Map<String, Object> pageData = (Map<String, Object>) request.getAttribute("pageData");
            List<Board> boards = (List<Board>) pageData.get("boards");
            int startPage = (int) pageData.get("startPage");
            int endPage = (int) pageData.get("endPage");
            int totalPageCount = (int) pageData.get("totalPageCount");
            int currentPage = (int) pageData.get("page");

            if (boards != null && !boards.isEmpty()) {
                for (Board board : boards) {
        %>
        <tr>
            <td class="title"><a href="<%=request.getContextPath()%>/boards/<%=board.getId()%>"><%= board.getTitle() %></a></td>
            <td><%= board.getWriter() %></td>
            <td class="created-at"><%= board.getCreatedAt() %></td>
            <td><%= board.getReadCount() %></td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

    <!-- 페이지 네비게이션 -->
    <div class="pagination">
        <%
            if(startPage != 1) {
        %>
        <a href="<%=request.getContextPath()%>/boards?page=<%=startPage - 1%>">&lt;이전</a>
        <%
            }
            for (int i = startPage; i <= endPage; i++) {
                if (i == currentPage) {
        %>
        <a class="current-page" href="<%=request.getContextPath()%>/boards?page=<%=i%>"><%=i%></a>
        <%
        } else {
        %>
        <a href="<%=request.getContextPath()%>/boards?page=<%=i%>"><%=i%></a>
        <%
                }
            }
            if(endPage != totalPageCount) {
        %>
        <a href="<%=request.getContextPath()%>/boards?page=<%=endPage + 1%>">다음&gt;</a>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
