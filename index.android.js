/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import AMapLocation from 'js/modules/AMapLocation'

export default class MyMapProject extends Component {

    componentDidMount() {
        this.listener = AMapLocation.addEventListener(this._onLocationChanged);    // 注册监听
        AMapLocation.startLocation(); // 开启定位
    }
    componentWillUnmount() {
        AMapLocation.destory();
        this.listener.clear();
    }

    _onLocationChanged = (data)=> {
        if (data && data.result) {
            console.log(data.address);
        }
    };

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          To get started, edit index.android.js
        </Text>
        <Text style={styles.instructions}>
          Double tap R on your keyboard to reload,{'\n'}
          Shake or press menu button for dev menu
        </Text>
      </View>
    );
  }


}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('MyMapProject', () => MyMapProject);
