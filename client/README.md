# 배틀의 민족

## 🖥️ 프로젝트 소개

  **배틀의 민족**은 MZ세대가 발표와 의견 표출에 대한 자신감을 키우고, 갈등 상황을 유쾌하게 풀어나가기 위한 **온라인 1대1 화상 토론 플랫폼**입니다.

  이 플랫폼은 사람들이 자유롭게 의견을 교환하고, 토론을 통해 자신의 생각을 발전시키며, 소통 능력을 기를 수 있는 기회를 제공합니다. 또한, 논쟁적인 주제도 즐겁게 다룰 수 있는 환경을 조성하여 보다 유연하게 갈등을 해결하는 데 도움을 주고자 합니다.

## 🕰️ 개발 기간

- 시작일: 2024.07.02
- 종료일: 2024.08.16

## 🧑‍🤝‍🧑 팀 구성

- **양세연(Judy)**: 팀장, 백엔드
    - 실시간 라이브 방송, 투표, 알림
    - [Yangseyeon](http://github.com/Yangseyeon)
- **조수연(Melissa)**: 백엔드
    - 배틀 보드, 밸런스 게임, 채팅, 알림
    - [5ooyeon](http://github.com/5ooyeon)
- **현경찬(Charles)**: 백엔드, 프론트엔드
    - 로그인, 마이페이지 풀스택 담당
    - [gyeongmann](https://github.com/gyeongmann)
- **윤성하(Kevin)**: 인프라, 풀스택 서포터
    - CI/CD, 실시간 라이브 방송
    - [MustSave](https://github.com/MustSave)
- **조윤정(Emily)**: 프론트엔드
    - 디자인, 실시간 투표, 알림, 채팅
    - [Huon0423](https://github.com/Huon0423)
- **김현지(Grace)**: 프론트엔드
    - 디자인, 사이트 전반적 프론트 담당
    - [hyeonzi423](https://github.com/hyeonzi423)

## ⚙️ 개발 환경 및 기술 스택

### 프론트엔드 (FE)

- **JavaScript**, **TypeScript**
- **React**, **React Router**
- **Tailwind CSS, Styled-Component**
- **Zustand**
- **MSW** (Mock Service Worker)

### 백엔드 (BE)

- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**, **Hibernate**
- **MySQL**
- **Redis**

### 인프라 (Infra)

- **AWS EC2**, **RDS**
- **Docker**
- **NGINX**
- **Jenkins**
- **Husky**

## 📌 프로젝트 페이지 설명

### 1. **로그인 및 회원가입 페이지**

- 사용자 로그인 및 회원가입 기능 제공
- 이메일 형식 및 닉네임 중복 여부에 대한 유효성 검사
- 로그인 시 JWT를 사용한 인증 처리

### 2. **마이 페이지**

- 사용자의 프로필 이미지 및 닉네임 수정 가능
- 원형 승률 그래프로 승률 시각화
    - 승리한 토론 수, 패배한 토론 수, 전체 참여 토론 수를 함께 표시
- 과거 배틀 내역 확인
    - 개최한 라이브 리스트와 각 승패 여부 표시
    - 참여한 투표 리스트와 각 승패 여부 표시
- 관심 카테고리 설정 및 수정

### 3. **메인 페이지**

- 상단에 캐로셀(Carousel)로 현재 진행 중인 라이브 배틀 표시
- 관심 카테고리에 해당하는 라이브를 각 카테고리 별로 캐로셀 표시
- 관심사가 아닌 카테고리 라이브는 하단에 각 카테고리 별로 캐로셀 표시

### 4. **불구경 게시판**

- 상단에 닌텐도 모양의 실시간 라이브 표시
- **실시간, 예정된, 종료된** 탭으로 구성된 배틀 라이브 게시판
    - **실시간 탭**: 현재 진행 중인 라이브 배틀을 확인하고, 실시간으로 토론에 참여 가능
    - **예정된 탭**: 예정된 배틀의 목록이 카드뷰 형태로 표시
        - 카드뷰를 클릭하면 모달로 참여자, 토론 주제, 각 선택지, 세부 내용 확인 가능
        - 배틀이 성사되면 AI가 자동으로 생성한 썸네일 이미지가 배치됨
    - **종료된 탭**: 종료된 배틀의 목록이 카드뷰 형태로 표시
        - 카드뷰를 클릭하면 모달로 참여자, 토론 주제, 각 선택지, 세부 내용 확인 가능
        - 사전 투표 결과와 최종 투표 결과가 게이지로 시각화되어 제공됨

### 5. **라이브 페이지**

- 배틀이 성사된 후 진행되는 실시간 토론 공간
- 실시간 화상 토론 제공 (WebRTC)
- 두 토론자의 얼굴이 화면에 대결 구도로 표시
- 실시간 타이머로 토론 시간 표시
- 채팅창을 통한 시청자들의 실시간 채팅
- 각 토론자의 주장에 실시간 투표 가능
- 투표 현황을 실시간 게이지로 표시
- 라이브 종료 시 승리한 토론자의 주장과 함께 폭죽 애니메이션 표시

### 6. **부채질 게시판**

- 배틀 성사를 위해 최소 5명 이상의 참석 투표 필요
- 사용자가 배틀 참석 버튼을 클릭 시 사전 투표 진행
- 버튼을 눌러 새로운 배틀 신청 가능

### 7. **모닥불 게시판**

- 배틀이 성사되지 못한 경우 또는 라이브를 원치 않을 경우, 찬반 밸런스 투표 제공
- 사용자가 찬반 의견을 선택 후, 바로 투표 결과 확인 가능
- 실시간 투표와 종료된 투표를 탭을 통해 각각 확인 가능
- 버튼을 눌러 새로운 배틀 신청 가능

### 8. **배틀 신청 페이지**

- 밸런스 게임 또는 라이브 개최 중 선택
- 제목, 카테고리, 작성자의 닉네임 입력
- **라이브 개최**에서는 상대방 닉네임에 글자을 입력하면 해당 글자가 포함된 사용자 목록 표시
    - 상대방의 선택지는 상대방이 수락할 때 함께 작성하므로 비활성화 상태
- 최소 인원은 5명이며, 최대 인원은 99명까지 설정 가능
- 라이브 시작 시간은 현재 시각 기준 1시간 이후로만 설정 가능 (최소 1시간 동안 참석 투표를 받아야 하기 때문)

### 9. **알림 페이지**

- 배틀 요청 알림 제공
    - 수락 시, 자신의 선택지를 적어 전송
    - 거절 시, 거절 사유를 적어 전송
- 요청한 배틀에 대한 상대의 수락 또는 거절 알림 제공
- 라이브 시작 5분 전에 URL과 함께 알림 전송
    - URL 클릭 시 라이브 페이지로 바로 이동 가능

## 🎥 시연 화면

### 1. **로그인 및 회원가입 페이지**

![image.png](@/assets/images/readmeImages/image(1).png)
![image.png](@/assets/images/readmeImages/image(2).png)

### 2. **마이 페이지**

![image.png](@/assets/images/readmeImages/image(3).png)
![image.png](@/assets/images/readmeImages/image(4).png)
![image.png](@/assets/images/readmeImages/image(5).png)
![image.png](@/assets/images/readmeImages/image(6).png)

### 3. **메인 페이지**

![image.png](@/assets/images/readmeImages/image(7).png)
![image.png](@/assets/images/readmeImages/image(8).png)

### 4. **불구경 게시판**

![image.png](@/assets/images/readmeImages/image(9).png)
![image.png](@/assets/images/readmeImages/image(10).png)
![image.png](@/assets/images/readmeImages/image(11).png)
![image.png](@/assets/images/readmeImages/image(12).png)
![image.png](@/assets/images/readmeImages/image(13).png)

### 5. **라이브 페이지**

![image.png](@/assets/images/readmeImages/image(14).png)

### 6. **부채질 게시판**

![image.png](@/assets/images/readmeImages/image(15).png)
![image.png](@/assets/images/readmeImages/image(16).png)

### 7. **모닥불 게시판**

![image.png](@/assets/images/readmeImages/image(17).png)
![image.png](@/assets/images/readmeImages/image(18).png)

### 8. **배틀 신청 페이지**

![image.png](@/assets/images/readmeImages/image(19).png)
![image.png](@/assets/images/readmeImages/image(20).png)

### 9. **알림 페이지**

![image.png](@/assets/images/readmeImages/image(21).png)
![image.png](@/assets/images/readmeImages/image(22).png)
![image.png](@/assets/images/readmeImages/image(23).png)


## 🔗 배포 링크

https://i11a706.p.ssafy.io/
