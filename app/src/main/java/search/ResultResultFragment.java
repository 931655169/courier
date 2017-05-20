package search;

import Base.BaseFragment;
import Entity.ExpressDetail;
import Entity.TracesBean;
import Network.ApiStores;
import Service.ApiService;
import Service.KdniaoTrackQueryAPI;
import Utils.CompanyfromCodeUtils;
import Utils.StatefromStateCode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import home.RecyclerviewAdapter;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import org.litepal.crud.DataSupport;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zjm.courier.MainActivity;
import zjm.courier.R;

public class ResultResultFragment extends BaseFragment {
  @BindView(R.id.recycler_express_trace) RecyclerView mRecyclerViewExpressTrace;
  @BindView(R.id.txt_express_name) TextView mTxtExpressName;
  @BindView(R.id.txt_express_status) TextView mTxtExpressStatus;
  @BindView(R.id.txt_express_company)TextView mTxtExpressCompany;
  @BindView(R.id.image_express)ImageView mImageExpress;
  private ExpressDetail expressDetail;
  private List<TracesBean> tracebeanlist = new ArrayList<TracesBean>();
  private RecyclerviewAdapter mExpressItemAdapter;
  private TraceAdapter mTraceAdapter;
  private String mLogisticCode;
  private Handler hander;
  private static ResultResultFragment fragment = null;
  public ResultResultFragment(){
  }
  public static Fragment newInstance() {
    if (fragment == null) {
      synchronized (ResultResultFragment.class) {
        if (fragment == null) {
          fragment = new ResultResultFragment();
        }
      }
    }
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutResId() {
    return R.layout.express_trace;
  }

  @Override
  protected void initView(View view, Bundle saveInstanceState) {
    handleMessage();
    initdb();
    initRecyclerview();
  }

  private void initRecyclerview() {
    mTraceAdapter = new TraceAdapter(tracebeanlist);
    mRecyclerViewExpressTrace.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerViewExpressTrace.setAdapter(mTraceAdapter);
    mRecyclerViewExpressTrace.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));
  }
  public void initdb() {
    if (mLogisticCode!=null&&DataSupport.where("LogisticCode = ?",mLogisticCode).find(ExpressDetail.class)!=null) {
      List<ExpressDetail> expressDetails= DataSupport.where("LogisticCode = ?",mLogisticCode).limit(1).find(ExpressDetail.class);
      expressDetail=expressDetails.get(0);
      mTxtExpressName.setText(expressDetail.getLogisticCode());
      mTxtExpressStatus.setText(StatefromStateCode.format(expressDetail.getState()));
      mTxtExpressCompany.setText(CompanyfromCodeUtils.Codeformat(expressDetail.getShipperCode()));
      if (expressDetail.isSuccess()){
        tracebeanlist=DataSupport.findAll(TracesBean.class);
        mTraceAdapter=new TraceAdapter(tracebeanlist);
        mImageExpress.setImageResource(R.mipmap.icon_blue);
      }
      if (!expressDetail.isSuccess()){
        tracebeanlist.clear();
      }
    }
  }

  void post(String mShipperCode, String mLogisticCode) {
    KdniaoTrackQueryAPI k = new KdniaoTrackQueryAPI();
    try {
      sendPost(mShipperCode, mLogisticCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void sendPost(String ShipperCode, final String LogisticCode) throws Exception {
    String requestData = "{'OrderCode':'','ShipperCode':'"
        + ShipperCode
        + "','LogisticCode':'"
        + LogisticCode
        + "'}";
    String dataSign = KdniaoTrackQueryAPI.encrypt(requestData, KdniaoTrackQueryAPI.AppKey, "UTF-8");
    ApiStores apiStores = ApiService.createApiStores();
    Call<ExpressDetail> call = apiStores.getExpressDetail(
        KdniaoTrackQueryAPI.urlEncoder(dataSign, "UTF-8"), "2", KdniaoTrackQueryAPI.EBusinessID,
        KdniaoTrackQueryAPI.urlEncoder(requestData, "UTF-8"), "1002"
    );
    call.enqueue(new Callback<ExpressDetail>() {
      @Override
      public void onResponse(Call<ExpressDetail> call, final Response<ExpressDetail> response) {
        response.body().saveOrUpdate("LogisticCode=?",response.body().getLogisticCode());
        if (response.body().isSuccess()) {
          if (response.body().getReason()==null){
            DataSupport.deleteAll(TracesBean.class);
            for (int i = 0; i < response.body().getTracesX().size(); i++) {
              TracesBean tracesBean = new TracesBean();
              tracesBean.setAcceptStation(response.body().getTracesX().get(i).getAcceptStation());
              tracesBean.setAcceptTime(response.body().getTracesX().get(i).getAcceptTime());
              tracesBean.save();
            }
            initdb();
            initRecyclerview();
            Toast.makeText(getActivity(),"获取成功",Toast.LENGTH_SHORT).show();
          }
          if (response.body().getReason()!=null){
            Toast.makeText(getActivity(),response.body().getReason(),Toast.LENGTH_SHORT).show();
          }
        }
        if (!response.body().isSuccess()){
          Toast.makeText(getActivity(),response.body().getReason(),Toast.LENGTH_SHORT).show();
        }
      }
      @Override public void onFailure(Call<ExpressDetail> call, Throwable t) {
        Log.d("调用", t.getMessage() + "网络请求失败");
        Toast.makeText(getContext(),"请检查网络",Toast.LENGTH_SHORT).show();
      }
    });
  }

  void handleMessage() {
    MainActivity activity = (MainActivity) getActivity();
    hander = activity.handler;
    activity.setSendResultFragmentMessage(new MainActivity.SendResultFragmentMessage() {
      @Override public void getSearchMessage(String ShipperCode, String LogisticCode) {
        post(ShipperCode, LogisticCode);
        mLogisticCode=LogisticCode;
      }
    });
  }
  public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
      this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
        RecyclerView.State state) {
      if (parent.getChildLayoutPosition(view) != 0) outRect.top = space;
    }
  }
}
