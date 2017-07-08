
# 集成原生高德地图显示

## step 1

> 申请API_KEY

1、注册高德开发者 
2、创建应用 
3、获取API key 
申请地址：http://lbs.amap.com/


## step 2

> 实现原生部分

1、权限

````xml
/地图包、搜索包需要的基础权限

<!--允许程序打开网络套接字-->
<uses-permission android:name="android.permission.INTERNET" />  
<!--允许程序设置内置sd卡的写权限-->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
<!--允许程序获取网络状态-->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!--允许程序访问WiFi网络信息-->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!--允许程序读写手机状态和身份-->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />    
<!--允许程序访问CellID或WiFi热点来获取粗略的位置-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
````

2、在application标签中加入API_KEY

````xml
 <meta-data
   android:name="com.amap.api.v2.apikey"
   android:value="${AMAP_KEY}"/>
````

3、导入sdk jar包或者Android Studio可直接计入依赖

````gradle
dependencies {
    ......
    compile 'com.amap.api:map2d:2.9.1'
    compile 'com.amap.api:location:3.1.0'
    compile 'com.amap.api:search:3.5.0'
}
````

4、自定义用于展示的View
> 1、需要可以显示当前位置
> 2、需要可以添加位置标签




