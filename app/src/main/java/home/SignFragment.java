package home;

import Entity.ExpressDetail;
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
import zjm.courier.R;

public class SignFragment extends Fragment {
  @BindView(R.id.recycler_sign) RecyclerView recyclerViewView;
  private RecyclerviewAdapter mSignExpressItemAdapter;
  private List<ExpressDetail> expressDetailList = new ArrayList<ExpressDetail>();

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.home_sign, container, false);
    ButterKnife.bind(this, v);
    initRecycler();
    initdb();
    return v;
  }

  private void initRecycler() {
    mSignExpressItemAdapter = new RecyclerviewAdapter(expressDetailList);
    recyclerViewView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerViewView.setAdapter(mSignExpressItemAdapter);
    recyclerViewView.addItemDecoration(
        new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.recycler_room)));
  }
  void initdb() {
    if (DataSupport.findAll(ExpressDetail.class) != null) {
      expressDetailList.clear();
      expressDetailList.addAll(DataSupport.where("State == ?","3").find(ExpressDetail.class));
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
