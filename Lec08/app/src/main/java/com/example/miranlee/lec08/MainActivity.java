package com.example.miranlee.lec08;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    MapFragment mapFr;
    GoogleMap map;
    LocationManager lm;
    Criteria criteria;
    GoogleApiClient mGoogleApiClient = null;
    Location mLastLocation;
    //LocationListener listener;
    LocationRequest mLocationRequest;

//    Spinner spinner;
//    ArrayList<String> m_data;
//    ArrayAdapter<String> m_adapter;
//    Geocoder gc;
//    double latitude, longitude;
//    EditText edittext;
//    Button gobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        mapFr = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFr.getMapAsync(this); // 비동기식 맵 서비스 제공. 인자는 인터페이스 정보
        criteria = new Criteria();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(1000)
                .setSmallestDisplacement(100);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String locationProvider = lm.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = lm.getLastKnownLocation(locationProvider);

//        listener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                updateMap(location);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                alertStatus(provider);
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//                alertProvider(provider);
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                checkProvider(provider);
//            }
//        };

//        lm.requestLocationUpdates(locationProvider, 1000, 1, listener);
//        gc = new Geocoder(this, Locale.KOREAN);
//        spinner = (Spinner)findViewById(R.id.spinner);
//        m_data = new ArrayList<String>();
//        m_data.add("Hybrid");
//        m_data.add("Normal");
//        m_data.add("Satellite");
//        m_data.add("Terrain");
//
//        m_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, m_data);
//        spinner.setAdapter(m_adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (map != null) {
//                    switch (position) {
//                        case 0:
//                            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//                            break;
//                        case 1:
//                            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                            break;
//                        case 2:
//                            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                            break;
//                        case 3:
//                            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//                            break;
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        edittext = (EditText)findViewById(R.id.edittext);
//        gobtn = (Button)findViewById(R.id.gobtn);
    }

    public void updateMap(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        final LatLng Loc = new LatLng(latitude, longitude);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16));
        MarkerOptions options = new MarkerOptions();
        options.position(Loc);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        options.title(location.toString());
    }

    public void alertStatus(String provider) {
        Toast.makeText(this, "위치서비스가 " + provider + "로 변경됨", Toast.LENGTH_SHORT).show();
    }

    public void alertProvider(String provider) {
        Toast.makeText(this, provider + "서비스가 켜졌습니다.", Toast.LENGTH_SHORT).show();
    }

    public void checkProvider(String provider) {
        Toast.makeText(this, provider + "위치 서비스가 꺼져 있습니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//            map.setMyLocationEnabled(true);
//        } // 현재 위치 컨트롤러
//
//        UiSettings uiSettings = map.getUiSettings();
//        uiSettings.setZoomControlsEnabled(true); //구글 맵 UI
//
//        latitude = 37.554752;
//        longitude = 126.970631;
//        final LatLng Loc = new LatLng(latitude, longitude);
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16)); // 특정 위도와 경도
//
//        MarkerOptions moptions = new MarkerOptions();
//        moptions.position(Loc);
//        moptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//        moptions.title("역");
//        moptions.snippet("서울역");
//        map.addMarker(moptions);
//        Marker mk1 = map.addMarker(moptions);
//        mk1.showInfoWindow();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        lm.removeUpdates(listener);
        if(mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation != null) {
            updateMap(mLastLocation);
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        Intent intent = new Intent(this, MyReceiver.class);
        android.app.PendingIntent mPending = android.app.PendingIntent.getBroadcast(this, 0, intent, 0);
        lm.addProximityAlert(37.543682, 127.074938, 1000, -1, mPending);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            updateMap(location);
        }
    }

    public static class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean bEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, true);
            Toast.makeText(context, bEnter? "집에 도착이요" : "집에 들어가세요", Toast.LENGTH_SHORT).show();
        }
    }

//    public void searchPlace(View v) {
//        String place = edittext.getText().toString();
//
//        try {
//            List<Address> addr = gc.getFromLocationName(place,5);
//            if(addr != null) {
//                latitude = addr.get(0).getLatitude();
//                longitude = addr.get(0).getLongitude();
//
//                final LatLng Loc = new LatLng(latitude, longitude);
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(Loc, 16)); // 특정 위도와 경도
//            }
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }
}

