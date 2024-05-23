# mju-likelion-messengerapp-project-
멋사 세션 과제 메신저 앱 만들기

API
1) 회원가입
HTTP method : POST
URI : /auth/join
@RequestBody로 name, email, password 받기

2) 로그인
HTTP method : POST
URI : /auth/login
@RequestBody로 email, password 받기

3) 로그아웃
HTTP method : DELETE
URI : /auth/logout

4) 회원탈퇴
HTTP method : DELETE
URI : /members

5) 회원정보 수정
HTTP method : PATCH
URI : /members
@RequestBody로 password, name, newPassword 받기

6) 해당 멤버의 메시지 목록 전체 조회
HTTP method : GET
URI : /messages

7) 멤버의 특정 메시지 조회
HTTP method : GET
URI : /messages/{id}
@PathVariable로 메시지 id 받기

8) 메시지 작성
HTTP method : POST
URI : /messages
@RequestBody로 content, recipients 받기

5) 메시지 수정
HTTP method : PATCH
URI : /messages/{id}
@RequestBody로 content 받기, @PathVariable로 메시지 id 받기

6) 발신자 측에서 메시지 삭제
HTTP method : DELETE
URI : /messages/{id}
@PathVariable로 메시지 id 받기

7) 수신자 측에서 메시지 삭제
HTTP method : DELETE
URI : /messages/{id}/delete
@PathVariable로 메시지 id 받기

8) 답장
HTTP method : POST
URI : /messages/{id}/comment
@RequestBody로 content 받기, @PathVariable로 메시지 id 받기
