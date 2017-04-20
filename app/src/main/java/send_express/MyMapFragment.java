package send_express;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.cloud.CloudSearch;
import zjm.courier.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMapFragment extends Fragment implements LocationSource,
    AMapLocationListener {
  private static MyMapFragment fragment = null;
  private LocationSource.OnLocationChangedListener mListener;
  private LocationManagerProxy mAMapLocationManager;
  private MapView mapView;
  private AMap aMap;
  private View mapLayout;
  private AMapLocation aMapLocation;
  private double geoLat;
  private double geoLng;
  private CloudSearch mCloudSearch;
  private LatLng latlng;

  public static Fragment newInstance() {
    if (fragment == null) {
      synchronized (MyMapFragment.class) {
        if (fragment == null) {
          fragment = new MyMapFragment();
        }
      }
    }
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (mapLayout == null) {
      Log.i("sys", "MF onCreateView() null");
      mapLayout = inflater.inflate(R.layout.fragment_my_map, container, false);
      mapView = (MapView) mapLayout.findViewById(R.id.map);
      mapView.onCreate(savedInstanceState);
    } else {
      if (mapLayout.getParent() != null) {
        ((ViewGroup) mapLayout.getParent()).removeView(mapLayout);
      }
    }
    return mapLayout;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    init();
    drawMarkers();
  }

  /**
   * 绘制系统默认的1种marker背景图片
   */
  public void drawMarkers() {
    Marker marker = aMap.addMarker(new MarkerOptions()
        .position(latlng)
        .title("快递网点\n")
        .icon(BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        .draggable(true));
    marker.showInfoWindow();// 设置默认显示一个infowinfow
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

  /**
   * 初始化AMap对象
   */
  private void init() {
    if (aMap == null) {
      aMap = mapView.getMap();
      aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
      setUpMap();
      aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {

        @Override
        public void onCameraChangeFinish(CameraPosition cameraPosition) {
          System.out.println("zoom level is:" + cameraPosition.tilt);
        }

        @Override
        public void onCameraChange(CameraPosition arg0) {

        }
      });
      aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
          Toast.makeText(getActivity(), "lat:" + latLng.latitude + "lng:" + latLng.longitude,
              Toast.LENGTH_SHORT).show();
          drawMarkers();
        }
      });
    }
  }

  /**
   * 设置一些amap的属性
   */
  private void setUpMap() {
    // 自定义系统定位小蓝点
    MyLocationStyle myLocationStyle = new MyLocationStyle();
    myLocationStyle.myLocationIcon(BitmapDescriptorFactory
        .fromResource(R.mipmap.location_marker));// 设置小蓝点的图标
    //        myLocationStyle.strokeColor(Color.TRANSPARENT);// 设置圆形的边框颜色
    //        myLocationStyle.radiusFillColor(Color.TRANSPARENT);// 设置圆形的填充颜色
    //   myLocationStyle.anchor(int,int)//设置小蓝点的锚点
    //       myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
    myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
    myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
    // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
    myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
    aMap.setMyLocationStyle(myLocationStyle);
    aMap.setLocationSource(MyMapFragment.this);// 设置定位监听
    aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
    aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    // aMap.setMyLocationType()
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
    deactivate();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  /**
   * 方法必须重写
   * map的生命周期方法
   */
  @Override
  public void onSaveInstanceState(Bundle outState) {
    Log.i("sys", "mf onSaveInstanceState");
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }

  @Override
  public void onLocationChanged(Location location) {

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }

  /**
   * 定位成功后回调函数
   */
  @Override
  public void onLocationChanged(AMapLocation aLocation) {
    if (mListener != null && aLocation != null) {
      mListener.onLocationChanged(aLocation);// 显示系统小蓝点
      if (aLocation.getAMapException().getErrorCode() == 0) {
        mListener.onLocationChanged(aLocation);// 显示系统小蓝点
        //获取位置信息
        geoLat = aMapLocation.getLatitude();
        geoLng = aMapLocation.getLongitude();
        latlng = new LatLng(37.543164, 115.590439);
      }
    }
  }

  /**
   * 激活定位
   */
  @Override
  public void activate(OnLocationChangedListener listener) {
    mListener = listener;
    if (mAMapLocationManager == null) {
      mAMapLocationManager = LocationManagerProxy.getInstance(getActivity());
      /*
       * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
      mAMapLocationManager.requestLocationUpdates(
          LocationProviderProxy.AMapNetwork, 2000, 10, this);
    }
  }

  /**
   * 停止定位
   */
  @Override
  public void deactivate() {
    mListener = null;
    if (mAMapLocationManager != null) {
      mAMapLocationManager.removeUpdates(this);
      mAMapLocationManager.destory();
    }
    mAMapLocationManager = null;
  }
}
