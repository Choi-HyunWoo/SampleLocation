package com.hw.corcow.samplelocation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager mLM;
    String mProvider;
    TextView locationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationView = (TextView)findViewById(R.id.text_location);

        mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);         // 1. LocationManager를 getSystemService(context.___)로 얻어온다.
        mProvider = LocationManager.GPS_PROVIDER;                                   // 2. Provider 얻어오기

    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            locationView.setText(location.getLatitude() + "," + location.getLongitude());
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    // Start 시 위치정보를 얻어오겠다.
    boolean isFirst = true;     // 단말기의 위치정보 설정 확인을 한번만 하도록
    @Override
    protected void onStart() {
        super.onStart();
        // 단말기에 위치정보 확인 기능이 켜져 있는지 확인..
        if (!mLM.isProviderEnabled(mProvider)) {
            if (isFirst) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                isFirst = false;
            } else {
                Toast.makeText(this, "This is ...", Toast.LENGTH_SHORT);
                finish();
            }
            return;
        }

        Location location = mLM.getLastKnownLocation(mProvider);          // Location 받아오기
        if (location != null) {
            mListener.onLocationChanged(location);
        }
        mLM.requestLocationUpdates(mProvider, 0, 0, mListener);        // parameters : provider, 시간(~m초 뒤에 알려줘), 거리(몇m이상 벗어나면 알려줘), Listener

    }
}
