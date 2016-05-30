package ku.im.dangjuhang.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapResourceProvider;

import ku.im.dangjuhang.Fragments.NMap.NMapCalloutCustomOldOverlay;
import ku.im.dangjuhang.Fragments.NMap.NMapFragment;
import ku.im.dangjuhang.Fragments.NMap.NMapViewerResourceProvider;
import ku.im.dangjuhang.R;

/**
 * Created by Tazo on 2016-05-27.
 */
public class MapFrag extends NMapFragment {
    TextView text;
    NMapView mMapView;
    NMapController mMapController;
    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.mapfrag, container, false);
        mMapView = (NMapView) root.findViewById(R.id.mapView);
        mMapView.setClientId("S6nKrH6CGNXyt2TKEYZY");
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();
        return root;
    }

    public void Set(Context context)
    {
        NMapLocationManager mMapLocationManager = new NMapLocationManager(context);
        mMapLocationManager.setOnLocationChangeListener(new NMapLocationManager.OnLocationChangeListener() {
            @Override
            public boolean onLocationChanged(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {
                return false;
            }

            @Override
            public void onLocationUpdateTimeout(NMapLocationManager nMapLocationManager) {

            }

            @Override
            public void onLocationUnavailableArea(NMapLocationManager nMapLocationManager, NGeoPoint nGeoPoint) {

            }
        });

        NMapCompassManager mMapCompassManager = new NMapCompassManager(getActivity());
        NMapViewerResourceProvider mMapViewerResourceProvider = new NMapViewerResourceProvider(context);
        NMapOverlayManager mOverlayManager = new NMapOverlayManager(context, mMapView, mMapViewerResourceProvider);
        NMapMyLocationOverlay mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
    }
}