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
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mancj.materialsearchbar.MaterialSearchBar;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import search.nicespinner.NiceSpinner;
import zjm.courier.MainActivity;
import zjm.courier.R;

public class SearchFragment extends Fragment {
  @BindView(R.id.btn_ifsearch) Button mBtnSearch;
  @BindView(R.id.searchBar) MaterialSearchBar materialSearchBar;
  @BindView(R.id.nice_spinner) NiceSpinner niceSpinner;
  private String mEdtText;
  private String mShipperCode;
  private String mLogisticCode;
  private String mSelectCompany;
  showResultListener mCallback;
  private List<String> dataset =
      new LinkedList<>(Arrays.asList("请选择相应的快递公司","顺丰", "百世汇通", "中通", "申通", "圆通", "韵达", "邮政平邮", "EMS", "天天快递"));

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
    nineSpinner();

    mBtnSearch.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (materialSearchBar.getText().toString().isEmpty()) {
          Toast.makeText(getActivity(), "你还没输入快递单号", Toast.LENGTH_SHORT).show();
        } else if (mSelectCompany == null) {
          Toast.makeText(getActivity(), "你还没选择快递公司", Toast.LENGTH_SHORT).show();
        } else if (getActivity() instanceof showResultListener) {
          getEdtExpressTab();
          mLogisticCode = mEdtText;
          mShipperCode = mSelectCompany;//还是文字，需要转换成公司代码
          ((MainActivity) getActivity()).onshowResultListener(
              CompanyfromCodeUtils.Companyformat(mShipperCode), mLogisticCode);
        }
      }
    });
    return v;
  }

  private void getEdtExpressTab() {
    mEdtText = materialSearchBar.getText().toString();
  }

  private void nineSpinner(){
    niceSpinner.attachDataSource(dataset);
    niceSpinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position==0){
          mSelectCompany=null;
        }else {
          mSelectCompany=dataset.get(position);
        }
      }
    });
  }
}
