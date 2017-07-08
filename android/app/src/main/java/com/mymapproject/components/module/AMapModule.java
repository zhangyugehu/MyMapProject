package com.mymapproject.components.module;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.mymapproject.components.view.RCTAMapView;

/**
 * @author zhangyugehu
 * @version V1.0
 * @data 2017/07/07
 */

public class AMapModule extends ReactContextBaseJavaModule implements AMapLocationListener {

    private final String NAME = "AMapModule";
    private ReactApplicationContext mContext;

    private RCTAMapView mMapView;
    private AMapLocationClient mClient;

    public AMapModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private void setAMapView(int tag) {
        if (mMapView == null) {
            mMapView = ((RCTAMapView) mContext.getCurrentActivity().findViewById(tag));
        }
    }

    /**
     * 定位当前位置
     */
    @ReactMethod public void startLocation(){
        if (mClient == null) {
            mClient = new AMapLocationClient(mContext);
            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);
//            mLocationOption.setOnceLocationLatest(true);
            //定位缓存策略
            mLocationOption.setLocationCacheEnable(true);
//            mLocationOption.setInterval(10);
//            mLocationOption.setInterval(3*60*1000);
            //设置定位参数
            mClient.setLocationOption(mLocationOption);

            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        }
        mClient.startLocation();
    }

    @ReactMethod public void setCamera(ReadableMap map){
        LatLng latLng = new LatLng(map.getDouble("lng"), map.getDouble("lat"));
        mMapView.setCamera(latLng);
        mMapView.addMarkersToMap(latLng);
    }

    /**
     * 在地图上添加marker
     */
    @ReactMethod public void addMarkersToMap(double lng, double lat) {
        LatLng latLng = new LatLng(lng, lat);
        mMapView.addMarkersToMap(latLng);
    }
    /**
     * 清空地图上所有已经标注的marker
     */
    @ReactMethod public void clearAllMarker(int tag) {
//        setAMapView(tag);
//        MarkerUtil.clearMarker(mMapView.getAmap());
    }

    /**
     * mClient.setLocationListener(this)
     * @param aMapLocation
     */
    @Override public void onLocationChanged(AMapLocation aMapLocation) {

    }
}
