package home;

import Base.BaseFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.BindView;
import java.util.ArrayList;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import zjm.courier.R;

public class HomeFragment extends BaseFragment {
  private ArrayList<Fragment> fragments;
  @BindView(R.id.pbt_express_status) PageBottomTabLayout mPageBottomExpressStatus;
  @BindView(R.id.viewpager_home) ViewPager mViewPager;
  private static HomeFragment fragment = null;
  public HomeFragment(){
  }
  public static Fragment newInstance() {
    if (fragment == null) {
      synchronized (HomeFragment.class) {
        if (fragment == null) {
          fragment = new HomeFragment();
        }
      }
  }
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  void initPageBottomTablayout() {
    NavigationController navigationController = mPageBottomExpressStatus.material()
        .addItem(R.mipmap.sign, "签收")
        .addItem(R.mipmap.unsign, "未签收")
        .addItem(R.mipmap.question, "问题件")
        .build();
    navigationController.setupWithViewPager(mViewPager);
  }
  void initFragmentList(){
    Fragment questionFragment =QuestionFragment.newInstance(getContext());
    SignFragment signFragment=new SignFragment();
    UnSignFragment unSignFragment=new UnSignFragment();
    fragments=new ArrayList<Fragment>();
    fragments.add(signFragment);
    fragments.add(unSignFragment);
    fragments.add(questionFragment);
  }
  @Override protected int getLayoutResId() {
    return R.layout.home;
  }

  @Override
  protected void initView(View view, Bundle savedInstanceState) {
    FragmentManager fragmentManager=getChildFragmentManager();
    initFragmentList();
    mViewPager.setAdapter(new FragmentListAdapter(fragmentManager,fragments));
    initPageBottomTablayout();
  }
}
