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

비즈니스 로직

model
1) member - 수신자, 발신자, 열람여부
2) message - 이름, 이메일, 비밀번호

service
1) 회원가입
- 이름, 이메일, 비밀번호를 받아서 회원가입
- 이메일 중복이면 회원가입 예외처리(AlreadyExistException)
2) 로그인
- 이메일, 비밀번호를 받아서 로그인
- 이메일 혹은 비밀번호 불일치 시 예외처리(ForbiddenException)
3) 로그아웃
4) 회원탈퇴
- 비밀번호를 받아서 일치하면 탈퇴 성공
- 불일치 시 예외처리(ForbiddenException)
5)회원정보 수정
- 이름이랑 비밀번호 수정 가능
- 원래 비밀번호를 같이 받아서 비교 후 일치하면 수정 성공
- 불일치 시 예외처리(ForbiddenException)
6) 메시지 작성
- 한 번에 여러 member에게 보낼 수 있음
- 메시지 내용이랑 recipients 리스트로 멤버 이름 받아서 각각 메시지 저장
7) 메시지 수정
- 조회하지 않았을 경우에만 수정 가능
- true이면 읽은 것, false이면 읽지 않은 것
8) 메시지 삭제
- 발신자는 수신자가 조회하지 않았을 경우에만 삭제 가능
- true이면 읽은 것, false이면 읽지 않은 것
- 수신자도 삭제할 수 있음
9) 메시지 보관함 목록 보기
- 1. 전체 보기 : 발신자, 메시지ID, 전송시간, 읽음여부
- 2. 특정 메시지 보기 : 발신자, 문자내용, 전송시간
10) 답장가능해야함
