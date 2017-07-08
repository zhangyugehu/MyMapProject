package com.mymapproject.components.view;

import android.content.Context;
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
import com.amap.api.maps2d.model.MarkerOptions;

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

    public RCTAMapView(@NonNull Context context) {
        this(context, null);
    }

    public RCTAMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCTAMapView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMapView = new MapView(context);
        mMapView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mAmap = mMapView.getMap();
        UiSettings uiSettings = mAmap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);//显示缩放按钮
        //缩放按钮  右边界中部：ZOOM_POSITION_RIGHT_CENTER 右下：ZOOM_POSITION_RIGHT_BUTTOM。
        uiSettings.setZoomPosition(ZOOM_POSITION_RIGHT_BUTTOM);
        //Logo的位置 左下：LOGO_POSITION_BOTTOM_LEFT 底部居中：LOGO_POSITION_BOTTOM_CENTER 右下：LOGO_POSITION_BOTTOM_RIGHT
        uiSettings.setLogoPosition(ZOOM_POSITION_RIGHT_BUTTOM);
        uiSettings.setCompassEnabled(false);//指南针
        uiSettings.setZoomGesturesEnabled(true);//手势缩放
        uiSettings.setScaleControlsEnabled(true);//比例尺
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

    public void addMarkersToMap(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .position(latLng)
                .draggable(true);
        mAmap.addMarker(markerOptions);
    }
}
