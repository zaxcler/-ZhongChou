package com.lianhai.zhongchou.adapter;

import android.content.Context;
import android.widget.TextView;

import com.lianhai.zhongchou.R;
import com.lianhai.zhongchou.bean.AddressBean;

import java.util.List;

public class AddressAdapter extends CommonAdapter<AddressBean> {

	public AddressAdapter(Context context, List<AddressBean> list, int resource) {
		super(context, list, resource);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDataToItem(ViewHolder holder, AddressBean t) {
		TextView name = holder.getView(R.id.name);
		if (t.getName()!=null&&t.getTelephone()!=null){
			name.setText(t.getName()+"("+t.getTelephone()+")");
		}else {
			name.setText("");
		}

		TextView address = holder.getView(R.id.address);
		if (t.getProvince_name()!=null&&t.getCity_name()!=null&&t.getZipcode()!=null){
			address.setText(t.getProvince_name()+" "+t.getCity_name()+" "+t.getZipcode());
		}else {
			address.setText("");
		}
		
	}

	public void update(List<AddressBean> list){
		this.list.clear();
		this.list.addAll(list);
	}

}
