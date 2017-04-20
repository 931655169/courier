package home;

import Entity.ExpressDetail;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import org.w3c.dom.Text;
import zjm.courier.R;

public class ExpressCardViewHolder extends EasyViewHolder<ExpressDetail> {
  @BindView(R.id.image_express) ImageView mImageViewExpress;
  @BindView(R.id.txt_express_status) TextView mTxtExpressStatus;
  @BindView(R.id.txt_express_name) TextView mTxtExpressName;
  private Context mContext;
  public ExpressCardViewHolder(Context context, ViewGroup parent, int layoutId) {
    super(context, parent, R.layout.recycler_item_express_card);
    this.mContext=context;
    ButterKnife.bind(this, itemView);
  }

  @Override public void bindTo(ExpressDetail value) {
    mImageViewExpress.setImageResource(R.mipmap.icon_blue);
    mTxtExpressStatus.setText(value.getState());
    mTxtExpressName.setText(value.getLogisticCode());
  }
}
