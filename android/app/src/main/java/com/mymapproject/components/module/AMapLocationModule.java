package com.mymapproject.components.module;

import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hth on 2017/7/4.
 */

public class AMapLocationModule extends com.facebook.react.bridge.ReactContextBaseJavaModule
    implements AMapLocationListener{

    private ReactApplicationContext mReactContext;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption;
    private AMapLocationClient mlocationClient;

    private Callback mOnLocationChangedCallBack;
    private Callback mOnErrorCallBack;

    public AMapLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
        mlocationClient = new AMapLocationClient(reactContext);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
    }


    @Override
    public String getName() {
        return "AMapLocationModule";
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if(mOnLocationChangedCallBack == null){ return; }
        if (amapLocation == null) { return; }
        if (amapLocation.getErrorCode() == 0) {
            //定位成功回调信息，设置相关消息
            StringBuilder builder = new StringBuilder();
            builder.append("当前定位结果来源：").append(amapLocation.getLocationType()).append("\n");//获取当前定位结果来源，如网络定位结果，详见定位类型表
            builder.append("纬度：").append(amapLocation.getLatitude()).append("\n");//获取纬度
            builder.append("经度：").append(amapLocation.getLongitude()).append("\n");//获取经度
            builder.append("精度信息：").append(amapLocation.getAccuracy()).append("\n");//获取精度信息
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            builder.append("定位时间：").append(df.format(date)).append("\n");//定位时间
            builder.append("地址：").append(amapLocation.getAddress()).append("\n");//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
            builder.append("国家信息：").append(amapLocation.getCountry()).append("\n");//国家信息
            builder.append("省信息：").append(amapLocation.getProvince()).append("\n");//省信息
            builder.append("城市信息：").append(amapLocation.getCity()).append("\n");//城市信息
            builder.append("城区信息：").append(amapLocation.getDistrict()).append("\n");//城区信息
            builder.append("街道信息：").append(amapLocation.getStreet()).append("\n");//街道信息
            builder.append("街道门牌号信息：").append(amapLocation.getStreetNum()).append("\n");//街道门牌号信息
            builder.append("城市编码：").append(amapLocation.getCityCode()).append("\n");//城市编码
            builder.append("地区编码：").append(amapLocation.getAdCode()).append("\n");//地区编码
            builder.append("获取当前定位点的AOI信息：").append(amapLocation.getAoiName()).append("\n");//获取当前定位点的AOI信息
            mOnLocationChangedCallBack.invoke(builder.toString());
        } else {
            if(mOnErrorCallBack == null){ return; }
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());
            mOnErrorCallBack.invoke(amapLocation.getErrorInfo());
        }
    }

    @ReactMethod protected void toast(String text) {
        Toast.makeText(mReactContext, text, Toast.LENGTH_SHORT).show();
    }

    @ReactMethod protected void setSuccessListener(Callback successCallback) {
        mOnLocationChangedCallBack = successCallback;
    }

    @ReactMethod protected void setErrorListener(Callback onErrorCallBack) {
        mOnErrorCallBack = onErrorCallBack;
    }

    @ReactMethod protected void startLocation() {
        //启动定位
        mlocationClient.startLocation();
    }

    @ReactMethod protected void stopLocation(){
        if(mlocationClient != null) { mlocationClient.stopLocation(); }
        Log.d("AMapLocationModule", "stopLocation: ");
    }

    @ReactMethod protected void tipLocation(long latitude, long longitude){

    }
}
