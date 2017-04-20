package send_express;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import send_express.lib.LocationTask;
import send_express.lib.OnLocationGetListener;
import send_express.lib.PositionEntity;
import send_express.lib.RegeocodeTask;
import send_express.lib.RouteTask;
import send_express.lib.Utils;
import zjm.courier.R;

public class MapActivity extends Activity implements AMap.OnCameraChangeListener,
    AMap.OnMapLoadedListener, OnLocationGetListener, View.OnClickListener,
    RouteTask.OnRouteCalculateListener {
  @BindView(R.id.mapview) MapView mMapView;
  @BindView(R.id.address_text) TextView mAddressTextView;
  @BindView(R.id.destination_button) Button mDestinationButton;
  @BindView(R.id.cancel_button) Button mCancelButton;
  @BindView(R.id.location_image) ImageView mLocationImage;
  @BindView(R.id.routecost_text) TextView mRouteCostText;
  @BindView(R.id.destination_text) TextView mDesitinationText;
  @BindView(R.id.destination_container) LinearLayout mDestinationContainer;
  @BindView(R.id.fromto_container) LinearLayout mFromToContainer;
  private AMap mAmap = null;
  private Marker mPositionMark;
  private LatLng mStartPosition;
  private RegeocodeTask mRegeocodeTask;
  private LocationTask mLocationTask;
  private boolean mIsFirst = true;
  private boolean mIsRouteSuccess = false;

  public interface OnGetLocationListener {
    public void getLocation(String locationAddress);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map);
    ButterKnife.bind(this);
    if (mMapView != null) {
      mMapView.onCreate(savedInstanceState);
    }
    if (mAmap == null) {
      mAmap = mMapView.getMap();
    }
    init(savedInstanceState);
    mLocationTask = LocationTask.getInstance(getApplicationContext());
    mLocationTask.setOnLocationGetListener(this);
    mRegeocodeTask = new RegeocodeTask(getApplicationContext());
    RouteTask.getInstance(getApplicationContext())
        .addRouteCalculateListener(this);
  }

  private void init(Bundle savedInstanceState) {
    mAmap.getUiSettings().setZoomControlsEnabled(false);
    mAmap.setOnMapLoadedListener(this);
    mAmap.setOnCameraChangeListener(this);
    mDestinationButton.setOnClickListener(this);
    mDesitinationText.setOnClickListener(this);
    mLocationImage.setOnClickListener(this);
  }

  private void hideView() {
    mFromToContainer.setVisibility(View.GONE);
    mDestinationButton.setVisibility(View.GONE);
    mCancelButton.setVisibility(View.GONE);
  }

  private void showView() {
    mFromToContainer.setVisibility(View.VISIBLE);
    mDestinationButton.setVisibility(View.VISIBLE);
    if (mIsRouteSuccess) {
      mCancelButton.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onCameraChange(CameraPosition arg0) {
    hideView();
  }

  @Override
  public void onCameraChangeFinish(CameraPosition cameraPosition) {
    showView();
    mStartPosition = cameraPosition.target;
    mRegeocodeTask.setOnLocationGetListener(this);
    mRegeocodeTask
        .search(mStartPosition.latitude, mStartPosition.longitude);
    if (mIsFirst) {
      Utils.addEmulateData(mAmap, mStartPosition);
      if (mPositionMark != null) {
        mPositionMark.setToTop();
      }
      mIsFirst = false;
    }
  }

  /**
   * 方法必须重写
   */
  @Override
  protected void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  /**
   * 方法必须重写
   */
  @Override
  protected void onPause() {
    super.onPause();
    mMapView.onPause();
  }

  /**
   * 方法必须重写
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mMapView.onSaveInstanceState(outState);
  }

  /**
   * 方法必须重写
   */
  @Override
  protected void onDestroy() {
    super.onDestroy();
    Utils.removeMarkers();
    mMapView.onDestroy();
    mLocationTask.onDestroy();
    RouteTask.getInstance(getApplicationContext()).removeRouteCalculateListener(this);
  }

  @Override
  public void onMapLoaded() {
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.setFlat(true);
    markerOptions.anchor(0.5f, 0.5f);
    markerOptions.position(new LatLng(0, 0));
    markerOptions
        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
            .decodeResource(getResources(),
                R.mipmap.icon_loaction_start)));
    mPositionMark = mAmap.addMarker(markerOptions);

    mPositionMark.setPositionByPixels(mMapView.getWidth() / 2,
        mMapView.getHeight() / 2);
    mLocationTask.startSingleLocate();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.destination_button:
        Intent intent = new Intent(this, DestinationActivity.class);
        startActivity(intent);
        break;
      case R.id.location_image:
        mLocationTask.startSingleLocate();
        break;
      case R.id.destination_text:
        Intent destinationIntent = new Intent(this,
            DestinationActivity.class);
        startActivity(destinationIntent);
        break;
    }
  }

  @Override
  public void onLocationGet(PositionEntity entity) {
    // todo 这里在网络定位时可以减少一个逆地理编码
    mAddressTextView.setText(entity.address);
    RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);

    mStartPosition = new LatLng(entity.latitue, entity.longitude);
    CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
        mStartPosition, mAmap.getCameraPosition().zoom);
    mAmap.animateCamera(cameraUpate);
  }

  @Override
  public void onRegecodeGet(PositionEntity entity) {
    mAddressTextView.setText(entity.address);
    entity.latitue = mStartPosition.latitude;
    entity.longitude = mStartPosition.longitude;
    RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
    RouteTask.getInstance(getApplicationContext()).search();
  }

  @Override
  public void onRouteCalculate(float cost, float distance, int duration) {
    mDestinationContainer.setVisibility(View.VISIBLE);
    mIsRouteSuccess = true;
    mRouteCostText.setVisibility(View.VISIBLE);
    mDesitinationText.setText(RouteTask
        .getInstance(getApplicationContext()).getEndPoint().address);
    mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm,用时%d分", cost,
        distance, duration));
    mDestinationButton.setText("我要用车");
    mCancelButton.setVisibility(View.VISIBLE);
    mDestinationButton.setOnClickListener(null);
  }
}
