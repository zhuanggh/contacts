package com.eoe.tampletfragment;

import com.eoe.application.application;
import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author yangyu ��������������Activity����
 */
public class QueryActivity extends FragmentActivity {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private EditText name;
	private EditText phonenum;
	private EditText address;
	private EditText remarks;
	private Context context;
	private ContactsInfo user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_query);
		context = this;
		dbOpera = ((application) getApplication()).getDatabaseOperation();
		db = dbOpera.getDatabase();
		name = (EditText) findViewById(R.id.query_name);
		phonenum = (EditText) findViewById(R.id.query_phonenum);
		address = (EditText) findViewById(R.id.query_address);
		remarks = (EditText) findViewById(R.id.query_remarks);
		Intent intent=getIntent();
		int index=intent.getIntExtra("key", -1);
		if(index!=-1){
			user=dbOpera.query(index);
			init();
		}
	}


	public void init(){
		name.setText(user.getName());
		phonenum.setText(user.getPhoneNum(0));
		address.setText(user.getAddress());
		remarks.setText(user.getRemark());
	}
}
