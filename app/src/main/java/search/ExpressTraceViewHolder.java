package search;

import Entity.TracesBean;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import zjm.courier.R;

public class ExpressTraceViewHolder extends EasyViewHolder<TracesBean>{
  @BindView(R.id.txt_accepttime)TextView mTxtAcceptTime;
  @BindView(R.id.txt_acceptstation)TextView mTxtAcceptStation;
  public ExpressTraceViewHolder(Context context, ViewGroup parent,
      int layoutId) {
    super(context, parent, R.layout.recycler_express_trace_item);
    ButterKnife.bind(this,itemView);
  }

  @Override public void bindTo(TracesBean value) {
    mTxtAcceptTime.setText(value.getAcceptTime());
    mTxtAcceptStation.setText(value.getAcceptStation());
  }
}
