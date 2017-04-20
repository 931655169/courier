package Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import zjm.courier.R;

public class BaseFragmentLayout {
  public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
    View v=inflater.inflate(R.layout.fragment_layout,container,false);
    return v;
  }
}
