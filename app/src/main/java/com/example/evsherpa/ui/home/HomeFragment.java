package com.example.evsherpa.ui.home;


import android.animation.Animator;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.evsherpa.MainActivity;
import com.example.evsherpa.OnBackPressedListener;
import com.example.evsherpa.R;
import com.example.evsherpa.data.StationInfoParser;
import com.example.evsherpa.data.model.StationInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements OnMapReadyCallback {


    private final float DEFAULT_ZOOM = 18;

    private final String KEY_LOCATION = "current_location";
    private final String KEY_CAMERA_POSITION = "camera_position";

    private GoogleMap mMap;
    private Location mCurLocation;
    private Location mDeviceLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private TextView zoomLevelTv;
    private TextView latlngTv;

    private final HashMap<String, StationInfo> stationInfoHashMap = new HashMap<String, StationInfo>();

    private float zoom;

    private final ArrayList<Marker>[][] markersBySector = new ArrayList[46][92];
    private int curSectorX, curSectorY;
    private final float SECTOR_WIDTH = 0.07801f;
    private final float SECTOR_HEIGHT = 0.11707f;
    private final float FAR_SOUTH = 33.0643f;
    private final float FAR_WEST = 124.3636f;

    private double lat, lng;

    private final int MARKER_WIDTH = 100;
    private final int MARKER_HEIGHT = 100;
    private final float MARKER_LABEL_SIZE = 50;

    private final int MARKER_BLUE = 0;
    private final int MARKER_GREEN = 1;
    private final int MARKER_YELLOW = 2;
    private final int MARKER_RED = 3;

    private final int[] totalCountByRegion = new int[10];

    private StationInfoParser parser;

    private Animator.AnimatorListener showBrieflyListener;
    private Animator.AnimatorListener showAllListener;
    private Animator.AnimatorListener hideBrieflyListener;
    private Animator.AnimatorListener hideAllListener;

    private LinearLayout infoPage;
    private TextView stationNameTv;
    private TextView busiNameTv;
    private TextView useTimeTv;
    private TextView busiCallTv;
    private TextView addressTv;

    private int SCREEN_WIDTH, SCREEN_HEIGHT;
    private final float MIN_MARKER_VISIBLE_ZOOM = 12.8f;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 스크린 사이즈 캐싱
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;

        // 현재 줌 레벨 표시 텍스트
        zoomLevelTv = root.findViewById(R.id.text_log_zoom);

        // 현재 카메라 위치 표시 텍스트
        latlngTv = root.findViewById(R.id.text_log_latlng);

        // 현재 GPS 위치
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        ImageButton moveToCurLocationBtn = root.findViewById(R.id.btn_curLocation);
        moveToCurLocationBtn.setOnClickListener(view -> {
            getCurrentLocation(task -> {
                if (task.isSuccessful()) {
                    if (mDeviceLocation != null) {
                        checkAroundMarkers(false);
                        setCameraPosition(mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude(), DEFAULT_ZOOM);
                    } else {
                        Log.d("ev-sherpa", "location null");
                    }
                } else {
                    Log.d("ev-sherpa", "get current location1 failed");
                }
            });
        });
        moveToCurLocationBtn.performClick();

        // 맵 정보 임시 저장
        if (savedInstanceState != null) {
            mCurLocation = savedInstanceState.getParcelable((KEY_LOCATION));
            mMap.moveCamera(savedInstanceState.getParcelable((KEY_CAMERA_POSITION)));
        }

        // 충전소 정보 페이지
        infoPage = root.findViewById(R.id.page_stationInfo);
        infoPage.setOnClickListener(view -> {
            infoPage.animate().translationY(0).setListener(showAllListener);
        });
        infoPage.setY(SCREEN_HEIGHT);
        //infoPage.animate().y(SCREEN_HEIGHT);

        ImageButton closeInfoPageBtn = root.findViewById(R.id.btn_close_page_stationInfo);
        closeInfoPageBtn.setOnClickListener(view -> {
            infoPage.animate().translationY(SCREEN_HEIGHT).setListener(hideAllListener);
        });

        stationNameTv = root.findViewById(R.id.text_stationName);
        busiNameTv = root.findViewById(R.id.text_busiName);
        useTimeTv = root.findViewById(R.id.text_useTime);
        busiCallTv = root.findViewById(R.id.text_busiCall);
        addressTv = root.findViewById(R.id.text_address);

        // 페이지 애니메이션 리스너
        showBrieflyListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                infoPage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        showAllListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        hideBrieflyListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                infoPage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };
        hideAllListener = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                infoPage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };

        // StationInfoParser 생성

        // markersBySector 할당
        for (int y = 0; y < markersBySector.length; y++) {
            for (int x = 0; x < markersBySector[0].length; x++) {
                markersBySector[y][x] = new ArrayList<>();
            }
        }

        return root;

//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ParseStationInfoData("");

        // 마커 클릭 시 해당 위치의 충전소 정보 표시
        mMap.setOnMarkerClickListener(marker -> {
            // 해당 마커를 최상단으로 표시
            marker.setZIndex(Float.MAX_VALUE);

            // stationId를 활용해 충전소에 대한 정보를 얻어와 UI에 충전소 정보 표시
            String stationId = marker.getTag().toString();
            StationInfo info = stationInfoHashMap.get(stationId);

            // ...

            Log.i("ev-sherpa", info.getBusiNm());
            Log.i("ev-sherpa", info.getUseTime());
            Log.i("ev-sherpa", info.getBusiCall());
            Log.i("ev-sherpa", info.getAddr());

            stationNameTv.setText(info.getStatNm());
            busiNameTv.setText("운경기관  " + info.getBusiNm());
            useTimeTv.setText("운영시간  " + info.getUseTime());
            busiCallTv.setText("전화번호  " + info.getBusiCall());
            addressTv.setText("주소  " + info.getAddr());

            // 페이지 애니메이션 실행
            infoPage.animate().translationY(SCREEN_HEIGHT * 0.6f).setListener(showBrieflyListener);

            return true;
        });

        //
        mMap.setOnMapClickListener(latLng -> {
            infoPage.animate().translationY(SCREEN_HEIGHT).setListener(hideBrieflyListener);
        });

        mMap.setOnCameraMoveListener(() -> {

            // 현재 카메라 위치에 따라 현재 구역 계산
            lat = mMap.getCameraPosition().target.latitude;
            lng = mMap.getCameraPosition().target.longitude;

            curSectorX = (int) ((lng - FAR_WEST) / SECTOR_WIDTH);
            curSectorY = (int) ((lat - FAR_SOUTH) / SECTOR_HEIGHT);

            checkZoom();
            checkAroundMarkers(true);

            // 현재 줌 레벨 표시
            zoomLevelTv.setText("Zoom : " + mMap.getCameraPosition().zoom);

            // 현재 카메라 위치 표시
            latlngTv.setText("LatLng : " + lat + ", " + lng);
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mCurLocation);
        }

        super.onSaveInstanceState(outState);
    }



    private void getCurrentLocation(OnCompleteListener onCompleteListener) {
        if (ContextCompat.checkSelfPermission(getContext().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mDeviceLocation = task.getResult();
                } else {
                    Log.d("ev-sherpa", "get current location failed");
                }
            });
            locationResult.addOnCompleteListener(onCompleteListener);
        }
    }

    private Marker createMarker(double lat, double lng, int count) {
        int color = count > 4 ? MARKER_GREEN : count > 2 ? MARKER_YELLOW : MARKER_RED;

        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).
                icon(BitmapDescriptorFactory.fromBitmap(markerWithLabelIcon(Integer.toString(count), color))));
        marker.setVisible(false);

        return marker;
    }

    public void setCameraPosition(double lat, double lng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }

    public void setCameraPosition(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void setCameraBounds(LatLngBounds bounds) {
        mMap.moveCamera((CameraUpdateFactory.newLatLngBounds(bounds, 0)));
    }

    public void setCameraBounds(LatLngBounds bounds, int padding) {
        mMap.moveCamera((CameraUpdateFactory.newLatLngBounds(bounds, padding)));
    }

    public void moveCameraPosition(double lat, double lng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }

    public void moveCameraPosition(LatLng latLng, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void setCameraPositionBounds(LatLng center, float regionRadius) {
        if (mCurLocation.getLatitude() != center.latitude || mCurLocation.getLongitude() != center.longitude) {
            setCameraPosition(center, DEFAULT_ZOOM);
        }

        LatLng leftBottom = new LatLng(center.latitude - regionRadius, center.longitude - regionRadius);
        LatLng rightTop = new LatLng(center.latitude + regionRadius, center.longitude + regionRadius);
        LatLngBounds cameraBounds = new LatLngBounds(leftBottom, rightTop);
        setCameraBounds(cameraBounds);
    }

    private Bitmap markerWithLabelIcon(String str, int markerColor) {
        Bitmap src = null;
        switch (markerColor) {
            case MARKER_BLUE:
                src = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_blue), MARKER_WIDTH, MARKER_HEIGHT, false);
                break;
            case MARKER_GREEN:
                src = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_green), MARKER_WIDTH, MARKER_HEIGHT, false);
                break;
            case MARKER_YELLOW:
                src = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_yellow), MARKER_WIDTH, MARKER_HEIGHT, false);
                break;
            case MARKER_RED:
                src = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_red), MARKER_WIDTH, MARKER_HEIGHT, false);
                break;
        }

        if (src != null) {
            Bitmap markerIcon = Bitmap.createBitmap(MARKER_WIDTH, MARKER_HEIGHT, src.getConfig());

            Canvas canvas = new Canvas(markerIcon);
            canvas.drawBitmap(src, 0, 0, null);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(MARKER_LABEL_SIZE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            canvas.drawText(str, canvas.getWidth() / 2, canvas.getHeight() / 2, paint);

            return markerIcon;
        } else {
            Log.d("ev-sherpa", "fail to make marker");
            return null;
        }
    }

    private void ParseStationInfoData(String jsonStr) {
        parser = new StationInfoParser(getContext());
        StationInfo[] infos = parser.Parse(parser.getJsonString());

        //StationInfo[] infos = new StationInfoParser(getContext()).Parse(jsonStr);

        for (int i = 0; i < infos.length; i++) {

            switch (infos[i].getZcode()) {
                case 11:
                    totalCountByRegion[0]++;
                    break;
                case 41:
                    totalCountByRegion[1]++;
                    break;
                case 42:
                    totalCountByRegion[2]++;
                    break;
                case 43:
                    totalCountByRegion[3]++;
                    break;
                case 44:
                    totalCountByRegion[4]++;
                    break;
                case 45:
                    totalCountByRegion[5]++;
                    break;
                case 46:
                    totalCountByRegion[6]++;
                    break;
                case 47:
                    totalCountByRegion[7]++;
                    break;
                case 48:
                    totalCountByRegion[8]++;
                    break;
                case 50:
                    totalCountByRegion[9]++;
                    break;
            }
            stationInfoHashMap.put(infos[i].getStatId(), infos[i]);

            // 마커 생성
            Marker marker = createMarker(infos[i].getLat(), infos[i].getLng(), 3);
            marker.setTag(infos[i].getStatId());

            // 위도/경도 위치를 계산해서 알맞은 Sector의 ArrayList에 마커 추가
            // 위도/경도 값이 0부터 시작하는게 아니라서 가장 왼쪽/하단 값을 알아야 한다.
            // 가장 왼쪽/하단 값이 20/10 이라고 한다면 이 값을 빼고 나눠야 0번부터 저장하는게 유효해진다

            int sectorX = (int) ((infos[i].getLng() - FAR_WEST) / SECTOR_WIDTH);
            int sectorY = (int) ((infos[i].getLat() - FAR_SOUTH) / SECTOR_HEIGHT);
            markersBySector[sectorY][sectorX].add(marker);
        }
    }

    private void checkAroundMarkers(boolean visible) {
        // 카메라 위치에 따라 마커 활성화/비활성화
        // 현재 위치한 구역의 주변 구역들을 검사해서 해당 마커가 화면 범위 내에 있는지 밖에 있는지 계산하여 마커 활성화/비활성화 결정
        if (zoom > MIN_MARKER_VISIBLE_ZOOM) {
            if (0 <= curSectorX && curSectorX < markersBySector.length && 0 <= curSectorY && curSectorY < markersBySector[0].length) {
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        int sectorX = curSectorX + x;
                        int sectorY = curSectorY + y;

                        if (0 <= sectorX && sectorX < markersBySector.length && 0 <= sectorY && sectorY < markersBySector[0].length) {
                            ArrayList<Marker> markers = markersBySector[sectorY][sectorX];
                            for (int i = 0; i < markers.size(); i++) {
                                LatLng latlng = markers.get(i).getPosition();
                                if (lat - SECTOR_WIDTH / 2 < latlng.latitude && latlng.latitude < lat + SECTOR_WIDTH / 2
                                        && lng - SECTOR_HEIGHT / 2 < latlng.longitude && latlng.longitude < lng + SECTOR_HEIGHT / 2)
                                    markers.get(i).setVisible(visible);
                                else {
                                    markers.get(i).setVisible(false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkZoom() {
        // 카메라 줌 정도에 따라 마커 활성화/비활성화
        if (0 <= curSectorX && curSectorX < markersBySector.length && 0 <= curSectorY && curSectorY < markersBySector[0].length) {
            float newZoom = mMap.getCameraPosition().zoom;
            if (zoom < MIN_MARKER_VISIBLE_ZOOM && newZoom > MIN_MARKER_VISIBLE_ZOOM) {
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        int sectorX = curSectorX + x;
                        int sectorY = curSectorY + y;

                        if (0 <= sectorX && sectorX < markersBySector.length && 0 <= sectorY && sectorY < markersBySector[0].length) {
                            ArrayList<Marker> markers = markersBySector[sectorY][sectorX];
                            for (int i = 0; i < markers.size(); i++)
                                markers.get(i).setVisible(true);
                        }
                    }
                }
            } else if (zoom > MIN_MARKER_VISIBLE_ZOOM && newZoom < MIN_MARKER_VISIBLE_ZOOM) {
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        int sectorX = curSectorX + x;
                        int sectorY = curSectorY + y;

                        if (0 <= sectorX && sectorX < markersBySector.length && 0 <= sectorY && sectorY < markersBySector[0].length) {
                            ArrayList<Marker> markers = markersBySector[sectorY][sectorX];
                            for (int i = 0; i < markers.size(); i++)
                                markers.get(i).setVisible(false);
                        }
                    }
                }
            }

            zoom = newZoom;
        }
    }
}