package home;

import Entity.ExpressDetail;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import zjm.courier.MainActivity;
import zjm.courier.R;

public class SignFragment extends Fragment {
  @BindView(R.id.recycler_sign) RecyclerView recyclerViewView;
  private RecyclerviewAdapter mSignExpressItemAdapter;
  private Context mContext;
  private List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
  showResultListener mCallback;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.home_sign, container, false);
    ButterKnife.bind(this, v);
    initRecycler();
    initdb();
    return v;
  }

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

  private void initRecycler() {
    mSignExpressItemAdapter = new RecyclerviewAdapter(expressDetailList);
    recyclerViewView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerViewView.setAdapter(mSignExpressItemAdapter);
    recyclerViewView.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));
    mSignExpressItemAdapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {
        if (getActivity() instanceof showResultListener) {
          ((MainActivity) getActivity()).onshowResultListener(
              expressDetailList.get(position).getShipperCode(),
              expressDetailList.get(position).getLogisticCode());
        }
      }
    });
  }

  void initdb() {
    if (DataSupport.findAll(ExpressDetail.class) != null) {
      expressDetailList.clear();
      expressDetailList.addAll(DataSupport.where("State == ?", "3").find(ExpressDetail.class));
    }
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
