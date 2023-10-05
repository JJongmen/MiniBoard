### 기능 요구사항
- 회원가입, 로그인, 게시글 작성, 게시글 목록 조회, 게시글 상세조회, 게시글 수정, 게시글 삭제 API 를 구현합니다.
- Content-type 응답 형태는 application/json(JSON) 형식을 사용합니다.

### 요구사항에 따른 상세 기술 구현 사항
- 회원가입 API
  - 기능: 사용자를 식별할 수 있는 값을 생성하여 저장합니다.
  - 요청: 이름, 이메일, 비밀번호
  - 응답: 사용자 식별값
- 로그인 API
  - 세션 로그인 방식
    - 기능: 세션을 통한 로그인 기능을 구현한다.
    - 요청: 이메일, 비밀번호
    - 응답: 이름, 이메일, 세션 값
  - 토큰 로그인 방식
    - 기능: 토큰을 통한 로그인 기능을 구현한다.
    - 요청: 이메일, 비밀번호
    - 응답: 이름, 이메일, 토큰 값
- 로그아웃 API
  - 기능: 로그인 시 발급된 사용자 식별값을 삭제합니다.
  - 요청: X
  - 응답: X
- 게시글 작성 API
  - 기능: 게시글을 작성합니다.
  - 요청: 사용자 식별값, 제목, 내용
  - 응답: X
- 게시글 목록 조회 API
  - 기능: 게시글 목록을 조회합니다.
  - 요청: X
  - 응답: 게시글 목록
- 게시글 상세조회 API
  - 기능: 게시글 상세정보를 조회합니다.
  - 요청: 게시글 식별값
  - 응답: 작성자 닉네임, 제목, 내용
- 게시글 수정 API
  - 기능: 본인의 게시글을 수정합니다.
  - 요청: 게시글 식별값, 제목, 내용
  - 응답: X
- 게시글 삭제 API
  - 기능: 게시글을 삭제합니다.
  - 요청: 유저 식별값, 게시글 식별값
  - 응답: X

### 기술 스택
- 개발언어: Java 17
- Framework: Spring Boot 3.1.4
- DB: H2