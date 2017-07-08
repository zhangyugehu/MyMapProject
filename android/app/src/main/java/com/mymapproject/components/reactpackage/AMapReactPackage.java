package com.mymapproject.components.reactpackage;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.mymapproject.components.module.AMapModule;
import com.mymapproject.components.view.manager.AMapViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhangyugehu
 * @version V1.0
 * @data 2017/07/07
 */

public class AMapReactPackage implements ReactPackage {

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Arrays.<NativeModule>asList(
                new AMapModule(reactContext)
        );
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Arrays.<ViewManager>asList(
                new AMapViewManager(reactContext)
        );
    }
}
