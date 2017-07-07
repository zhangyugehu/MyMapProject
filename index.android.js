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

    render() {
        return (
            <View style={styles.container}>
                <Text style={styles.welcome} onPress={()=>{this._onPressed();}}>
                    获取定位信息
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

    componentDidMount() {
        this._toast("componentDidMount");
        AMap.setSuccessListener((info)=>{
            this.setState({
                successInfo:info
            })
        });

        AMap.setErrorListener((error)=>{
            this._toast(error);
        });
    }
    componentWillUnmount() {
        AMap.stopLocation();
    }

    _toast = (text) => {
        AMap.toast(text);
    }
    _onPressed() {
        this._toast("startLocation");
        AMap.startLocation();
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
    textAlign: 'left',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('MyMapProject', () => MyMapProject);
