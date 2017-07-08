/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  View,
} from 'react-native';

import AMapView from 'js/components/AMapView'

export default class MyMapProject extends Component {

    render() {
        return (
            <View style={styles.container}>
                <AMapView refs="aMapView"/>
            </View>
        );
    }

    componentDidMount() {
        this.refs.aMapView.startLocationNative();
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
});

AppRegistry.registerComponent('MyMapProject', () => MyMapProject);
