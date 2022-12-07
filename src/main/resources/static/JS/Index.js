(()=>{
  function geoFindMe() {

    const status = document.querySelector('#status');
    const mapLink = document.querySelector('#map-link');

    mapLink.href = '';  //map으로 연결된 src
    mapLink.textContent = '';  //보여줄 문자

    function success(position) {  //위치 조회 성공 시
      const latitude  = position.coords.latitude;
      const longitude = position.coords.longitude;

      status.textContent = '';
      mapLink.href = `https://www.openstreetmap.org/#map=18/${latitude}/${longitude}`; //mapLink의 src설정 openstreetmap에서 위도 경도를 사용하여 불러온다.
      mapLink.textContent = `위도: ${latitude} °,  경도: ${longitude} °`; //출력할 문자

      document.querySelector('.myLocation').src=`${mapLink.href}`;
    }

    function error() {  //위치 조회 실패 시
      status.textContent = '위치 조회가 불가능 합니다.';
    }

    if(!navigator.geolocation) { //navigator를 지원하지 않는다면
      status.textContent = 'Geolocation을 지원하지 않는 브라우저입니다.';
    } else {
      status.textContent = '조회 중입니다…';
      navigator.geolocation.getCurrentPosition(success, error);
    }

  }

  document.querySelector('#find-me').addEventListener('click', geoFindMe);
})();
