package search;

import Entity.TracesBean;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import zjm.courier.R;

public class TraceAdapter extends RecyclerView.Adapter<TraceAdapter.ViewHolder> {
  private List<TracesBean> mTraceList;

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView mTxtAcceptstattion;
    TextView mTxtAccepttime;

    public ViewHolder(View itemView) {
      super(itemView);
      mTxtAcceptstattion = (TextView) itemView.findViewById(R.id.txt_acceptstation);
      mTxtAccepttime = (TextView) itemView.findViewById(R.id.txt_accepttime);
    }
  }

  public TraceAdapter(List<TracesBean> mTraceLists) {
    this.mTraceList = mTraceLists;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recycler_express_trace_item, parent, false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    TracesBean tracesBean = mTraceList.get(position);
    holder.mTxtAccepttime.setText(tracesBean.getAcceptTime());
    holder.mTxtAcceptstattion.setText(tracesBean.getAcceptStation());
  }

  @Override public int getItemCount() {
    return mTraceList.size();
  }
}
