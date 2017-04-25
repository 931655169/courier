package home;

import Entity.ExpressDetail;
import Utils.CompanyfromCodeUtils;
import Utils.StatefromStateCode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import zjm.courier.R;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
  private List<ExpressDetail> mExpressDetailList;

  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView mImageViewExpress;
    TextView mTxtExpressStatus;
    TextView mTxtExpressName;
    TextView mTxtExpressCompany;

    public ViewHolder(View itemView) {
      super(itemView);
      mImageViewExpress = (ImageView) itemView.findViewById(R.id.image_express);
      mTxtExpressName = (TextView) itemView.findViewById(R.id.txt_express_name);
      mTxtExpressStatus = (TextView) itemView.findViewById(R.id.txt_express_status);
      mTxtExpressCompany= (TextView) itemView.findViewById(R.id.txt_express_company);
    }
  }

  public RecyclerviewAdapter(List<ExpressDetail> ExpressList) {
    this.mExpressDetailList = ExpressList;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recycler_item_express_card, parent, false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    ExpressDetail expressDetail = mExpressDetailList.get(position);
    holder.mImageViewExpress.setImageResource(R.mipmap.icon_blue);
    holder.mTxtExpressStatus.setText(StatefromStateCode.format(expressDetail.getState()));
    holder.mTxtExpressName.setText(expressDetail.getLogisticCode());
    holder.mTxtExpressCompany.setText(CompanyfromCodeUtils.Codeformat(expressDetail.getShipperCode()));
  }

  @Override public int getItemCount() {
    return mExpressDetailList.size();
  }
}
