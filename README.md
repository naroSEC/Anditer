# Anditer
<img width="30%" src="https://user-images.githubusercontent.com/89144246/229952059-ad8e15c0-8fc4-4efb-89c5-1fb55bd54e06.png">

* Anditer는 실제 앱에서 사용되는 보안 코드 탐지(루팅, 프리다, 디버깅 등) 방법을 배울 수 있으며, 또 그것을 우회하는 방법도 익힐 수 있는 모바일 모의침투 테스팅 도구입니다.

# Learning Courses
Anditer를 통해 아래와 같은 다양한 탐지 방법을 학습하고 우회 방법을 익힐 수 있습니다.
* 루팅(Rooting)
  * Bypass Packages : 패키지 탐지 방식
  * Bypass Binaries : 바이너리 파일 탐지 방식
  * Bypass Command Execution : 명령어 실행 가능 여부 탐지 방식
  * Bypass Build-Tags : Build.prop[keys] 비정상 값 탐지 방식
  * Bypass Writeable : 특정 디렉터리 쓰기 가능 여부 탐지 방식
  * Bypass System Property : Build.prop[secure] 비정상 값 탐지 방식
  * Bypass Check Process : 프로세스 상태 탐지 방식

* 디버깅(Debugging)
  * Bypass TracerPID : TracerPid 비정상 값 탐지 방식
  * Bypass Debuggable : Build.prop[debuggable] 비정상 값 탐지 방식
  * Bypass Debug Tools : 디버깅 도구 탐지 방식
  * Bypass Develop Mode : 개발자 모드 탐지 방식
  * Bypass Debuggging Mode : USB 디버깅 모드 탐지 방식
  * Bypass Connect USB : USB 연결 탐지 방식

* 애뮬레이터(Emulator)
  * Bypass Build Setting : 애뮬레이터 Build 값 탐지 방식
  * Bypass Default Files : 애뮬레이터 전용 바이너리 파일 탐지 방식
  * Bypass Packages : 애뮬레이터 전용 패키지 탐지 방식

* 프리다(Frida)
  * Bypass File & Path : 프리다 디폴트 파일, 디렉터리 탐지 방식
  * Bypass Port : 프리다 리스닝 포트 탐지 방식
  * Bypass Module : 모듈 탐지 방식
  * Bypass Pipe : 파이프 특정 문자열 탐지 방식

* 피닝(Pinning)
  * Bypass Pinning(Root CA) : 디바이스 Root CA 인증서 탐지 방식
  * Bypass Pinning(Allow CA) : 고정 인증서 탐지 방식

* 무결성(Integrity)
  * Bypass App Name : 앱 이름 변조 여부 탐지 방식
  * Bypass Hash Key : 사이니킹 변조 여부 탐지 방식
  * Bypass Installer : 마켓 출처 탐지 방식
  * Bypass CRC : Dex 파일 체크섬 변조 여부 탐지 방식

* 동적 로딩(Dynamic Dex Load)
  * Bypass Dynamic Code : Dex 파일 동적 로딩 탐지 기법(파일 삭제X)
  * Bypass Hide Code : Dex 파일 동적 로딩 탐지 기법(로딩 후 파일 삭제)

* 잠금화면
  * 비밀번호 : 잠금 비밀번호 우회 방법 학습
  * 패턴 : 잠금 패턴 우회 방법 학습
  * 지문 : 생체인증 방식의 잠금 우회 방법 학습

* 네이티브 탐지
  * 추후 예정

# Use
* 모든 항목들에서 탐지 되고자 할 경우 몇몇 설정들은 사용자가 직접 디바이스에서 설정해줘야 합니다.
* Android OS 7.0 이상 버전만 사용 가능합니다. (되도록이면 9.0 버전 이상 사용을 권고 드립니다.)

<p align="center">
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/235369374-439409b1-78bd-4032-ae09-b720824a350d.jpg">
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/235369376-0f3a677a-e6c4-4d4f-9617-db57de6cc220.jpg">
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/235369377-233937c7-fb34-4ab5-aef4-d52e8837be35.jpg">
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/235369380-d30669ff-8fc6-432d-9a08-5b8cdecca65c.jpg">
</p>

# Info
* 난독화 적용(Encryption) 버전과 난독화 미적용(NoEncryption) 두 가지 버전을 제공합니다.
  * 난독화 버전의 경우 Proguard 난독화 모듈이 적용되어 있습니다.
* AnditerRooting.apk는 루팅 탐지 시 사용되는 앱으로, 디바이스에서 루팅 탐지가 되지 않는다면 해당 앱 설치를 해주시면 됩니다.
* 상업적 목적 이용 시 꼭 출처를 남겨주시길 바랍니다.

# Issue
* 1.0.ver
   * 잠금 화면 항목의 지문 및 패턴은 추후 패치를 통해 지원 될 예정입니다.

# Patch
* Release
  * 1.0

* Later Version Patch
  * 네이티브 코드 탐지 추가 예정
