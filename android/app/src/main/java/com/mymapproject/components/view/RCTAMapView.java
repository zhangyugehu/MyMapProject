package com.mymapproject.components.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.mymapproject.R;

import java.util.ArrayList;
import java.util.List;

import static com.amap.api.maps2d.AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM;

/**
 * @author zhangyugehu
 * @version V1.0
 * @data 2017/07/07
 */

public class RCTAMapView extends FrameLayout {

    private MapView mMapView;
    private AMap mAmap;
    private float mZoomLevel;

    private List<Marker> mMarkerList;

    public RCTAMapView(@NonNull Context context) {
        this(context, null);
    }

    public RCTAMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCTAMapView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMarkerList = new ArrayList<>();
        mMapView = new MapView(context);
        mMapView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAmap = mMapView.getMap();
        mAmap.setMyLocationEnabled(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeView(mMapView);
        mMapView.onDestroy();
    }

    private void setZoomLevel(float zoomLevel){
        this.mZoomLevel = zoomLevel;
    }

    public void setCamera(LatLng latLng) {
        LatLng latlng = new LatLng(latLng.latitude, latLng.longitude);
        mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, mZoomLevel));
    }

    public void addLine(LatLng start, LatLng end){

        // 绘制曲线
        mAmap.addPolyline((new PolylineOptions())
                .add(start, end)
                .geodesic(true).color(Color.RED)
        );
    }

    public void addMarkersToMap(LatLng location) {
        //绘制marker
        Marker marker = mAmap.addMarker(new MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));

        mMarkerList.add(marker);
    }

    public void removeAllMarkers(){
        for(Marker marker : mMarkerList){
//            mAmap.
        }
    }

    public AMap getAMap() {
        return mAmap;
    }

    public void setMyLocationEnabled(boolean b) {
        mAmap.setMyLocationEnabled(b);
    }
}
