package home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class FragmentListAdapter extends FragmentPagerAdapter{
  private ArrayList<Fragment> fragments;
  public FragmentListAdapter(FragmentManager fm) {
    super(fm);
  }

  public FragmentListAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
    super(fm);
    this.fragments=fragments;
  }
  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }
}
