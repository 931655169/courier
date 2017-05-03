package home;

import Base.BaseFragment;
import Entity.ExpressDetail;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import zjm.courier.MainActivity;
import zjm.courier.R;

public class QuestionFragment extends BaseFragment {
  private RecyclerviewAdapter mQuestionExpressItemAdapter;
  private List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
  @BindView(R.id.recycler_all) RecyclerView recyclerViewView;
  private static QuestionFragment questionFragment = null;
  showResultListener mCallback;

  public static Fragment newInstance(Context context) {
    if (questionFragment == null) {
      synchronized (HomeFragment.class) {
        if (questionFragment == null) {
          questionFragment = new QuestionFragment();
        }
      }
    }
    return questionFragment;
  }

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.home_all, container, false);
    ButterKnife.bind(this,v);
    initAdapter();
    initRecycler();
    initdb();
    return v;
  }

  @Override protected int getLayoutResId() {
    return R.layout.home_all;
  }

  private void initAdapter() {
    mQuestionExpressItemAdapter = new RecyclerviewAdapter(expressDetailList);
  }

  private void initRecycler() {
    recyclerViewView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerViewView.setAdapter(mQuestionExpressItemAdapter);
    recyclerViewView.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));
    mQuestionExpressItemAdapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickListener() {
      @Override public void onItemClick(View view, int position) {
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
      expressDetailList.addAll(DataSupport.where("State == ?","4").find(ExpressDetail.class));
    }
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
