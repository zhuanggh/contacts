package com.eoe.tampletfragment.adapter;

import java.util.ArrayList;
import android.app.Activity;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eoe.store.ContactsInfo;
import com.eoe.tampletfragment.QueryActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.addActivity;

class ViewHolder {
	public ImageView animal;
	public TextView cn_word;
	public TextView en_word;
}

public class ListAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	private ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>();
	private ArrayList<String> type = new ArrayList<String>();
	private Context context;
	public ListAdapter(Context context) {
		super();
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context=context;
	}

	public void setList(ArrayList<ContactsInfo> list) {
		this.list = list;
	}

	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder.animal = (ImageView) convertView.findViewById(R.id.animal);
			holder.cn_word = (TextView) convertView.findViewById(R.id.cn_word);
			holder.en_word = (TextView) convertView.findViewById(R.id.en_word);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.animal.setImageResource(R.drawable.ic_launcher);
		if (type.isEmpty()) {
			holder.cn_word.setText(list.get(position).getName());
			holder.en_word.setText("");
		}
		else{
			if(type.get(position)=="name"){
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText("");
			}
			else if(type.get(position)=="address"){
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(list.get(position).getAddress());
			}
			else if(type.get(position)=="remarks"){
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(list.get(position).getRemark());
			}
			else if(type.get(position)=="pinYin"){
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(list.get(position).getPinYin());
			}
			else if(type.get(position)=="FirstpinYin"){
				holder.cn_word.setText(list.get(position).getName());
				holder.en_word.setText(list.get(position).getFirstPinYin());
			}
			else{
				holder.cn_word.setText(list.get(position).getName());
				String tmp=type.get(position);
				holder.en_word.setText(tmp.substring(8));
			}
		}
		
		Button delButton = (Button)convertView.findViewById(R.id.bt_query); 
		delButton.setTag(position); 
		delButton.setOnClickListener(new View.OnClickListener() { 
			public void onClick(View v) { 
				//int index = Integer.parseInt(v.getTag().toString());
				int index = (Integer)v.getTag(); 
				Intent intent = new Intent(context, QueryActivity.class);
				intent.putExtra("key", list.get(index).getId());
				context.startActivity(intent);
				} 
			});
		// holder.speaker.setOnClickListener(new OnClickListener(){
		//
		// @Override
		// public void onClick(View v) {
		// System.out.println("Click on the speaker image on ListItem ");
		// }
		// });

		return convertView;
	}

}
