package com.eoe.tampletfragment.fragment;


import java.util.ArrayList;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.tampletfragment.MainActivity;
import com.eoe.tampletfragment.R;
import com.eoe.tampletfragment.addActivity;
import com.eoe.tampletfragment.addContactsWay;
import com.eoe.tampletfragment.adapter.ListAdapter;
import com.eoe.tampletfragment.view.TitleView;
import com.eoe.tampletfragment.view.TitleView.OnLeftButtonClickListener;
import com.eoe.tampletfragment.view.TitleView.OnRightButtonClickListener;

/**
 * @author yangyu 功能描述：搜索fragment页面
 */
public class SearchFragment extends ListFragment {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private ArrayList<ContactsInfo> list = new ArrayList<ContactsInfo>();
	private ArrayList<ContactsInfo> list_search = new ArrayList<ContactsInfo>();
	private ArrayList<String> type = new ArrayList<String>();
	private View mParent;
	private FragmentActivity mActivity;
	private EditText etSearch;
	private ImageView ivClearText;

	private TitleView mTitle;

	private ListAdapter adapter = null;

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ListAdapter(getActivity());
		setListAdapter(adapter);
		dbOpera = ((application) getActivity().getApplication())
				.getDatabaseOperation();
		db = dbOpera.getDatabase();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		list = dbOpera.getAllUser();
		if (list.size() != 0) {
			adapter.setList(list);
			adapter.setType(type);
			adapter.notifyDataSetChanged();
		}
	}

	public static SearchFragment newInstance(int index) {
		SearchFragment f = new SearchFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		ivClearText = (ImageView) view.findViewById(R.id.ivClearText);
		etSearch = (EditText) view.findViewById(R.id.et_search);
		initListener();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();

		mTitle = (TitleView) mParent.findViewById(R.id.title);
		mTitle.setTitle(R.string.tab_contact);
		mTitle.setLeftButton(R.string.back, new OnLeftButtonClickListener() {

			@Override
			public void onClick(View button) {
				backHomeFragment();
			}
		});
		mTitle.setRightButton(R.string.add, new OnRightButtonClickListener() {

			@Override
			public void onClick(View button) {
				goHelp();
			}
		});
	}

	private void initListener() {

		/** 娓呴櫎杈撳叆瀛楃 **/
		ivClearText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		etSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable e) {

				String content = etSearch.getText().toString();
				list_search.clear();
				type.clear();
				if ("".equals(content)) {
					ivClearText.setVisibility(View.INVISIBLE);
				} else {
					ivClearText.setVisibility(View.VISIBLE);
				}
				if (content.matches("[0-9]+")) {
					Cursor cursor = db.rawQuery(
							"select * from phone where phonenum like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_search.add(dbOpera.query(id));
						type.add("phonenum" + cursor.getString(1));
					}
					cursor.close();
				}
				// 短拼音和全拼音搜索
				else if (content.matches("[a-zA-Z]+")) {
					Cursor cursor = db.rawQuery(
							"select id from contacts where FirstpinYin like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_search.add(dbOpera.query(id));
						type.add("FirstpinYin");
					}
					cursor.close();
					Cursor cursor1 = db.rawQuery(
							"select id from contacts where pinYin like'%"
									+ content + "%'", null);
					for (int i = 0; i < cursor1.getCount(); i++) {
						cursor1.moveToNext();
						int id = cursor1.getInt(0);
						list_search.add(dbOpera.query(id));
						type.add("pinYin");
					}

					cursor1.close();
				}
				// 搜索名字
				else {
					Cursor cursor = db.rawQuery(
							"select id from contacts where name like'%"
									+ content + "%'", null);// 此时得到的是一个数，而不是表，所以用cursor.getColumnIndex("id"))是错误的
					for (int i = 0; i < cursor.getCount(); i++) {
						cursor.moveToNext();
						int id = cursor.getInt(0);
						list_search.add(dbOpera.query(id));
						type.add("name");
					}
					cursor.close();
				}
				search_address();
				search_remarks();

				adapter.setList(list_search);
				adapter.setType(type);
				adapter.notifyDataSetChanged();

				// if (content.length() > 0) {
				// ArrayList<SortModel> fileterList = (ArrayList<SortModel>)
				// search(content);
				// adapter.updateListView(fileterList);
				// //mAdapter.updateData(mContacts);
				// } else {
				// adapter.updateListView(mAllContactsList);
				// }
				// mListView.setSelection(0);
				if (content == "") {
					if (list.size() != 0) {
						adapter.setList(list);
						type.clear();
						adapter.setType(type);
						adapter.notifyDataSetChanged();
					}
				}
			}
		});

		// 璁剧疆鍙充晶[A-Z]蹇�瀵艰埅鏍忚Е鎽哥洃鍚�
		// sideBar.setOnTouchingLetterChangedListener(new
		// OnTouchingLetterChangedListener() {
		//
		// @Override
		// public void onTouchingLetterChanged(String s) {
		// //璇ュ瓧姣嶉娆″嚭鐜扮殑浣嶇疆
		// int position = adapter.getPositionForSection(s.charAt(0));
		// if (position != -1) {
		// mListView.setSelection(position);
		// }
		// }
		// });
		// mListView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> adapterView, View view, int
		// position, long arg3) {
		// ViewHolder viewHolder = (ViewHolder) view.getTag();
		// viewHolder.cbChecked.performClick();
		// adapter.toggleChecked(position);
		// }
		// });

	}

	public void search_address() {
		String text = etSearch.getText().toString();
		// 查询地址
		Cursor cursor = db.rawQuery(
				"select sourceid from vir_address where value match'" + text
						+ "'", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			int id = cursor.getInt(0);
			list_search.add(dbOpera.query(id));
			type.add("address");
		}
		cursor.close();
	}

	public void search_remarks() {
		String text = etSearch.getText().toString();
		// 查询地址
		Cursor cursor = db.rawQuery(
				"select sourceid from vir_remarks where value match'" + text
						+ "'", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToNext();
			int id = cursor.getInt(0);
			list_search.add(dbOpera.query(id));
			type.add("remarks");
		}
		cursor.close();
	}

	/**
	 * 返回到首页
	 */
	private void backHomeFragment() {
		getFragmentManager().beginTransaction()
				.hide(MainActivity.mFragments[1])
				.show(MainActivity.mFragments[0]).commit();
		FragmentIndicator.setIndicator(0);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}

	private void goHelp() {
		Intent intent = new Intent(mActivity, addContactsWay.class);
		startActivity(intent);
	}

}
