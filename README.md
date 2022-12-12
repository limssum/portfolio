<div align=center>
	
 ![header](https://capsule-render.vercel.app/api?type=rounded&color=auto&height=200&section=header&text=PROJECT01&tSize=90&animation=fadeIn&fontAlignY=38&desc=미니프로젝트&descAlign=62)	
</div>
<div align=center>
	<h3>피카츄 키우기 게임</h3>
	<p>MVC 패턴과 JDBC 기술을 사용하여 회원 정보를 입력 받아 DB에 저장된 정보와 비교한 후, 회원별로 피카츄를 육성할 수 있는 게임 제작하였습니다.
Java와 DataBase 등을 활용하였습니다.</p>
</div>

# 프로젝트 소개

---

- 개발 기간 : 2022년 7월 12일 ~ 2022년 7월 14일
- 개발 인원 : 5명

##### [ 프로젝트 목표 ]
 1. MVC 패턴을 활용하여 코드 간결화
 2. JDBC를 활용하여 DB와 연결 및 Java에서 SQL문 사용
 3. 회원 정보를 입력 받아 DB에 저장된 정보와 비교한 후, 회원별로 피카츄를 육성할 수 있는 게임 제작
 
 <br>
 
##### [ 제작할 기능 ]
- 회원가입
-  로그인
- 피카츄 등록(별명을 입력해서 등록한다. / 이 계정에 피카츄가 없을 때만 진행)
- 피카츄 키우기
  1) 피카츄 바람에 관련된 문구를 랜덤하게 출력한다.
  2) 사용자가 문구를 보고 피카츄에게 시킬 활동을 선택할 수 있게 한다.
  3) 피카츄의 바람과 선택한 활동을 비교한 후 맞는 선택지라면 경험치 증가, 아니라면 변동 없음
  4) 선택한 활동에 따라 에너지가 증가하거나 감소한다.
  5) 경험치가 100일시 레벨 업 (경험치는 게임 종료 시 초기화)
  6) 에너지가 0 이하라면 사망 (피카츄 데이터 삭제)
- 게임 종료
 
 <br>
 
##### [ 산출문서 ]
  + 유스케이스 다이어그램
  + 요구사항 정의서
  + 테이블 명세서
 
 <br>
 
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

<br>

- 게임 진행
  - 알맞은 선택지를 선택했을 때 / 선택하지 않았을 때   
![image](https://user-images.githubusercontent.com/89984853/198171832-a33e075a-3ed9-4605-b6df-e2626adf969c.png)

<br>

- 레벨업   
![image](https://user-images.githubusercontent.com/89984853/198172054-8dc050d7-de01-4ad1-b746-c1fbd7a2952f.png)

<br>

- 에너지가 소진되어 피카츄 데이터 삭제   
![image](https://user-images.githubusercontent.com/89984853/198172174-96172ad8-2e8e-40af-bca3-0eed87a22a24.png)


## 6. 팀원 역할 및 소개

|  이름  |  역할  |                 Github 주소                 |
| :----: | :----: | :-----------------------------------------: |
| 김도연[팀장] |  자료 흐름도, 기능 분석 및 명세서, 로그인, 회원가입, 게임 로직 및 구현, 최종 정리 |     [Github](https://github.com/kdn00)     |
| 김기범 |  로그인, 자료 흐름도, 게임 구현 |   [Github](https://github.com/colaage23)    |
| 황윤정 |  로그인, 게임 구현  |   [Github](https://github.com/jjenniyun)   |
| 임수민 |  회원가입, 게임 구현  |    [Github](https://github.com/wjdrmstnals)    |
| 박선우 |  회원가입  | - [Github](https://github.com/Jjomyi) |

## 7. 프로젝트를 진행하며 느낀 점과 학습한 것

임수민

