package com.example.evsherpa.ui.home;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.evsherpa.R;
import com.example.evsherpa.StationInfo;
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

    private HomeViewModel homeViewModel;

    private final float DEFAULT_ZOOM = 18;


    private final String KEY_LOCATION = "current_location";
    private final String KEY_CAMERA_POSITION = "camera_position";

    private GoogleMap mMap;
    private Location mCurLocation;
    private Location mDeviceLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private TextView tv;
    private Button btn;

    private HashMap<Integer, StationInfo> stationInfoHashMap = new HashMap<Integer, StationInfo>();

    private final int zoomLevelDivider = 5;
    private int zoomLevel;
    private ArrayList<Marker>[] markersByZoomLevel = new ArrayList[3];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        tv = root.findViewById(R.id.text_log);
        btn = root.findViewById(R.id.btn_curLocation);

        btn.setOnClickListener(view -> {
            getCurrentLocation(task -> {
                if (task.isSuccessful()) {
                    if (mDeviceLocation != null) {
                        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude())).title("디바이스 위치")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        marker.setTag(0);

                        setCameraPosition(mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude(), DEFAULT_ZOOM);
                    }
                }
            });
        });

        if (savedInstanceState != null) {
            mCurLocation = savedInstanceState.getParcelable((KEY_LOCATION));
            mMap.moveCamera(savedInstanceState.getParcelable((KEY_CAMERA_POSITION)));
        }

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 로컬의 파일을 읽어와 충전소에 대한 정보를 전부 로드하고 해당 정보를 바탕으로 Marker 생성
        /*
        ArrayList<StationInfo> infos = new StationInfoParser().Parse("file.xml");
        for (int i = 0; i < infos.size(); i++) {
            StationInfo info = infos.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(info.getLat(), info.getLng())).title(info.getStatNm())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))); // 원하는 색상
            marker.setTag(info.getStatId());

            stationInfoHashMap.put(info.getStatId(), info);
        }
        */

        // 마커 클릭 시 해당 위치의 충전소 정보 표시
        mMap.setOnMarkerClickListener(marker -> {
            marker.setZIndex(Float.MAX_VALUE);

            // stationId를 활용해 충전소에 대한 정보를 얻어와 UI에 충전소 정보 표시
            int stationId = (int) marker.getTag();
            StationInfo info = stationInfoHashMap.get(stationId);

            String text = String.format("Tag : %d, Lat : %f, Lng : %f, Title : %s", (int) marker.getTag(), marker.getPosition().latitude, marker.getPosition().longitude, marker.getTitle());
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

            return true;
        });

        // 카메라 줌 정도에 따라 마커 활성화/비활성화
        mMap.setOnCameraMoveListener(() -> {
            int newZoomLevel = (int) mMap.getCameraPosition().zoom / zoomLevelDivider;

            if (newZoomLevel != zoomLevel) {
                //ArrayList<Marker> markers = markersByZoomLevel[zoomLevel];
                //for (int i = 0; i < markers.size(); i++)
                //    markers.get(i).setVisible(false);

                //markers = markersByZoomLevel[newZoomLevel];
                //for (int i = 0; i < markers.size(); i++)
                //    markers.get(i).setVisible(true);
            }

            tv.setText("Zoom : " + mMap.getCameraPosition().zoom);
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
                }
            });
            locationResult.addOnCompleteListener(onCompleteListener);
        }
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
}