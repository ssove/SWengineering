package com.example.hiros.sharetaxi;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //// Add a marker in Sydney and move the camera
        // 기존 튜토리얼 코드 3줄
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // camera 좌표를 경북대 근처로
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(
                new LatLng(35.890330, 128.611317)
        ));

        // 구글지도(지구) 에서의 zoom 레벨은 1~23 까지 가능
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        // moveCamera 는 바로 변경하지만 animateCamera() 는 근거리에선 부드럽게 변경
        googleMap.animateCamera(zoom);

        // marker 표시
        // marker 의 위치, 타이틀, 짧은설명 추가 가능
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(35.892440, 128.609075)).title("경북대학교북문");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.885068, 128.614337)).title("경북대학교정문");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.870733, 128.595477)).title("한일극장앞");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.870836, 128.595884)).title("한일극장건너");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.866162, 128.590628)).title("현대백화점");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.878743, 128.626578)).title("동대구역");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.898940, 128.636493)).title("대구국제공항건너");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.855516, 128.550632)).title("롯데시네마광장코아");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.836613, 128.753342)).title("영남대역");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.842107, 128.680771)).title("삼성라이온즈파크");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.832740, 128.686243)).title("대구스타디움");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.932713, 128.548962)).title("칠곡동아백화점");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.926748, 128.547412)).title("대구과학대학/보건대학");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.942335, 128.563600)).title("롯데시네마칠곡");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        marker = new MarkerOptions();
        marker.position(new LatLng(35.947982, 128.574625)).title("보병50사단건너");
        googleMap.addMarker(marker).showInfoWindow(); // marker 추가, 화면에출력

        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                // Main Activity에 마커에 등록돼있는 title string을 전달
                Intent intent = getIntent();
                intent.putExtra("placeName", marker.getTitle());
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
    }
}
