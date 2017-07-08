package com.mymapproject.components.view.manager;

import com.amap.api.maps2d.model.LatLng;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.mymapproject.components.view.RCTAMapView;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * @author zhangyugehu
 * @version V1.0
 * @data 2017/07/07
 */

public class AMapViewManager extends ViewGroupManager<RCTAMapView> {
    private final String NAME = "RCTAMapView";

    private static WeakReference<ReactApplicationContext> mContext;

    public AMapViewManager(ReactApplicationContext mContext) {
        this.mContext = new WeakReference<ReactApplicationContext>(mContext);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected RCTAMapView createViewInstance(ThemedReactContext reactContext) {
        return new RCTAMapView(reactContext);
    }

    @Nullable
    @Override
    public Map getExportedCustomBubblingEventTypeConstants() {
        Map<String, Map<String, String>> eventMap = new HashMap<>();
        return eventMap;
    }

    /**
     *
     * @param id viewId
     * @param eventName Android原生事件名
     * @param event 返回的参数
     */
    public static void sendEvent(int id, String eventName, @Nullable WritableMap event) {
        mContext.get().getJSModule(RCTEventEmitter.class)
                .receiveEvent(id, eventName, event);
    }
//
//    public void showLocation(RCTAMapView mapView){
//        if(mapView == null){ return; }
//        mapView.setMyLocationEnabled(true);
//    }
//
//    public void addMarker(RCTAMapView mapView, long lat, long lng){
//        LatLng latLng = new LatLng(lat, lng);
//        mapView.addMarkersToMap(new LatLng(lat, lng));
//    }
}
