package home;

import Entity.ExpressDetail;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import zjm.courier.MainActivity;
import zjm.courier.R;

public class UnSignFragment extends Fragment {
  private RecyclerviewAdapter mUnsignExpressItemAdapter;
  private List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
  showResultListener mCallback;

  @BindView(R.id.recycler_unsign) RecyclerView recyclerViewView;

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.home_unsign, container, false);
    ButterKnife.bind(this, v);
    initRecycler();
    initdb();
    return v;
  }

  private void initRecycler() {
    mUnsignExpressItemAdapter = new RecyclerviewAdapter(expressDetailList);
    recyclerViewView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerViewView.setAdapter(mUnsignExpressItemAdapter);
    recyclerViewView.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));

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

  void initdb() {
    if (DataSupport.findAll(ExpressDetail.class) != null) {
      expressDetailList.clear();
      expressDetailList.addAll(DataSupport.where("State == ?","2").find(ExpressDetail.class));
    }
    mUnsignExpressItemAdapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        if (getActivity() instanceof showResultListener){
          ((MainActivity) getActivity()).onshowResultListener(
              expressDetailList.get(position).getShipperCode(),
              expressDetailList.get(position).getLogisticCode());
        }
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
