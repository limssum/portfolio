# Pika-Project
---

## 프로젝트 소개

- 개발 기간 : 2022년 7월 12일 ~ 2022년 7월 14일
- 개발 인원 : 5명

- 프로젝트 목표
  1) MVC 패턴을 활용하여 코드 간결화
  2) JDBC를 활용하여 DB와 연결 및 Java에서 SQL문 사용
  3) 회원 정보를 입력 받아 DB에 저장된 정보와 비교한 후, 회원별로 피카츄를 육성할 수 있는 게임 제작
- 제작할 기능
  1) 회원가입
  2) 로그인
  3) 피카츄 등록(별명을 입력해서 등록한다. / 이 계정에 피카츄가 없을 때만 진행)
  4) 피카츄 키우기
     - 피카츄 바람에 관련된 문구를 랜덤하게 출력한다.
     - 사용자가 문구를 보고 피카츄에게 시킬 활동을 선택할 수 있게 한다.
     - 피카츄의 바람과 선택한 활동을 비교한 후 맞는 선택지라면 경험치 증가, 아니라면 변동 없음
     - 선택한 활동에 따라 에너지가 증가하거나 감소한다.
     - 경험치가 100일시 레벨 업 (경험치는 게임 종료 시 초기화)
     - 에너지가 0 이하라면 사망 (피카츄 데이터 삭제)
  6) 게임 종료

- 산출문서
  1) 유스케이스 다이어그램
  2) 요구사항 정의서
  3) 테이블 명세서

## 1. 사용 기술

1. 언어 : Java, SQL
2. Tool : eclipse, oracle(developer)
3. 디자인 패턴 : MVC 패턴

## 2. ER Diagram
![image](https://user-images.githubusercontent.com/89984853/198168171-b97a8eba-d5d9-425f-9793-fe0d947ded3a.png)

## 3. 흐름도
![피카츄 흐름도](https://user-images.githubusercontent.com/89984853/198162917-6048c184-87c4-4f73-a043-7d42bed83044.png)

## 4. 프로젝트 일정
![image](https://user-images.githubusercontent.com/89984853/198159660-eb619671-2ebc-46ac-8793-08e976351eb4.png)

## 5. 구현 화면
- 메인 화면   
![image](https://user-images.githubusercontent.com/89984853/198171786-f0b34394-9a53-4f71-b46e-511a54afd276.png)

- 게임 진행
  - 알맞은 선택지를 선택했을 때 / 선택하지 않았을 때   
![image](https://user-images.githubusercontent.com/89984853/198171832-a33e075a-3ed9-4605-b6df-e2626adf969c.png)

- 레벨업   
![image](https://user-images.githubusercontent.com/89984853/198172054-8dc050d7-de01-4ad1-b746-c1fbd7a2952f.png)

- 에너지가 소진되어 피카츄 데이터 삭제   
![image](https://user-images.githubusercontent.com/89984853/198172174-96172ad8-2e8e-40af-bca3-0eed87a22a24.png)


## 6. 팀원 역할 및 소개

|  이름  |  역할  |                 Github 주소                 |
| :----: | :----: | :-----------------------------------------: |
| 김도연[팀장] |  자료 흐름도, 기능 분석 및 명세서, 로그인, 회원가입, 게임 로직 및 구현, 최종 정리 |     [Github](https://github.com/kdn00)     |
| 김기범 |  로그인, 자료 흐름도, 게임 구현 |   [Github](https://github.com/colaage23)    |
| 황윤정 |  로그인, 게임 구현  |   [Github](https://github.com/jjenniyun)   |
| 임수민 |  회원가입, 게임 구현  |    [Github](https://github.com/wjdrmstnals)    |
| 박선우 |  회원가입  | - |

## 7. 프로젝트를 진행하며 느낀 점과 학습한 것

|  이름  |  학습한 기술  | 느낀점 |
| :----: | :----: | :------------------: |
| 김도연[팀장] | 팀 프로젝트를 진행할 때의 흐름, MVC패턴과 JDBC 활용, GitHub에 코드를 공유(배포)하는 방법, 협업 및 커뮤니케이션 | 단기 프로젝트를 진행하며 시간 관리와 프로젝트 계획의 중요성을 알 수 있었고 Java에 대한 이해도가 늘어서 만족스럽다. |
| 김기범 |   |       |
| 황윤정 |    |      |
| 임수민 |    |        |
| 박선우 |  -  | - |
