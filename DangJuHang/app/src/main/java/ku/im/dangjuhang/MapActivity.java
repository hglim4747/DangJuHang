/*
 * Copyright 2016 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ku.im.dangjuhang;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import java.util.ArrayList;

import ku.im.dangjuhang.Fragments.HeadlinesFragment;
import ku.im.dangjuhang.Fragments.NMap.NMapPOIflagType;
import ku.im.dangjuhang.Fragments.NMap.NMapViewer;
import ku.im.dangjuhang.Fragments.NMap.NMapViewerResourceProvider;

/**
 * Sample class for map viewer library.
 *
 * @author kyjkim
 */
public class MapActivity extends NMapActivity  {
    private static final String LOG_TAG = "MapActivity";
    private static final boolean DEBUG = false;

    // set your Client ID which is registered for NMapViewer library.
    private static final String CLIENT_ID = "S6nKrH6CGNXyt2TKEYZY";
    private NMapView mMapView;
    private NMapController mMapController;
    private NMapViewerResourceProvider mMapViewerResourceProvider;
    private NMapOverlayManager mOverlayManager;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;
    private NMapMyLocationOverlay mMyLocationOverlay;
    NMapPOIdataOverlay poiDataOverlay;

    SeoulXMLParser mXMLParser;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mXMLParser = HeadlinesFragment.mXMLParser;

        mMapView = (NMapView) findViewById(R.id.mapView);
        mMapView.setClientId(CLIENT_ID);

        // initialize map view
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        mMapController = mMapView.getMapController();

        mMapView.setScalingFactor(1.0f, false);

        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);

        int markerId = NMapPOIflagType.PIN;
        NMapPOIdata poiData = new NMapPOIdata(100, mMapViewerResourceProvider);
        poiData.beginPOIdata(5);

        ArrayList<Hangsa> list = mXMLParser.getArray();
        for(int i=0; i<list.size(); i++)
        {
            Double[] v = new Double[2];
            Double[] v1 = new Double[2];
            boolean success = new Client().SearchPlace(list.get(i).getplace(), v, v1);
            if(success)
            {
                poiData.addPOIitem(v[0].doubleValue(), v1[0].doubleValue(), list.get(i).getTitle(), markerId, 0);
            }
        }

        poiData.endPOIdata();
        poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);
        poiDataOverlay.showAllPOIdata(0);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(new NMapLocationManager.OnLocationChangeListener() {
            @Override
            public boolean onLocationChanged(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {
                if (mMapController != null) {
                    mMapController.animateTo(nGeoPoint);
                }
                return true;
            }

            @Override
            public void onLocationUpdateTimeout(NMapLocationManager nMapLocationManager) {

            }

            @Override
            public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {

            }
        });

        mMapCompassManager = new NMapCompassManager(this);
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, null);
        mMapLocationManager.enableMyLocation(true);

        startMyLocation();
    }

    private void startMyLocation() {

        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {

                if (!mMapView.isAutoRotateEnabled()) {
                    mMyLocationOverlay.setCompassHeadingVisible(true);
                    mMapCompassManager.enableCompass();
                    mMapView.setAutoRotateEnabled(false, false);
                    //mMapContainerView.requestLayout();
                } else {
                    stopMyLocation();
                }

                mMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Toast.makeText(MapActivity.this, "위치 사용 설정을 해주세요.",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (mMapView.isAutoRotateEnabled()) {
                mMyLocationOverlay.setCompassHeadingVisible(false);

                mMapCompassManager.disableCompass();

                mMapView.setAutoRotateEnabled(false, false);

               // mMapContainerView.requestLayout();
            }
        }
    }
}