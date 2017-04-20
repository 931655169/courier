package send_express;

import Base.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import zjm.courier.R;

public class SendExpressFragment extends BaseFragment {
  @BindView(R.id.send_map) MapView mMapView;
  private AMap aMap;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.send_express, container, false);
    ButterKnife.bind(this, v);
    mMapView.onCreate(savedInstanceState);
    if (aMap == null) {
      aMap = mMapView.getMap();
    }
    return v;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
    mMapView.onDestroy();
  }

  @Override
  public void onResume() {
    super.onResume();
    //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
    mMapView.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
    //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
    mMapView.onPause();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
    mMapView.onSaveInstanceState(outState);
  }

  @Override protected int getLayoutResId() {
    return R.layout.send_express;
  }
}
