/**  
 * Project Name:Android_Car_Example  
 * File Name:RecomandAdapter.java  
 * Package Name:com.amap.api.car.example  
 * Date:2015年4月3日上午11:29:45  
 *  
 */

package send_express.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import zjm.courier.R;

/**
 * ClassName:RecomandAdapter <br/>
 * Function: 显示的poi列表 <br/>
 * Date: 2015年4月3日 上午11:29:45 <br/>
 * 
 * @author yiyi.qi
 * @version
 * @since JDK 1.6
 * @see
 */
public class RecomandAdapter extends BaseAdapter {

	PositionEntity[] entities = new PositionEntity[] {
			new PositionEntity(37.543164, 115.590439
					, "汇通快运","031"),
			new PositionEntity(37.553785,115.599778
					, "畅通货运","031"),
			new PositionEntity(37.601999, 115.634103
					, "韵达快递","031"),
			new PositionEntity(37.701378, 115.685487
					, "德邦","031"),
			new PositionEntity(37.721066, 115.679026
					, "申通快递","031"),
			new PositionEntity(37.744271, 115.689943
					, "中铁快运","031"),
			new PositionEntity(37.710631, 115.713186
					, "运达恒通火车站快零代办点","031"),
			new PositionEntity(37.729615, 115.705776
					, "安华物流","031"),
			new PositionEntity(37.729774, 115.705971
					, "希望物流(新桥南街)","031")
	};

	private List<PositionEntity> mPositionEntities;

	private Context mContext;

	public RecomandAdapter(Context context) {
		mContext = context;
		mPositionEntities = Arrays.asList(entities);
	
	}

	public void setPositionEntities(List<PositionEntity> entities) {
		this.mPositionEntities = entities;

	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return mPositionEntities.size();
	}

	@Override
	public Object getItem(int position) {

		return mPositionEntities.get(position);
	}

	@Override
	public long getItemId(int position) {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView textView = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			textView = (TextView) inflater.inflate(R.layout.view_recommond,
					null);
		} else {
			textView = (TextView) convertView;
		}
		textView.setText(mPositionEntities.get(position).address);
		return textView;
	}
}
