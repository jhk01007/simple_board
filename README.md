# 게시판 만들기

# ✅ 개요

- 회원가입과 로그인을 하고 글을 CRUD 하는 간단한 구조의 게시판.
- Tomcat 기반의 Servlet, JSP를 활용한 MVC 패턴을 통해 구현한다.
- RDBMS는 MySQL을 사용한다..

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

<img width="640" alt="1" src="https://github.com/user-attachments/assets/3a238ae7-80f7-4a8b-b053-0d5942a69c68">


## 2️⃣ 로그인

<img width="640" alt="2" src="https://github.com/user-attachments/assets/8777c1cd-eaac-4811-b3c8-c4e70aca8e88">


## 3️⃣ 메인화면

### 로그인 전

<img width="640" alt="3" src="https://github.com/user-attachments/assets/cb95ef2f-a617-4d19-90a9-3dc3bba825e9">


### 로그인 후

<img width="640" alt="4" src="https://github.com/user-attachments/assets/82b4fc70-7568-46b2-b0a7-41fce96e285a">


## 4️⃣ 글 목록

<img width="640" alt="5" src="https://github.com/user-attachments/assets/65606eda-5ccd-4b07-958e-9893288f9866">


## 5️⃣ 글 세부정보

### 내 글이 아닌 경우

<img width="640" alt="6" src="https://github.com/user-attachments/assets/cb5a18fe-50a5-4c86-83d0-359c363a3b36">


### 내 글인 경우

<img width="640" alt="1" src="https://github.com/user-attachments/assets/e04a214f-faa4-49f3-b6b0-e3a57e3f0592">


## 6️⃣ 수정

<img width="640" alt="2" src="https://github.com/user-attachments/assets/0923a4b5-ddfa-4868-bf94-97b694e36c3b">


## 7️⃣ 삭제

<img width="640" alt="3" src="https://github.com/user-attachments/assets/8fb3c2e3-17a9-4063-8cfa-c90aaf2af768">

<img width="422" alt="4" src="https://github.com/user-attachments/assets/5c11b410-5132-46d0-b38f-462965d9b0f8">

<img width="640" alt="5" src="https://github.com/user-attachments/assets/0c0ef327-0ec3-4ca5-bc30-c269c945662b">
