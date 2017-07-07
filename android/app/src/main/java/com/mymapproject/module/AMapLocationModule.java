package com.mymapproject.module;

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

public class AMapLocationModule extends com.facebook.react.bridge.ReactContextBaseJavaModule {

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
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if(mOnLocationChangedCallBack == null){ return; }
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        int locationType = amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double latitude = amapLocation.getLatitude();//获取纬度
                        double longitude = amapLocation.getLongitude();//获取经度
                        float accuracy = amapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(amapLocation.getTime());
                        String time = df.format(date);//定位时间
                        mOnLocationChangedCallBack.invoke(locationType, latitude, longitude, accuracy, time);
                    } else {
                        if(mOnErrorCallBack == null){ return; }
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                        mOnErrorCallBack.invoke(amapLocation.getErrorInfo());
                    }
                }
            }
        });
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

    @ReactMethod
    public void toast(String text) {
        Toast.makeText(mReactContext, text, Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    protected void startLocation(Callback successCallback, Callback onErrorCallBack) {
        //启动定位
        mOnLocationChangedCallBack = successCallback;
        mOnErrorCallBack = onErrorCallBack;
        mlocationClient.startLocation();
    }

    @ReactMethod
    protected void stopLocation(){
        mlocationClient.stopLocation();
        Log.d("AMapLocationModule", "stopLocation: ");
    }

    /**
     * 开始进行poi搜索
     */
    @ReactMethod
    protected void doSearchQuery(String keyWord, final Callback onPoiSearched, final Callback onPoiItemSearched) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(mReactContext, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                onPoiSearched.invoke(i, "lalala");
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
                onPoiItemSearched.invoke(i, "hhaha");
            }
        });
        poiSearch.searchPOIAsyn();
    }


}
