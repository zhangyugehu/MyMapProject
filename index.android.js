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
  View, NativeModules, DeviceEventEmitter
} from 'react-native';

let AMap = NativeModules.AMapLocationModule;

export default class MyMapProject extends Component {

    constructor(props){
        super(props);
        this.state={
            successInfo:"",
            errorInfo:""
        }
    }

    componentDidMount() {
        // AMap.doSearchQuery("高德", (index, poiResult)=>{
        //     console.log(index + "===" + poiResult);
        // }, (i, poiItem)=>{
        //     console.log(index + "===" + poiItem);
        // });
        _toast("componentDidMount");
        AMap.startLocation((locationType, latitude, longitude, accuracy, time)=>{
            this.setState({
                successInfo:locationType + "; " + latitude + "; " + longitude + "; " + accuracy + "; " + time
            })
            // console.log(locationType + "; " + latitude + "; " + longitude + "; " + accuracy + "; " + time);
        },
            (error)=>{
                _toast(error);
            })
    }
    componentWillUnmount() {
        AMap.stopLocation();
    }

    _toast = (text) => {
        AMap.toast(text);
    }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>

        </Text>
        <Text style={styles.instructions}>
            {
                `${this.state.successInfo == ""?"":this.state.successInfo}
                ${this.state.errorInfo==""?"":this.state.errorInfo}`
            }
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
