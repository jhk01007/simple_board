<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 작성하기</title>
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
        .form-group input[type="file"] {
            display: none; /* Hide the default file input */
        }
        .custom-file-upload {
            display: inline-block;
            padding: 10px 20px;
            cursor: pointer;
            border-radius: 5px;
            background-color: #5cb85c;
            color: white;
            font-size: 16px;
            text-align: center;
            transition: background-color 0.3s ease;
        }
        .custom-file-upload span {
            color: white; /* Change this color to the desired text color */
        }
        .custom-file-upload:hover {
            background-color: #4cae4c;
        }
        .file-name {
            margin-top: 10px;
            font-size: 14px;
            color: #333;
            display: block; /* Ensure it displays as a block element */
            white-space: pre-wrap; /* Preserve whitespace for multiple files */
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
        function confirmSubmission() {
            const input = document.getElementById('images');
            if (input.files.length === 0) {
                input.remove(); // 파일 입력 요소를 제거하여 빈 파일 필드가 제출되지 않도록 함
            }
            return confirm('작성하시겠습니까?');
        }

        function updateFileName() {
            const input = document.getElementById('images');
            const fileNameDisplay = document.getElementById('fileNameDisplay');
            let fileNames = '';

            // Iterate through all selected files and append their names
            for (let i = 0; i < input.files.length; i++) {
                fileNames += input.files[i].name;
                if (i < input.files.length - 1) {
                    fileNames += ', '; // Add comma between file names
                }
            }

            // Set the text content to display the file names
            fileNameDisplay.textContent = fileNames || '선택된 파일 없음'; // Display file names or a default message
        }
    </script>
</head>
<body>
<div class="container">
    <h2>글 작성하기</h2>
    <form action="<%=request.getContextPath()%>/recipes/write" method="post" enctype="multipart/form-data" onsubmit="return confirmSubmission();">
        <div class="form-group">
            <label for="foodName">음식 이름</label>
            <input type="text" id="foodName" name="foodName" required>
        </div>
        <div class="form-group">
            <label for="process">레시피</label>
            <textarea id="process" name="process" required></textarea>
        </div>
        <div class="form-group">
            <label for="images">파일 추가</label>
            <label class="custom-file-upload">
                <input type="file" id="images" name="images" multiple onchange="updateFileName()">
                <span>파일 선택</span>
            </label>
            <span id="fileNameDisplay" class="file-name">선택된 파일 없음</span> <!-- Display selected file names here -->
        </div>
        <div class="actions">
            <button type="submit" class="btn">작성</button>
        </div>
    </form>
</div>
</body>
</html>
