package com.mymapproject.module;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Created by hth on 2017/7/4.
 */

public class AMapLocationModule extends com.facebook.react.bridge.ReactContextBaseJavaModule implements PoiSearch.OnPoiSearchListener {

    private ReactApplicationContext mReactContext;

    public AMapLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "AMapLocationModule";
    }

    @ReactMethod
    public void logd(String text){
        Log.d("AMapLocationModule", "Toast: " + text);
    }
    /**
     * 开始进行poi搜索
     */
    @ReactMethod
    protected void doSearchQuery(String keyWord) {
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(mReactContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.d("AMapLocationModule", "onPoiSearched: ");
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.d("AMapLocationModule", "onPoiItemSearched: ");
    }
}
