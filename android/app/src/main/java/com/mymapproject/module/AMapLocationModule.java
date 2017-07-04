package com.mymapproject.module;

import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Created by hth on 2017/7/4.
 */

public class AMapLocationModule extends com.facebook.react.bridge.ReactContextBaseJavaModule {

    private ReactApplicationContext mReactApplicationContext;
    private AMapLocationClient mLocationClient = null;
    // 定位回调监听器
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            // 实例化一个回调给RN端的map对象
            WritableMap params = Arguments.createMap();
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    // 定位成功
                    params.putString("address", aMapLocation.getAddress());
                } else {
                    // 定位失败
                    params.putBoolean("result", false);
                }
            }
            // 发送给RN端
            sendEvent("onLocationChanged", params);
        }
    };
    private void sendEvent(String eventName, @Nullable WritableMap params) {
        if (mReactApplicationContext != null) {
            mReactApplicationContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }
    public AMapLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // 在构造函数中实例化定位功能的相关对象，并设置相关的定位配置，跟原生实现定位功能一样配置
        mReactApplicationContext = reactContext;
        mLocationClient = new AMapLocationClient(reactContext);
        mLocationClient.setLocationListener(mAMapLocationListener);
        AMapLocationClientOption clientOption = new AMapLocationClientOption();
        clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving); // 低功耗模式, 只使用wifi
        clientOption.setOnceLocation(true);
        mLocationClient.setLocationOption(clientOption);
    }

    @Override
    public String getName() {
        return "AMapLocation";
    }
    @ReactMethod    //  可以被RN端调用的方法
    public void destory() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation(); // 停止定位
            mLocationClient.onDestroy(); // 销毁定位
        }
    }
    @ReactMethod   // 可以被RN端掉用的方法，开启定位
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }
}
