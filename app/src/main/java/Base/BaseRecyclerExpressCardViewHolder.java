package Base;

import Entity.ExpressDetail;
import Utils.CompanyfromCodeUtils;
import Utils.StatefromStateCode;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.carlosdelachica.easyrecycleradapters.adapter.EasyViewHolder;
import zjm.courier.R;

public class BaseRecyclerExpressCardViewHolder extends EasyViewHolder<ExpressDetail> {
  @BindView(R.id.image_express)ImageView mImageExpress;
  @BindView(R.id.txt_express)TextView mTxtExpressName;
  @BindView(R.id.txt_express_status)TextView mTxtExpressStatus;
  public BaseRecyclerExpressCardViewHolder(Context context, ViewGroup parent, int layoutId) {
    super(context, parent, R.layout.recycler_item_express_card);
    ButterKnife.bind(this,itemView);
  }

  @Override public void bindTo(ExpressDetail Express) {
    mTxtExpressName.setText(CompanyfromCodeUtils.Codeformat(Express.getShipperCode()));
    mTxtExpressStatus.setText(StatefromStateCode.format(Express.getState()));
  }
}
