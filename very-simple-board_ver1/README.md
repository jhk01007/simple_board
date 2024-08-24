# 게시판 만들기

# ✅ 개요

- 회원가입과 로그인을 하고 글을 CRUD 하는 간단한 구조의 게시판.
- Tomcat 기반의 Servlet, JSP를 활용한 MVC 패턴을 통해 구현한다.
- RDBMS는 MySQL을 사용한다.

# ✅ 아키텍처

![1](https://github.com/user-attachments/assets/0dddb38a-b0c7-4dca-a514-e50b0e5effae)

# ✅ 환경 설정

- WAS: Tomcat 10.1.28
- build: maven
- RDBMS: MySQL 8.4.0
- RDBMS Connector: MySQL-Connector: 9.0.0
    
    ```xml
    <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>9.0.0</version>
    </dependency> 
    ```
    
    [Maven Repository: com.mysql » mysql-connector-j](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j)<br><br>

  
# ✅ 주요 화면

## 1️⃣ 회원가입

<img width="1505" alt="2" src="https://github.com/user-attachments/assets/13e782ba-e49a-4714-8dc9-1f7e85319237">

## 2️⃣ 로그인

<img width="1499" alt="3" src="https://github.com/user-attachments/assets/84648b4a-1151-415f-812d-99243db8eb97">

## 3️⃣ 메인화면

### 로그인 전

<img width="1503" alt="4" src="https://github.com/user-attachments/assets/a637d7e1-eef2-43a9-b109-53232c01b101">

### 로그인 후

<img width="1505" alt="5" src="https://github.com/user-attachments/assets/d86d5102-1c40-4152-8e8c-a591ee4f0886">

## 4️⃣ 글 목록

<img width="1493" alt="6" src="https://github.com/user-attachments/assets/615d8406-bb97-412f-a4b0-9403efad974d">

## 5️⃣ 글 세부정보

### 내 글이 아닌 경우

<img width="1500" alt="7" src="https://github.com/user-attachments/assets/f9494ca0-fdf2-4748-8806-a23aebf9aae6">

### 내 글인 경우

<img width="1499" alt="8" src="https://github.com/user-attachments/assets/fcae1b53-5993-4252-b8a1-41fbd7369949">

## 6️⃣ 수정

<img width="1500" alt="9" src="https://github.com/user-attachments/assets/7eb0eb3c-51d6-476c-9c1c-856d7e5c2ae0">

## 7️⃣ 삭제

<img width="1505" alt="110" src="https://github.com/user-attachments/assets/4e43af08-c199-4ceb-a921-5d4a04b9f644">

<img width="422" alt="111" src="https://github.com/user-attachments/assets/baf4541e-03c4-40e3-b250-95d88dae1642">

<img width="1503" alt="115" src="https://github.com/user-attachments/assets/988a0b14-0332-4aa6-aa62-b4866f37d4fc">
