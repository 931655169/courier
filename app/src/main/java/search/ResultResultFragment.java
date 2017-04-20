package search;

import Base.BaseFragment;
import Entity.ExpressDetail;
import Entity.TracesBean;
import Service.KdniaoTrackQueryAPI;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyRecyclerAdapter;
import home.RecyclerviewAdapter;
import java.util.ArrayList;
import java.util.List;
import org.litepal.crud.DataSupport;
import zjm.courier.R;

public class ResultResultFragment extends BaseFragment {
  @BindView(R.id.recycler_express_trace) RecyclerView mRecyclerViewExpressTrace;
  @BindView(R.id.recycler_card) RecyclerView mRecyclerViewExpressCard;

  private ArrayList<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();
  private List<TracesBean> tracebeanlist=new ArrayList<TracesBean>();
  private RecyclerviewAdapter mExpressItemAdapter;
  private TraceAdapter mTraceAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected int getLayoutResId() {
    return R.layout.express_trace;
  }

  @Override
  protected void initView(View view, Bundle saveInstanceState) {
    FragmentManager fragmentManager = getChildFragmentManager();
    post();
    initdb();
    initRecyclerview();
  }
  private void initRecyclerview() {
    mExpressItemAdapter = new RecyclerviewAdapter(expressDetailList);
    mRecyclerViewExpressCard.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerViewExpressCard.setAdapter(mExpressItemAdapter);
    mRecyclerViewExpressCard.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));

    mTraceAdapter=new TraceAdapter(tracebeanlist);
    mRecyclerViewExpressTrace.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRecyclerViewExpressTrace.setAdapter(mTraceAdapter);
    mRecyclerViewExpressTrace.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));
  }
  void initdb() {
    if (DataSupport.findLast(ExpressDetail.class) != null) {
      expressDetailList.add(DataSupport.findLast(ExpressDetail.class,true));
      tracebeanlist=DataSupport.findAll(TracesBean.class);
    }
  }

  void post() {
    KdniaoTrackQueryAPI k = new KdniaoTrackQueryAPI();
    try {
      k.sendPost("", "");
    } catch (Exception e) {
      e.printStackTrace();
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
