package search;

import Utils.CompanyfromCodeUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yyydjk.library.DropDownMenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import zjm.courier.R;

public class SearchFragment extends Fragment {
  @BindView(R.id.dropDownMenu) DropDownMenu dropDownMenu;
  @BindView(R.id.edt_express_tab) EditText mEdtExpressTab;
  @BindView(R.id.btn_ifsearch) Button mBtnSearch;

  private List<View> mPopupView;
  private String mEdtText;
  private String[] mMenu = {"请选择快递公司"};
  private LinearLayout linearLayout;
  private dropdownmenuAdapter CompanyAdapter;
  private String mCompanyName[] = {"顺丰", "百世汇通", "中通", "申通", "圆通", "韵达", "邮政平递", "EMS", "天天快递"};
  private String mShipperCode;
  private String mLogisticCode;
  private String mSelectCompany;
  showResultListener mCallback;

  public interface showResultListener {
    public void onshowResultListener(String selectcompany, String edt);
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    try {
      mCallback = (showResultListener) activity;
    } catch (ClassCastException e) {
      throw new ClassCastException(activity.toString() + "未继承showResultListener方法");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
    View v = inflater.inflate(R.layout.search_courier, container, false);
    ButterKnife.bind(this, v);
    initDropdownWindow();

    mBtnSearch.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mEdtExpressTab.getText().toString().isEmpty()) {
          Toast.makeText(getActivity(), "你还没输入快递单号", Toast.LENGTH_SHORT).show();
        } else if (mSelectCompany == null) {
          Toast.makeText(getActivity(), "你还没选择快递公司", Toast.LENGTH_SHORT).show();
        } else if (getActivity() instanceof showResultListener) {
          getEdtExpressTab();
          mLogisticCode=mEdtText;
          mShipperCode=mSelectCompany;//还是文字，需要转换成公司代码
          ((showResultListener) getActivity()).onshowResultListener(
              CompanyfromCodeUtils.Companyformat(mShipperCode), mLogisticCode);
        }
      }
    });
    return v;
  }

  private void getEdtExpressTab() {
    mEdtText = mEdtExpressTab.getText().toString();
  }

  private void initDropdownWindow() {
    final ListView ComanyMenu = new ListView(getActivity());
    ComanyMenu.setDividerHeight(0);
    CompanyAdapter = new dropdownmenuAdapter(getActivity(), Arrays.asList(mCompanyName));
    ComanyMenu.setAdapter(CompanyAdapter);
    mPopupView = new ArrayList<>();
    mPopupView.add(ComanyMenu);
    ComanyMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CompanyAdapter.setCheckItem(position);
        dropDownMenu.setTabText(position == 0 ? mMenu[0] : mCompanyName[position]);
        dropDownMenu.closeMenu();
        mSelectCompany = mCompanyName[position];
      }
    });
    linearLayout = new LinearLayout(getContext());
    linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT));
    dropDownMenu.setDropDownMenu(Arrays.asList(mMenu), mPopupView, linearLayout);
  }
}
