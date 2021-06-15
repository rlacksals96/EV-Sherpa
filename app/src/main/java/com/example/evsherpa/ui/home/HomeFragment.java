package com.example.evsherpa.ui.home;

import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.evsherpa.R;
import com.example.evsherpa.data.ChargerStatusInfoParser;
import com.example.evsherpa.data.FilteredStationInfoParser;
import com.example.evsherpa.data.StationInfoParser;
import com.example.evsherpa.data.model.ChargerInfo;
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
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private int SCREEN_WIDTH, SCREEN_HEIGHT;
    private final float MIN_MARKER_VISIBLE_ZOOM = 12.8f;
    private final float DEFAULT_ZOOM = 18;

    private final String KEY_LOCATION = "current_location";
    private final String KEY_CAMERA_POSITION = "camera_position";

    private GoogleMap mMap;
    private Location mCurLocation;
    private Location mDeviceLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ImageButton moveToCurLocationBtn;

    //private TextView zoomLevelTv;
    //private TextView latlngTv;

    private double lat, lng;
    private float zoom;

    private StationInfoParser stationInfoParser;
    private ChargerStatusInfoParser chargerStatusInfoParser;
    private FilteredStationInfoParser filteredStationInfoParser;

    private HashMap<String, StationInfo> stationInfoHashMap = new HashMap<String, StationInfo>();

    private int curSectorX, curSectorY;
    private final float SECTOR_WIDTH = 0.07801f;
    private final float SECTOR_HEIGHT = 0.11707f;
    private final float FAR_SOUTH = 33.0643f;
    private final float FAR_WEST = 124.3636f;
    private final ArrayList<Marker>[][] markersBySector = new ArrayList[46][92];

    private final int MARKER_WIDTH = 100;
    private final int MARKER_HEIGHT = 100;
    private final float MARKER_LABEL_SIZE = 50;
    private final int MARKER_BLUE = 0;
    private final int MARKER_GREEN = 1;
    private final int MARKER_YELLOW = 2;
    private final int MARKER_RED = 3;

    private final int[] totalCountByRegion = new int[10];

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
    private LinearLayout[] chargerInfoLayouts;
    private TextView[] chargerStatTvs;
    private ImageView[][] chargerTypeIvs;

    private boolean showFiltered;
    private ArrayList<Marker> filteredMarkers = new ArrayList<Marker>();
    private ImageButton cancelShowFilteredBtn;
    private ImageButton filteringBtn;

    private final String GET_STATIONINFO_URL = "http://192.168.35.63:8080/api";
    private final String GET_CHARGERSTATUSINFO_URL = "http://";
    private final String GET_FILTEREDSTATIONLIST_URL = "http://";

    private Bitmap blueMarkerIcon;
    private Bitmap greenMarkerIcon;
    private Bitmap yellowMarkerIcon;
    private Bitmap redMarkerIcon;

    private float pressedTime;

    private Button searchPathBtn;

    private StationInfo selectedStationInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stationInfoParser = new StationInfoParser(getContext());
        chargerStatusInfoParser = new ChargerStatusInfoParser(getContext());
        filteredStationInfoParser = new FilteredStationInfoParser(getContext());

        // **************************
        // **************************
        // **************************
        Button test1 = root.findViewById(R.id.btn_server_test1);
        test1.setOnClickListener(view -> {
            GetStationInfo();
            Toast.makeText(getContext(), "Get Station Info", Toast.LENGTH_SHORT).show();
        });
        Button test2 = root.findViewById(R.id.btn_server_test2);
        test2.setOnClickListener(view -> {
            GetChargerStatusInfo();
            Toast.makeText(getContext(), "Get Charger Status Info", Toast.LENGTH_SHORT).show();
        });

        // 뒤로가기 버튼
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                // 정보 페이지가 켜져 있다면 페이지 닫기
                if (infoPage.getVisibility() == View.VISIBLE) {
                    infoPage.animate().translationY(SCREEN_HEIGHT).setListener(hideAllListener);
                }

                // 종료할지 한번 더 물어보기
                else {
                    if (pressedTime == 0) {
                        Toast.makeText(getContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
                        pressedTime = System.currentTimeMillis();
                    } else {
                        int seconds = (int) (System.currentTimeMillis() - pressedTime);
                        if (seconds > 200) {
                            pressedTime = 0;
                        } else {
                            getActivity().finish();
                        }
                    }
                }
            }
        });

        // 스크린 사이즈 캐싱
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;

        // 맵 정보 임시 저장
        if (savedInstanceState != null) {
            mCurLocation = savedInstanceState.getParcelable((KEY_LOCATION));
            mMap.moveCamera(savedInstanceState.getParcelable((KEY_CAMERA_POSITION)));
        }

        // 현재 줌 레벨 표시 텍스트
        //zoomLevelTv = root.findViewById(R.id.text_log_zoom);

        // 현재 카메라 위치 표시 텍스트
        //latlngTv = root.findViewById(R.id.text_log_latlng);

        // 현재 GPS 위치
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        moveToCurLocationBtn = root.findViewById(R.id.btn_curLocation);
        moveToCurLocationBtn.setOnClickListener(view -> {
            getCurrentLocation(task -> {
                if (task.isSuccessful()) {
                    if (mDeviceLocation != null) {
                        setAroundMarkersVisibility(false);
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

        // 충전소 정보 페이지
        infoPage = root.findViewById(R.id.page_stationInfo);
        infoPage.setOnClickListener(view -> {
            infoPage.animate().translationY(0).setListener(showAllListener);
        });
        infoPage.setY(SCREEN_HEIGHT);

        // 충전소 정보 페이지 닫기 버튼
        ImageButton closeInfoPageBtn = root.findViewById(R.id.btn_close_page_stationInfo);
        closeInfoPageBtn.setOnClickListener(view -> {
            infoPage.animate().translationY(SCREEN_HEIGHT).setListener(hideAllListener);
        });

        stationNameTv = root.findViewById(R.id.text_stationName);
        busiNameTv = root.findViewById(R.id.text_busiName);
        useTimeTv = root.findViewById(R.id.text_useTime);
        busiCallTv = root.findViewById(R.id.text_busiCall);
        addressTv = root.findViewById(R.id.text_address);

        chargerInfoLayouts = new LinearLayout[4];
        chargerInfoLayouts[0] = root.findViewById(R.id.layout_chargerInfo1);
        chargerInfoLayouts[1] = root.findViewById(R.id.layout_chargerInfo2);
        chargerInfoLayouts[2] = root.findViewById(R.id.layout_chargerInfo3);
        chargerInfoLayouts[3] = root.findViewById(R.id.layout_chargerInfo4);

        chargerStatTvs = new TextView[4];
        chargerStatTvs[0] = root.findViewById(R.id.text_chargerState1);
        chargerStatTvs[1] = root.findViewById(R.id.text_chargerState2);
        chargerStatTvs[2] = root.findViewById(R.id.text_chargerState3);
        chargerStatTvs[3] = root.findViewById(R.id.text_chargerState4);

        chargerTypeIvs = new ImageView[4][4];
        chargerTypeIvs[0][0] = root.findViewById(R.id.image_combo1);
        chargerTypeIvs[0][1] = root.findViewById(R.id.image_demo1);
        chargerTypeIvs[0][2] = root.findViewById(R.id.image_ac1);
        chargerTypeIvs[0][3] = root.findViewById(R.id.image_slowCharge1);
        chargerTypeIvs[1][0] = root.findViewById(R.id.image_combo2);
        chargerTypeIvs[1][1] = root.findViewById(R.id.image_demo2);
        chargerTypeIvs[1][2] = root.findViewById(R.id.image_ac2);
        chargerTypeIvs[1][3] = root.findViewById(R.id.image_slowCharge1);
        chargerTypeIvs[2][0] = root.findViewById(R.id.image_combo3);
        chargerTypeIvs[2][1] = root.findViewById(R.id.image_demo3);
        chargerTypeIvs[2][2] = root.findViewById(R.id.image_ac3);
        chargerTypeIvs[2][3] = root.findViewById(R.id.image_slowCharge1);
        chargerTypeIvs[3][0] = root.findViewById(R.id.image_combo4);
        chargerTypeIvs[3][1] = root.findViewById(R.id.image_demo4);
        chargerTypeIvs[3][2] = root.findViewById(R.id.image_ac4);
        chargerTypeIvs[3][3] = root.findViewById(R.id.image_slowCharge1);

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

        // 필터링 버튼
        filteringBtn = root.findViewById(R.id.btn_filtering);
        filteringBtn.setOnClickListener(view -> {
            GetFilteredStationList();
            cancelShowFilteredBtn.setVisibility(View.VISIBLE);
            filteringBtn.setVisibility(View.INVISIBLE);
        });

        // 필터링 취소 버튼
        cancelShowFilteredBtn = root.findViewById(R.id.btn_cancelShowFiltered);
        cancelShowFilteredBtn.setOnClickListener(view -> {
            for (int i = 0; i < filteredMarkers.size(); i++) {
                filteredMarkers.get(i).remove();
            }
            setAroundMarkersVisibility(true);

            cancelShowFilteredBtn.setVisibility(View.INVISIBLE);
            filteringBtn.setVisibility(View.VISIBLE);
        });

        // 길찾기 버튼
        searchPathBtn = root.findViewById(R.id.btn_searchPath);
        searchPathBtn.setOnClickListener(view -> {
            findPathByKakaoMap(selectedStationInfo.getLat(), selectedStationInfo.getLng());
        });

        return root;

        /*
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
         */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 마커 클릭 시 해당 위치의 충전소 정보 표시
        mMap.setOnMarkerClickListener(marker -> {

            // 해당 마커를 최상단으로 표시
            marker.setZIndex(Float.MAX_VALUE);

            // stationId를 활용해 충전소에 대한 정보를 얻어와 UI에 충전소 정보 표시
            String stationId = marker.getTag().toString();
            StationInfo info = stationInfoHashMap.get(stationId);

            selectedStationInfo = info;

            if (info != null) {
                stationNameTv.setText(info.getStatNm());
                busiNameTv.setText("운경기관  " + info.getBusiNm());
                useTimeTv.setText("운영시간  " + info.getUseTime());
                busiCallTv.setText("전화번호  " + info.getBusiCall());
                addressTv.setText("주소  " + info.getAddr());

                Object[] values = info.getChargerInfoHashMap().values().toArray();
                ChargerInfo[] chargerInfos = new ChargerInfo[values.length];
                for (int i = 0; i < values.length; i++)
                    chargerInfos[i] = (ChargerInfo) values[i];

                for (int i = 0; i < chargerInfos.length; i++) {
                    chargerInfoLayouts[i].setVisibility(View.VISIBLE);

                    ChargerInfo chargerInfo = chargerInfos[i];

                    TextView tv = chargerStatTvs[i];
                    switch (chargerInfo.getStatus()) {
                        case 1:
                            tv.setText("통신 이상");
                            break;
                        case 2:
                            tv.setText("충전 대기");
                            break;
                        case 3:
                            tv.setText("충전중");
                            break;
                        case 4:
                            tv.setText("운영 중지");
                            break;
                        case 5:
                            tv.setText("점검중");
                            break;
                        case 9:
                            tv.setText("상태 미확인");
                            break;
                    }

                    ImageView[] ivs = chargerTypeIvs[i];
                    switch (chargerInfo.getType()) {
                        case 1:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                        case 2:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            break;
                        case 3:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                        case 4:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                        case 5:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                        case 6:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                        case 7:
                            ivs[0].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[1].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            ivs[2].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                            ivs[3].setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_700));
                            break;
                    }
                }
                for (int i = chargerInfos.length; i < chargerTypeIvs.length; i++) {
                    chargerInfoLayouts[i].setVisibility(View.INVISIBLE);
                }

                // 페이지 열기 애니메이션 실행
                infoPage.animate().translationY(SCREEN_HEIGHT * 0.6f).setListener(showBrieflyListener);
            }
            return true;
        });

        // 지도 클릭시 충전소 정보 페이지 닫기 애니메이션 실행
        mMap.setOnMapClickListener(latLng -> {
            infoPage.animate().translationY(SCREEN_HEIGHT).setListener(hideBrieflyListener);
        });

        mMap.setOnCameraMoveListener(() -> {

            // 현재 카메라 위치에 따라 현재 구역 계산
            lat = mMap.getCameraPosition().target.latitude;
            lng = mMap.getCameraPosition().target.longitude;

            curSectorX = (int) ((lng - FAR_WEST) / SECTOR_WIDTH);
            curSectorY = (int) ((lat - FAR_SOUTH) / SECTOR_HEIGHT);

            //checkZoom();
            //checkAroundMarkers(true);

            // 현재 줌 레벨 표시
            //zoomLevelTv.setText("Zoom : " + mMap.getCameraPosition().zoom);

            // 현재 카메라 위치 표시
            //latlngTv.setText("LatLng : " + lat + ", " + lng);
        });

        parseStationInfoData(stationInfoParser.getJsonString());
        parseChargerStatusInfoData(chargerStatusInfoParser.getJsonString());
        createMarkers();
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

    private Marker createMarker(double lat, double lng, int count, boolean visibility) {
        int color = count > 4 ? MARKER_GREEN : count > 2 ? MARKER_YELLOW : MARKER_RED;
        MarkerOptions option = new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(markerIconWithLabel(Integer.toString(count), color)));

        Marker marker = mMap.addMarker(option);
        marker.setVisible(visibility);

        return marker;
    }

    private Marker createFilteredMarker(double lat, double lng, int color) {
        MarkerOptions option = new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.fromBitmap(getMarkerIcon(color)));
        Marker marker = mMap.addMarker(option);
        marker.setVisible(true);

        return marker;
    }

    public void setCameraPosition(double lat, double lng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));
    }

    public void setCameraPosition(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    public void setCameraBounds(Marker[] markers, int padding) {
        double left = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;
        double top = Double.MIN_VALUE;
        double bottom = Double.MAX_VALUE;

        for (int i = 0; i < markers.length; i++) {
            double lat = markers[i].getPosition().latitude;
            double lng = markers[i].getPosition().longitude;

            if (lat > top) top = lat;
            if (lat < bottom) bottom = lat;
            if (lng > right) right = lng;
            if (lng < left) left = lng;
        }

        LatLngBounds bounds = new LatLngBounds(new LatLng(bottom, left), new LatLng(top, right));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 12));
    }

    private Bitmap markerIconWithLabel(String str, int markerColor) {
        Bitmap src = null;
        switch (markerColor) {
            case MARKER_BLUE:
                if (blueMarkerIcon == null)
                    blueMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_blue), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = blueMarkerIcon;
                break;
            case MARKER_GREEN:
                if (greenMarkerIcon == null)
                    greenMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_green), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = greenMarkerIcon;
                break;
            case MARKER_YELLOW:
                if (yellowMarkerIcon == null)
                    yellowMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_yellow), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = yellowMarkerIcon;
                break;
            case MARKER_RED:
                if (redMarkerIcon == null)
                    redMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_red), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = redMarkerIcon;
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

    private Bitmap getMarkerIcon(int markerColor) {
        Bitmap src = null;
        switch (markerColor) {
            case MARKER_BLUE:
                if (blueMarkerIcon == null)
                    blueMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_blue), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = blueMarkerIcon;
                break;
            case MARKER_GREEN:
                if (greenMarkerIcon == null)
                    greenMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_green), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = greenMarkerIcon;
                break;
            case MARKER_YELLOW:
                if (yellowMarkerIcon == null)
                    yellowMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_yellow), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = yellowMarkerIcon;
                break;
            case MARKER_RED:
                if (redMarkerIcon == null)
                    redMarkerIcon = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_marker_red), MARKER_WIDTH, MARKER_HEIGHT, false);
                src = redMarkerIcon;
                break;
        }

        return src == null ? null : Bitmap.createBitmap(MARKER_WIDTH, MARKER_HEIGHT, src.getConfig());
    }

    private void parseStationInfoData(String jsonStr) {
        stationInfoHashMap = stationInfoParser.Parse(jsonStr);

        StationInfo[] infos = new StationInfo[stationInfoHashMap.values().size()];
        stationInfoHashMap.values().toArray(infos);

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

            /*
            if(infos[i].getLng() > FAR_WEST && infos[i].getLat() > FAR_SOUTH) {
                int sectorX = (int) ((infos[i].getLng() - FAR_WEST) / SECTOR_WIDTH);
                int sectorY = (int) ((infos[i].getLat() - FAR_SOUTH) / SECTOR_HEIGHT);
                markersBySector[sectorY][sectorX].add(marker);
            }
             */
        }
    }

    private void parseChargerStatusInfoData(String jsonStr) {
        chargerStatusInfoParser.Parse(jsonStr, stationInfoHashMap);
    }

    private void parseFilteredStationInfoData(String jsonStr) {
        ArrayList<String> list = filteredStationInfoParser.Parse(jsonStr);

        for (int i = 0; i < list.size(); i++) {
            String id = list.get(i);
            if (stationInfoHashMap.containsKey(id)) {
                StationInfo info = stationInfoHashMap.get(id);
                Marker marker = createFilteredMarker(info.getLat(), info.getLng(), info.getUsableChargerCount());
                marker.setTag(info.getStatId());

                filteredMarkers.add(marker);
            }
        }
    }

    private void createMarkers() {
        StationInfo[] infos = new StationInfo[stationInfoHashMap.values().size()];
        stationInfoHashMap.values().toArray(infos);

        for (int i = 0; i < infos.length; i++) {
            Marker marker = createMarker(infos[i].getLat(), infos[i].getLng(), infos[i].getUsableChargerCount(), true);
            marker.setTag(infos[i].getStatId());
        }
    }

    private void setAroundMarkersVisibility(boolean visibility) {
        if (!showFiltered && zoom > MIN_MARKER_VISIBLE_ZOOM) {
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
                                    markers.get(i).setVisible(visibility);
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkAroundMarkers() {
        // 카메라 위치에 따라 마커 활성화/비활성화
        // 현재 위치한 구역의 주변 구역들을 검사해서 해당 마커가 화면 범위 내에 있는지 밖에 있는지 계산하여 마커 활성화/비활성화 결정
        if (!showFiltered && zoom > MIN_MARKER_VISIBLE_ZOOM) {
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
                                    markers.get(i).setVisible(true);
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
            if (!showFiltered && zoom < MIN_MARKER_VISIBLE_ZOOM && newZoom > MIN_MARKER_VISIBLE_ZOOM) {
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

    private void GetStationInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "qq@naver.com");
        map.put("lat", Double.toString(lat));
        map.put("lng", Double.toString(lng));
        MapRequest stationInfoRequest = new MapRequest(Request.Method.POST, GET_STATIONINFO_URL, map, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseStationInfoData(response);
                Log.i("ev-sherpa", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ev-sherpa", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stationInfoRequest);

        Toast.makeText(getContext(), "Get Station Info", Toast.LENGTH_SHORT).show();
    }

    private void GetChargerStatusInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "qq@naver.com");
        MapRequest stationInfoRequest = new MapRequest(Request.Method.POST, GET_CHARGERSTATUSINFO_URL, map, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                parseChargerStatusInfoData(response);
                Log.i("ev-sherpa", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ev-sherpa", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stationInfoRequest);

        Toast.makeText(getContext(), "Get Charger Status Info", Toast.LENGTH_SHORT).show();
    }

    private void GetFilteredStationList() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "qq@naver.com");
        map.put("lat", Double.toString(lat));
        map.put("lng", Double.toString(lng));
        MapRequest stationInfoRequest = new MapRequest(Request.Method.POST, GET_FILTEREDSTATIONLIST_URL, map, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                showFiltered = true;

                parseFilteredStationInfoData(response);
                moveToCurLocationBtn.performClick();

                Log.i("ev-sherpa", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ev-sherpa", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stationInfoRequest);

        Toast.makeText(getContext(), "Get Filtered Station Info", Toast.LENGTH_SHORT).show();
    }

    private void findPathByKakaoMap(double endLat, double endLng) {

        double startLat = mDeviceLocation.getLatitude();
        double startLng = mDeviceLocation.getLongitude();

        String url = "kakaomap://route?sp=" + startLat + "," + startLng + "&ep=" + endLat + "," + endLng + "&by=CAR";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}