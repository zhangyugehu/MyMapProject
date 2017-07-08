import React, { Component } from 'react';
import {
    NativeModules,
    requireNativeComponent,
    findNodeHandle

} from 'react-native';
const AMapModule = NativeModules.AMapModule;
const MapView = requireNativeComponent('RCTAMapView', AMapView);

export default class AMapView extends Component {
    static defaultProps = {};

    render() {
        return (
            <MapView
                {...this.props}
            />
        );
    }

    startLocationNative = () => {
        AMapModule.setMyLocationEnabled(findNodeHandle(this));
    }

    setCameraNative =() =>{
        // AMapModule.setCamera(findNodeHandle(this), 39.90403, 116.407525)
    }
}