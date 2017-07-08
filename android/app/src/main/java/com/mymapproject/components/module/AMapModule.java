package com.mymapproject.components.module;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.mymapproject.components.view.RCTAMapView;
import com.mymapproject.components.view.manager.AMapViewManager;

/**
 *
 * @author zhangyugehu
 * @version V1.0
 * @data 2017/07/07
 */

public class AMapModule extends ReactContextBaseJavaModule {

    private final String NAME = "AMapModule";
    private ReactApplicationContext mContext;

//    private AMapViewManager mMapManager;
    private AMapLocationClient mClient;

    public AMapModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    private void initLocation() {
        mClient = new AMapLocationClient(mContext.getApplicationContext());
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//        mOption.setHttpTimeOut(TIMEOUT);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(STRINTERVAL);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mClient.setLocationOption(mOption);
        mClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    //解析定位结果
//                    aMapLocation.getAoiName();
//                    String LocationData = Utils.getLocationStr(loc);
//                    WritableMap params = Arguments.createMap();
//                    params.putString("AMapLocation", aMapLocation.getAoiName());
//                    sendEvent(mContext, "onAMAPLocationResult", params);
                    toast(aMapLocation.getAoiName());
                } else {
                    toast("aMapLocation is null.");
                }
            }
        });
    }

    private void toast(String text){
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    private RCTAMapView findViewByTag(int tag){
        RCTAMapView mapView = (RCTAMapView) mContext.getCurrentActivity().findViewById(tag);
        toast("mapView " + mapView + "  tag " + tag);
        return mapView;
    }

    /**
     * 定位当前位置
     */
    @ReactMethod public void startLocation(){
        if(mClient == null) {
            initLocation();
        }
        mClient.startLocation();
    }

    @ReactMethod public void setMyLocationEnabled(int tag){
        RCTAMapView mapView = findViewByTag(tag);
        if(mapView == null){ return; }
        mapView.setMyLocationEnabled(true);
    }

    @ReactMethod public void setCamera(int tag, double lng, double lat){
        RCTAMapView mapView = findViewByTag(tag);
        if(mapView == null){ return; }
        LatLng latLng = new LatLng(lng, lat);
        mapView.setCamera(latLng);
        mapView.addMarkersToMap(latLng);
    }

}
