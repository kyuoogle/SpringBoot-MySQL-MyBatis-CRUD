# Board Project (Spring Boot + Gradle + MyBatis + Thymeleaf)
 
Spring Boot 기반으로 개발되었으며 MyBatis를 이용한 데이터 접근, Thymeleaf를 이용한 템플릿 렌더링을 중심으로 구성.

---

# 1. 프로젝트 개요 및 구현 기능 정리

## 1.1 주요 기술 스택
- Java 17  
- Spring Boot 3.x  
- Gradle 빌드  
- MyBatis  
- MySQL  
- Thymeleaf (View Template Engine)

## 1.2 구현된 기능

### 1) 글 작성(Create)
- 작성 폼에서 전달된 데이터를 BoardDTO에 담아 서비스 계층으로 전달
- MyBatis의 insert 구문을 통해 board 테이블에 저장
- 저장 후 상세보기 페이지로 redirect

### 2) 글 목록 조회(Read)
- BoardMapper의 selectAll()을 통해 전체 게시물 목록을 조회
- Thymeleaf에서 반복문으로 렌더링
- 조회 시 게시물의 id, 제목, 작성자, 작성일, 조회수를 출력

### 3) 글 상세 조회(Read)
- GET /detail/{id} 요청으로 특정 글의 상세 정보 조회
- BoardMapper.findById() 실행
- 조회 시 바로 조회수 증가 기능(boardHits 증가)을 Service 계층에서 함께 처리

### 4) 글 수정(Update)
- 수정 화면을 GET 방식으로 제공
- 사용자가 변경한 내용을 POST 방식으로 제출
- MyBatis의 update 구문을 통해 DB 내용 수정
- 수정 후 상세 페이지로 redirect

### 5) 글 삭제(Delete)
- GET /delete/{id} 요청으로 글 삭제
- BoardMapper.delete() 실행
- 삭제 후 list 페이지로 redirect

### 6) 구조 요약
- Controller: 요청 처리, Service 호출, View 반환 또는 redirect  
- Service: 비즈니스 로직 수행, Mapper 호출  
- Mapper(MyBatis): SQL 실행 담당  
- Thymeleaf: HTML에 데이터 렌더링  

---

# 2. Maven + JSP + MyBatis 방식과 Gradle + Thymeleaf + MyBatis 방식 비교

프로젝트에서 MyBatis는 동일하게 사용하지만, 빌드 도구(Maven/Gradle)와 View 엔진(JSP/Thymeleaf)의 차이에 따라 구조와 개발 경험이 크게 다르다.

## 2.1 빌드 도구 차이: Maven vs Gradle

| 구분 | Maven | Gradle |
|------|--------|--------|
| 빌드 방식 | XML 기반 설정(pom.xml) | DSL 기반 설정(build.gradle) |
| 설정 난이도 | 구조적이지만 길어짐 | 간결하고 선언적 |
| 빌드 속도 | 상대적으로 느림 | 캐시 기반으로 빠름 |
| 의존성 선언 | 반복적, 양이 많아짐 | 짧고 간단함 |

즉, 동일한 MyBatis 프로젝트라도 Gradle 기반은 설정이 훨씬 간결하고 유지보수가 편하다.

---

## 2.2 View 기술 차이: JSP vs Thymeleaf

### JSP 방식의 특징
- Spring Boot에서 JSP는 기본적으로 비추천
- 특별한 톰캣 설정 필요
- resources/templates가 아니라 webapp/WEB-INF/jsp 구조 사용
- JSTL과 scriptlet이 혼합되어 유지보수성이 떨어질 수 있음
- 기존 Spring MVC + MyBatis + Maven 조합에서 자주 사용됨

### Thymeleaf 방식의 특징
- Spring Boot에서 공식적으로 권장되는 템플릿 엔진
- HTML 문법 그대로 사용 가능
- 서버 렌더링과 정적 HTML 호환성이 뛰어남
- th:text, th:each 등 속성 기반의 템플릿 처리
- JSP처럼 WAS 설정을 별도로 하지 않아도 됨

### 종합 비교

| 항목 | JSP | Thymeleaf |
|------|------|------------|
| 문법 | JSTL 위주의 서버 템플릿 | HTML 친화적 템플릿 |
| 설정 편의성 | Spring Boot에서 번거로움 | Spring Boot 기본 지원 |
| 유지보수 | 표현과 로직 혼합 위험 존재 | 구조적이고 가독성 높음 |
| 정적 파일 미리보기 | 어렵다 | 완전한 HTML로 미리보기 가능 |

프로젝트가 Thymeleaf를 채택한 이유는 Spring Boot 환경과의 호환성, 유지보수성, 템플릿 단순성 때문이다.

---

# 3. MyBatis 기반 프로젝트와 JPA의 차이점 정리

Spring Boot 환경에서의 데이터 접근 기술 중 가장 많이 비교되는 것이 MyBatis와 JPA이다.  
둘은 목적과 철학 자체가 크게 다르다.

## 3.1 MyBatis
- SQL 중심(SLQ Mapper Framework)
- 개발자가 직접 SQL 작성
- 모든 쿼리와 매핑을 명확하게 제어 가능
- 복잡한 SQL이 많은 서비스에서 유리
- 유지보수 시 SQL을 직접 보는 것이 필요

### 장점
- SQL을 명확하게 제어가능  
- DB에 특화된 기능 사용 용이  
- 복잡한 조인, 서브쿼리 등 작성 편리  

### 단점
- SQL 작성량이 많아짐  
- 엔티티 중심 모델링이 아님  

---

## 3.2 JPA (Hibernate 기반 구현체 사용)

JPA는 객체 중심(Object-Oriented) ORM 방식이다.  
SQL 직접 작성 없이 엔티티 중심으로 데이터 접근을 처리한다.

### 특징
- SQL 대신 엔티티를 조작한다
- 영속성 컨텍스트 사용
- 더티 체킹으로 자동 update
- 자동쿼리 생성 기능 제공

### 장점
- SQL 자동 생성으로 생산성 향상  
- 객체 중심 모델링 가능  
- 캐시, 연관관계 매핑 등 풍부한 기능  

### 단점
- 동작 원리를 모르면 디버깅 난이도 상승  
- 복잡한 쿼리는 결국 QueryDSL/JPA Native Query 필요  
- 성능 이슈가 발생할 수 있음  

---

## 3.3 MyBatis vs JPA 비교 정리

| 항목 | MyBatis | JPA |
|------|---------|------|
| 철학 | SQL 중심 | 객체 중심 |
| 쿼리 제어 | 개발자가 직접 작성 | 프레임워크가 생성 |
| 러닝커브 | 상대적으로 낮음 | 영속성 개념 이해가 필요 |
| 생산성 | SQL이 많으면 낮아짐 | CRUD는 매우 빠르게 개발 |
| 복잡한 쿼리 | 직접 제어 가능 | Native Query 또는 QueryDSL 필요 |
| DB 종속성 | 높음 | 낮음(ORM 철학) |

이 프로젝트는 SQL을 명확히 제어할 수 있는 MyBatis 방식을 사용하여 구현하였다.

---

# 4. 결론

본 프로젝트는 Spring Boot, Gradle, MyBatis, Thymeleaf 기반의 게시판 기능을 구현하였다.  
전통적인 Maven + JSP + MyBatis 방식과 비교하여 더 단순하고 유지보수성이 높은 구조이며,  
JPA와 비교했을 때 SQL 위주의 명확한 데이터 제어가 필요한 환경에 적합한 형태로 구성되어 있다.
