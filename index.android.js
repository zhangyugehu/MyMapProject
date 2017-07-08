/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  View, Text
} from 'react-native';

import AMapView from './js/components/AMapView'

export default class MyMapProject extends Component {

    render() {
        return (
            <View style={styles.container}>
                <Text>高德地图</Text>
                <AMapView
                    style={styles.mapStyle}
                    ref="aMapView"/>
            </View>
        );
    }

    componentDidMount() {
        this.refs.aMapView.startLocationNative();
        // this.refs.aMapView.setCameraNative();
    }

    componentWillUnmount() {
        // AMap.stopLocation();
    }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
    mapStyle:{
        width:400,
        height:400,
        backgroundColor:"#f00"
    }
});

AppRegistry.registerComponent('MyMapProject', () => MyMapProject);
