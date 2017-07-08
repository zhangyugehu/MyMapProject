package com.mymapproject.components.view.manager;

import com.amap.api.maps2d.AMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
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

    private WeakReference<ReactApplicationContext> mContext;

    public AMapViewManager(ReactApplicationContext mContext) {
        this.mContext = new WeakReference<>(mContext);
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
}
