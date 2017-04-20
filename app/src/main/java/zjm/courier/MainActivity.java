package zjm.courier;

import Entity.ExpressDetail;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import home.FragmentListAdapter;
import home.HomeFragment;
import java.util.ArrayList;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import search.ResultResultFragment;
import search.SearchFragment;
import send_express.DestinationActivity;
import send_express.MapActivity;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, SearchFragment.showResultListener {
  private ArrayList<Fragment> mFragmentArrayList;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.drawer_layout) DrawerLayout drawer;
  @BindView(R.id.nav_view) NavigationView navigationView;
  //@BindView(R.id.fab) FloatingActionButton fab;
  @BindView(R.id.pbt_tab) PageBottomTabLayout mPageButtonTalayoutButtom;
  @BindView(R.id.viewpager_tablayout_buttom) ViewPager mViewPagerTablayout;
  private FragmentListAdapter fragmentPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fresco.initialize(this);//需要要senContentView前初始化
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    initActionBar();
    initPageTabLayout();
    initViewPager();
    LitePal.initialize(this);
  }

  void initActionBar() {
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);
  }

  //void initFloatingActionButton() {
  //  fab.setOnClickListener(new View.OnClickListener() {
  //    @Override
  //    public void onClick(View view) {
  //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
  //          .setAction("Action", null).show();
  //    }
  //  });
  //}

  void initPageTabLayout() {
    NavigationController navigationController = mPageButtonTalayoutButtom.material()
        .addItem(R.mipmap.home_unselect, "首页")
        .addItem(R.mipmap.search_unselect, "查快递")
        .addItem(R.mipmap.car_unselect, "历史查询")
        .build();
    navigationController.setupWithViewPager(mViewPagerTablayout);
  }

  void initViewPager() {
    Fragment Homefragment = HomeFragment.newInstance();
    SearchFragment searchFragment = new SearchFragment();
    ResultResultFragment resultResultFragment = new ResultResultFragment();
    mFragmentArrayList = new ArrayList<Fragment>();
    mFragmentArrayList.add(Homefragment);
    mFragmentArrayList.add(searchFragment);
    mFragmentArrayList.add(resultResultFragment);
    FragmentManager fragmentManager = this.getSupportFragmentManager();
    fragmentPagerAdapter = new FragmentListAdapter(fragmentManager, mFragmentArrayList);
    mViewPagerTablayout.setAdapter(fragmentPagerAdapter);
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      DataSupport.deleteAll(ExpressDetail.class);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_histroy) {
      mViewPagerTablayout.setCurrentItem(2);
    } else if (id == R.id.nav_search) {
      Intent intent = new Intent(MainActivity.this, MapActivity.class);
      startActivity(intent);

    } else if (id == R.id.nav_home) {
      mViewPagerTablayout.setCurrentItem(0);
    } else if (id == R.id.nav_back) {
      Toast.makeText(this, "By 石成", Toast.LENGTH_SHORT).show();
    }

    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onshowResultListener(String ShipperCode,String OrderCode ) {
    if (ShipperCode!=null && OrderCode!=null) {
      mViewPagerTablayout.setCurrentItem(3);
    }
  }
}
