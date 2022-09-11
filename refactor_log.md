# Android 리팩토링

## 📌 프로젝트 구조
(리팩토링과 관련 없는 View는 차트에서 제외함)
<img width="897" alt="스크린샷 2022-09-07 오전 2 52 37" src="https://user-images.githubusercontent.com/37799862/188825075-5ab6b6d7-c7ac-47e2-93c1-dda057dd352d.png">

- 기존 방식
  - MainActivity가 싱글톤으로 인스턴스를 소유하고 있고, 그 인스턴스를 Fragment에서 접근하여 데이터를 사용

- 문제 인식
  - Data가 변경되었을 때, Callback으로 그 데이터를 사용하는 모든 View를 일일히 수정해주어야 했다.
  - 구현 파트가 늘어남에 따라 Callback 코드도 점점 늘어났고, 스파게티 코드가 되었다.
- 해결  
  - `MainViewModel`을 구현하여, HomeFragment, MyPageFragment, QrScanFragment가 공유하도록 하였다. 양방향으로 데이터바인딩하고있기 때문에, 어느 한 Fragment에서 MainViewModel의 데이터를 변경하면 모든 프래그먼트의 값이 변경된다.
  - 그 외 다른 Activity에서 observe되어야하는 데이터가 존재할 경우, 그 액티비티의 ViewModel을 구현해주었다. 액티비티 간에는 뷰모델을 공유할 수 없기 때문이다.

## 📌 주요 리팩토링 내용

- Fragment간 공유데이터 수정 시 UI 변경 보완(Activity와 Fragment들 간 데이터 효율적으로 공유)
    - 기존) Callback으로 구현
    - 리팩토링) `MainViewModel`을 통해 공유
- `MVVM 구조` 활용 → View와 데이터를 뷰모델을 사용하여 분리함 (View를 수정하는 부분은 Contract interface에 구현하여 view에 Implements)
- ViewModel 적극 활용 →  visibility `양방향 바인딩` 처리
- findViewById → `DataBinding` 이용
- Deprecated된 StartActivityForResult → `ActivityResultLauncher` 이용
- Retrofit을 더 Clean하게 사용
    - retrofit 변수 private화, getRetrofit()함수 이용
    - 반복되던 ErrorConverter 코드 메소드화하여 중복 제거

<br>

## 📌 리팩토링 일지
| <div sytle="width:150px">Date</div> | <div sytle="width:150px">Which Part</div> | <div sytle="width:600px">Description</div> |
| --- | --- | --- |
| 22.03.16 | init | DataBinding을 위한 setting |
| 22.03.17 | MainViewModel | - MainActivity의 UserData를 ViewModel로 변경 <br> - 각 Fragment에서 필요한 api 및 공유 데이터 MainViewModel에 구현 |
|  | HomeFragment, MyPageFragment | FIndViewById → DataBinding |
| 22.08.19 | MainViewModel | - HomeFragment에 MainViewModel 공유 <br> - HomeFragment의 callback구현→ viewModel data로 변경 <br> - MainViewModel에 LoginState 추가, 각 Fragment에서 비로그인/로그인 상태에 따라 다르게 표시되어야 하는 부분들의 visibility 양방향바인딩 처리 |
| 22.08.25 | MVVM | MainActivityView → MainContract <br> (단순 인터페이스명 변경) |
| 22.08.26 | 중복코드제거 | - RetrofitResponse를 부모클래스로 생성하여 상속하는 방식으로 Response POJO클래스들의 중복 코드 제거<br> - ErrorConverter를 ApplicationClass에 메소드화하여 중복 코드 제거<br> - 화면별로 분리되어있던 패키지 구조를 일부 통합하여, api Response, Request model class들을 하나의 패키지에 모음(같은 model을 사용하는 api끼리 공유하는 경우 중복코드 제거하며 코드 관리 수월해짐) |
|  | Clean Code | - ApplicationClass의 retrofit변수 private화, getRetrofit()으로 접근 |
|  | MVVM | - home, main, mypage의 패키지 구조 정리 |
|  | fix | HomeFragment HomeService 생성 관련 버그 수정 |
| 22.08.27 | MainViewModel | - ParkingMemoActivity에서 memo 변경시 MainViewModel값 변경 |
|  | Clean Code | - 주석처리 된 코드 삭제 |
| 22.08.29 | fix | - HomeFragment refresh Error 해결 (휴먼이슈 - key값 오타)<br> - HomeFragment ↔ ParkingMemoActivity 데이터 적용 바로 안되는 문제 해결 |
|  | MainViewModel | - QrManagementActivity ↔ HomeFragment 데이터 공유 <br> (QrManagementActivity getSafeyInfo api 제거 → HomeFragment에서 intent이용해 mainViewModel list 전달)<br> - PakringMemoActivity ↔ HomeFragment 데이터 공유<br> - HomeFragement ViewPager viewModel observe → ViewPager에 정상적으로 update되도록 getItemPosition 재정의 |
|  | Clean Code | - HomeFragment의 mHomeService 멤버변수 private화 |
| 22.08.30 | fix | - EditUserInfoActivity: api success 이전에 activity finish()되는 문제 해결<br> - QrManagementActivity recyclerView scroll 안되는 문제 해결 |
|  | MVVM | - mypageFragment, EditUserInfoActivity, DeleteAccountActivity package 구조 변경<br> - ActivityView all removed<br> - EditUserInfoActivity ↔  MyPageFragment, HomeFragment userInfo Observe |
|  | QrManagementViewModel | - QrManagementActivity Viewmodel 생성 및 RecyclerView 연결 |
| 22.08.31 | fix | - QrManagementActivity → recyclerView delete 비정상작동 Error 해결<br> - QrManagementActivity Visibility MutableLiveData에 따라 변경되지 않는 문제 해결(setLifeCycleOwner 누락)<br> - QrManagementActivity에서 모든 Item삭제 후 HomeFragment 복귀 시 ViewPager없어지는 문제 해결 → getSerializedExtra에서 비어있을 경우 guideItem 추가함 |
|  | MVVM | - QrManagement SafetyInfo 개수에 따른 visibility 양방향 바인딩<br> - EditUSerInfoActivity 양방향 바인딩 |
|  | UI/UX 개선 | - HomeFragment QR등록버튼 비로그인상태 시 비활성화<br> - QrManagement RegisterDialog EditText 줄바꿈 금지<br> - ParkingMemoActivity 내용 비어있을 때 저장알림Dialog 나오는 현상 해결 |
|  | Clean code | - Lambda 적극 활용 |
| 22.09.01 | fix | - repSecondPhone api error 해결 |
|  | MVVM<br>(SecondPhoneViewModel) | SecondPhoneActivity ViewModel 생성 |
| 22.09.02 | UI/UX 개선 | - SecondPhoneActivity delete 개선<br> - SecondPhoneRegisteAcitivity crossbtn 삭제<br> - SecondPhoneActivity EditState에서 setRep 제한 |
| 22.09.05 | MVVM | - SecondPhoneRegisterActivity binding|
|  | fix | - SecondPhoneActivity PhoneNum 유효성 검사 추가, Spinner 삭제<br> - numva.co.kr 도메인 만료 → ip주소로 수정 |
| 22.09.06 | fix | - qrscan으로 register시 발생 오류 해결 |
