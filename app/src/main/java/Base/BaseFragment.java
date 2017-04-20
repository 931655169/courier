package Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
  private Activity activity;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResId(), container, false);
    ButterKnife.bind(this, view);
    initView(view, savedInstanceState);
    return view;
  }

  protected abstract int getLayoutResId();

  protected void initView(View view, Bundle savedInstanceState) {
  }
}