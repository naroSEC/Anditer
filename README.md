# Anditer
<img width="30%" src="https://user-images.githubusercontent.com/89144246/229952059-ad8e15c0-8fc4-4efb-89c5-1fb55bd54e06.png">

* Anditer는 실제 앱에서 어떤 방식으로 루팅, 디버깅, 프리다 등을 탐지하고 차단하는지 배울 수 있으며, 또 그것을 우회하는 방법도 익힐 수 있는 모바일 모의침투 테스팅 도구입니다.

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

# Use
* 모든 항목들에서 탐지 되고자 할 경우 몇몇 설정들은 사용자가 직접 디바이스에서 설정해줘야 합니다.
* Android OS 7.0 이상 버전만 사용 가능합니다.
* 난독화 적용(Encrypt) 버전과 난독화 미적용(NoEncrypt) 두 가지 버전을 제공합니다.
* AnditerRooting.apk는 루팅 탐지 시 사용되는 앱으로, 디바이스에서 루팅 탐지가 되지 않는다면 해당 앱 설치를 해주시면 됩니다.
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/229981965-60891400-80ac-482b-96c4-128861529d45.jpg">
<img width="200" height="400" src="https://user-images.githubusercontent.com/89144246/229982016-2d521fb7-6c71-48eb-b4e5-6b8dced41e5c.jpg">

# Issue
* 1.0.ver
   * 지문 항목은 API level 30 이상에서만 사용 가능합니다. 추후 패치를 통해 아래 버전도 지원 될 예정입니다.
   * 루팅 항목의 [Bypass Check Process]에서 안드로이드 보안 모델 정책에 의해 오류가 발생하는 이슈가 있어 확인 중 입니다.

# Patch
* Release
  * 1.0

* Later Version Patch
  * 덱스 동적 호출
  * 네이티브 탐지 방식
