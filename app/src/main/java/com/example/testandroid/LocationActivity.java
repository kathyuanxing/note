package com.example.testandroid;

/**
 * Created by kathy on 2015/12/22.
 */

import android.app.Activity;
import android.view.animation.Interpolator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Handler;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View.OnClickListener;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMapLongClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.example.util.Constants;
import com.example.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置
 *
 */
public class LocationActivity extends Activity implements LocationSource,
        AMapLocationListener, OnClickListener, OnMapClickListener,
        OnMapLongClickListener, OnGeocodeSearchListener, InfoWindowAdapter,
        OnMapLoadedListener, OnMarkerClickListener, TextWatcher,
        OnPoiSearchListener {

    private AMap aMap;
    private MapView mapView;
    private UiSettings mUiSettings;
    private Marker marker;
    private Marker mMarker;
    private MediaPlayer mPlayer;
    private OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;
    private Handler handler;// 接收请求数据的handler
    private TextView regeocodeTextView;
    private GeocodeSearch geocoderSearch;
    List<LatLng> pointList = new ArrayList<LatLng>();
    LatLng point = null;
    private Button locationSendButton;
    private Boolean isShowLocation = false;
    private String location;
    private AMapLocation myLocation;

    private AutoCompleteTextView searchText;// 输入搜索关键字
    private String keyWord = "";// poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    private EditText editCity;// 城市
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 设置应用单独的地图存储目录，在下载离线地图或初始化地图时设置
        MapsInitializer.sdcardDir = getSdCacheDir(this);
        setContentView(R.layout.activity_location);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        if (location != null) {
            isShowLocation = true;
        }
        init();
        // initHandler();// 初始化handler
        if (isShowLocation) {
            showLocation();
        }
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setupMap();
        }
        regeocodeTextView = (TextView) findViewById(R.id.regeocode_textView);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        locationSendButton = (Button) findViewById(R.id.location_send_button);
        locationSendButton.setOnClickListener(this);
        if (isShowLocation) {
            locationSendButton.setVisibility(View.GONE);
        }
        mPlayer = MediaPlayer.create(this, R.raw.newdatatoast);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // mp.release();
            }
        });
    }

    /**
     * 设置地图
     */
    private void setupMap() {
        if (!isShowLocation) {
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.WUHAN,
                    18));
            // 自定义系统定位小蓝点
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
            myLocationStyle.strokeColor(Color.BLUE);// 设置圆形的边框颜色
            // myLocationStyle.radiusFillColor(color)//设置圆形的填充颜色
            // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
            myLocationStyle.strokeWidth(2);// 设置圆形的边框粗细
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.setLocationSource(this);// 设置定位监听
            aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
            // aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mUiSettings.setScaleControlsEnabled(true);
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
    }

    @Override
    public void onMapLoaded() {
        // TODO Auto-generated method stub
    }

    // 显示位置信息
    private void showLocation() {
        double latitude = Double.parseDouble(location.substring(1,
                location.indexOf(",")));
        double longitude = Double.parseDouble(location.substring(
                location.indexOf(",") + 1, location.length() - 1));
        getAddress(new LatLonPoint(latitude, longitude));
        LatLng point = new LatLng(latitude, longitude);
        mMarker = aMap.addMarker(new MarkerOptions().position(point).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
    }

    /**
     * 获取map 缓存和读取目录
     *
     * @param context
     * @return
     */
    private String getSdCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            java.io.File fExternalStorageDirectory = Environment
                    .getExternalStorageDirectory();
            java.io.File autonaviDir = new java.io.File(
                    fExternalStorageDirectory, "autonavi");
            boolean result = false;
            if (!autonaviDir.exists()) {
                result = autonaviDir.mkdir();
            }
            java.io.File minimapDir = new java.io.File(autonaviDir,
                    "mini_mapv3");
            if (!minimapDir.exists()) {
                result = minimapDir.mkdir();
            }
            return minimapDir.toString() + "/";
        } else {
            return null;
        }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
    }

    @Override
    public void onPoiSearched(PoiResult arg0, int arg1) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.onLocationChanged(location);// 显示系统小蓝点
        }
        myLocation = location;
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener arg0) {
        // TODO Auto-generated method stub
        mListener = arg0;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            mAMapLocationManager.setGpsEnable(true);
			/*
			 * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是5000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationUpdates(
                    LocationProviderProxy.AMapNetwork, 5000, 0, this);
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destory();
        }
        mAMapLocationManager = null;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mapView.onDestroy();
        deactivate();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.location_send_button:
                if (point == null) {
                    if (myLocation == null) {
                        ToastUtils.show(this, "没有可用位置");
                        return;
                    }
                    point = new LatLng(myLocation.getLatitude(),
                            myLocation.getLongitude());
                }
                String location = "(" + point.latitude + "," + point.longitude
                        + ")";
                Intent intent = new Intent();
                intent.putExtra("location", location);
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onMapLongClick(LatLng point) {
        // TODO Auto-generated method stub
        mUiSettings.setScrollGesturesEnabled(false);
        if (pointList.size() != 0) {
            Polyline polyline = aMap.addPolyline((new PolylineOptions())
                    .add(pointList.get(pointList.size() - 1), point)
                    .width(5).color(Color.BLUE));
        }
        pointList.add(point);
    }

    @Override
    public void onMapClick(LatLng point) {
        // TODO Auto-generated method stub
        if (isShowLocation) {
            return;
        }
        if (marker != null) {
            marker.remove();
        }
        getAddress(new LatLonPoint(point.latitude, point.longitude));
        marker = aMap.addMarker(new MarkerOptions().position(point)
                .icon(
                        BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mPlayer.start();
        // jumpPoint(marker);
        this.point = point;
    }

    /**
     * marker跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        final LatLng position = marker.getPosition();
        Point startPoint = proj.toScreenLocation(position);
        startPoint.offset(0, -20);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1000;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * position.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * position.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.isInfoWindowShown()) {
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
        return false;
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    @Override
    public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        // TODO Auto-generated method stub
        if (rCode == 0) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                String addressName = result.getRegeocodeAddress()
                        .getFormatAddress() + "附近";
                if (isShowLocation) {
                    mMarker.setTitle("位置信息：");
                    mMarker.setSnippet(addressName);
                    mMarker.showInfoWindow();
                } else {
                    regeocodeTextView.setText(addressName);
                    regeocodeTextView.setVisibility(View.VISIBLE);
                }
            } else {

            }
        }
    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}