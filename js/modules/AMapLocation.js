
import {NativeModules, DeviceEventEmitter} from 'react-native';

const location = NativeModules.AMapLocation;
export default class AMapLocation {
    // 开启定位
    static startLocation() {
        location.startLocation();
    }
    // 关闭定位
    static destory() {
        location.destory();
    }
    // 注册定位回调监听
    static addEventListener(handler) {
        const listener = DeviceEventEmitter.addListener(
            'onLocationChanged',   // 与模块中的eventName保持一致
            handler       // 回调函数，在此函数接收定位信息
        );
        return listener;
    }
}