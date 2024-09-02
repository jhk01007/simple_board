<%@ page import="java.util.List" %>
<%@ page import="org.example.recipe_board_jpa.dto.join.RecipeWithServiceId" %>
<%@ page import="org.example.recipe_board_jpa.dto.ImageDTO" %>
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
            cursor: pointer;
            border: none;
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

        /* New Styles for Images */
        .images-gallery {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 20px;
        }

        .images-gallery img {
            max-width: 100%;
            border-radius: 5px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        /* Styles for Comments */
        .comments-section {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
        }

        .comment {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }

        .comment p {
            margin: 5px 0;
        }

        .comment-author {
            font-weight: bold;
            color: #333;
        }

        .comment-date {
            color: #777;
            font-size: 0.9em;
        }

        .comment-form {
            margin-top: 20px;
        }

        .comment-form textarea {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            resize: none;
        }

        .comment-form button {
            margin-top: 10px;
            padding: 10px 20px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .comment-form button:hover {
            background-color: #4cae4c;
        }

        /* New styles for replies */
        .replies {
            margin-left: 20px;
            padding-left: 15px;
            border-left: 2px solid #f9f9f9;
            background: #f9f9f9;
            margin-top: 10px;
        }

        .replies .comment-author {
            color: #555;
        }

        .replies .comment-date {
            color: #aaa;
            font-size: 0.85em;
        }

        /* Styles for Reply Button */
        .reply-button {
            background-color: #eee;
            border: none;
            padding: 5px 10px;
            margin-top: 5px;
            cursor: pointer;
            font-size: 0.9em;
            border-radius: 3px;
            color: #555;
        }

        .reply-button:hover {
            background-color: #ddd;
        }

        /* Styles for Reply Form */
        .reply-form {
            margin-top: 10px;
            margin-left: 20px;
            display: flex;
            flex-direction: column;
        }

        .reply-form textarea {
            width: 100%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ddd;
            resize: none;
            margin-bottom: 5px;
        }

        .reply-form button {
            align-self: flex-start;
            padding: 5px 10px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .reply-form button:hover {
            background-color: #4cae4c;
        }

    </style>

</head>
<body>
<%
    RecipeWithServiceId recipe = (RecipeWithServiceId) request.getAttribute("recipe");
%>
<div class="container">
    <h2>게시글 상세보기</h2>
    <div class="info">
        <span><strong>제목:</strong> <%= recipe.getFoodName() %></span>
        <span><strong>작성자:</strong> <%= recipe.getWriter() %></span>
        <span><strong>작성시간:</strong> <%= recipe.getCreatedAt() %></span>
        <span><strong>조회수:</strong> <%= recipe.getReadCount() %></span>
        <%
            if (recipe.getImages() != null && !recipe.getImages().isEmpty()) {
        %>
        <!-- Start of Image Gallery -->
        <div class="images-gallery">
            <%
                for (ImageDTO i : recipe.getImages()) {
            %>
            <a href="<%=request.getContextPath()%>/recipes/download?id=<%=i.getId()%>">첨부파일 : <%=i.getOriginalName()%></a><br>
            <%
                }
            %>
        </div>
        <!-- End of Image Gallery -->
        <%
            }
        %>
    </div>
    <div class="content">
        <p><%= recipe.getProcess() %></p>
    </div>
    <div class="actions">
        <%
            Long login = (Long) session.getAttribute("login");
            if (login != null && login.equals(recipe.getWriterId())) {
        %>
        <form action="<%=request.getContextPath()%>/recipes/edit/<%=recipe.getId()%>">
            <button type="submit" class="btn">수정하기</button>
        </form>
        <form action="<%=request.getContextPath()%>/recipes/delete/<%= recipe.getId() %>" method="post"
              onsubmit="return confirm('정말로 삭제하시겠습니까?');">
            <button type="submit" class="btn btn-danger">삭제하기</button>
        </form>
        <%
            }
        %>
    </div>

    <!-- Comments Section -->
    <div class="comments-section">
        <h3>댓글</h3>
        <!-- 댓글 컨테이너, 동적으로 댓글이 추가될 영역 -->
        <div id="comments-container">
            <!-- 여기에 JavaScript로 동적으로 댓글이 추가될 것입니다. -->
        </div>
        <!-- 댓글 작성 폼 -->
        <div class="comment-form">
            <form>
                <%
                    if (request.getSession().getAttribute("login") != null) {
                %>
                <textarea id="com-content" name="comContent" rows="4" placeholder="댓글을 입력하세요..."></textarea>
                <button type="button" id="send-button">댓글 등록</button>
                <%
                } else {
                %>
                <textarea id="com-content" name="comContent" rows="4" placeholder="댓글을 달려면 로그인을 해주세요" disabled></textarea>
                <button type="button" id="send-button" disabled>댓글 등록</button>
                <%
                    }
                %>
            </form>
        </div>
    </div>


</div>

<script>
    async function getComments() {
        let url = "/comments/list/" + <%=recipe.getId()%>;
        let resp = await fetch(url);
        let jsonData = await resp.json();
        console.log(jsonData);
        // 댓글을 표시할 영역을 선택합니다.
        const commentsContainer = document.getElementById("comments-container");

        // 기존 댓글 목록을 초기화합니다.
        commentsContainer.innerHTML = '';
        renderComments(jsonData, commentsContainer)
    }
    async function renderComments(comments, parentContainer) {
        if (comments && comments.length > 0) {
            comments.forEach(comment => {
                const commentDiv = document.createElement('div');
                commentDiv.className = 'comment';

                const authorParagraph = document.createElement('p');
                authorParagraph.className = 'comment-author';
                authorParagraph.textContent = comment.writer;
                commentDiv.appendChild(authorParagraph);

                const contentParagraph = document.createElement('p');
                contentParagraph.textContent = comment.content;
                commentDiv.appendChild(contentParagraph);

                const timeParagraph = document.createElement('p');
                timeParagraph.className = 'comment-date';
                timeParagraph.textContent = comment.createdAt;
                commentDiv.appendChild(timeParagraph);

                // 답글쓰기 버튼 추가
                const replyButton = document.createElement('button');
                replyButton.className = 'reply-button';
                replyButton.textContent = '답글쓰기';
                replyButton.onclick = function() {
                    addReplyForm(commentDiv, comment.id);
                };
                commentDiv.appendChild(replyButton);

                parentContainer.appendChild(commentDiv);

                // 대댓글이 있는 경우, 재귀적으로 대댓글 렌더링
                if (comment.children && comment.children.length > 0) {
                    const replyContainer = document.createElement('div');
                    replyContainer.className = 'replies';
                    commentDiv.appendChild(replyContainer);

                    renderComments(comment.children, replyContainer);
                }
            });
        }
    }

    // 답글 폼을 추가하는 함수
    function addReplyForm(commentDiv, parentId) {
        // 기존에 답글 폼이 있으면 삭제
        const existingForm = commentDiv.querySelector('.reply-form');
        if (existingForm) {
            existingForm.remove();
        }

        const replyForm = document.createElement('div');
        replyForm.className = 'reply-form';
        replyForm.innerHTML = `
        <textarea rows="2" placeholder="답글을 입력하세요..." class="reply-content"></textarea>
        <button type="button" class="submit-reply-button">답글 등록</button>
    `;

        const submitButton = replyForm.querySelector('.submit-reply-button');
        submitButton.onclick = async function() {
            const content = replyForm.querySelector('.reply-content').value;
            if (content.trim() !== "") {
                await postReply(parentId, content);
                getComments(); // 답글 등록 후 새로고침
            }
        };

        commentDiv.appendChild(replyForm);
    }

    // 답글을 서버에 전송하는 함수
    async function postReply(parentId, content) {
        let writer_id = '<%=request.getSession().getAttribute("login")%>';
        console.log(parentId)
        let url = '/comments/write?recipeId=<%=recipe.getId()%>' + '&writerId=' + writer_id + "&content=" + encodeURIComponent(content) + "&parentId=" + parentId;
        await fetch(url, { method: 'POST' });
    }

    document.getElementById("send-button").onclick = async function () {
        let writer_id = '<%=request.getSession().getAttribute("login")%>';
        let content = document.getElementById("com-content").value;
        document.getElementById("com-content").value = '';

        let url = '/comments/write?writerId=' + writer_id + "&content=" + encodeURIComponent(content) + "&recipeId=" + <%=recipe.getId()%>;
        await fetch(url, { method: 'POST' });
        getComments(); // 댓글 작성 후 새로고침
    }

    // 페이지 로드 시 초기 댓글 목록을 가져옵니다.
    getComments();

</script>
</body>
</html>
