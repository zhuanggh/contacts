package com.eoe.application;

import java.io.IOException;

import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

import com.eoe.store.ContactsInfo;
import com.eoe.store.DatabaseOperation;
import com.eoe.util.DataBaseUtil;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class application extends Application {
	private DatabaseOperation dbOpera = null;
	private SQLiteDatabase db = null;
	private mThread mthread = null;
	private static final int MSG_SUCCESS = 0;
	private static final int MSG_FAILURE = 1;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {// 此方法在ui线程运行
			switch (msg.what) {
			case MSG_SUCCESS:
				dbOpera.setInfo();
				//load_data();
				break;

			case MSG_FAILURE:
				break;
			}
		}
	};

	public void onCreate() {
		copyDataBaseToPhone();
		mthread = new mThread();
		mthread.start();
	}

	public DatabaseOperation getDatabaseOperation() {
		dbOpera = new DatabaseOperation(this);
		db = dbOpera.getDatabase();
		return dbOpera;
	}

	public SQLiteDatabase getSQLiteDatabase() {
		dbOpera = new DatabaseOperation(this);
		db = dbOpera.getDatabase();
		return db;
	}

	public class mThread extends Thread {
		public mThread() {
			// TODO Auto-generated constructor stub

		}

		public void run() {
			Configuration cfg = new Configuration();
			cfg.setUseSmart(true);
			Dictionary.getInstance(cfg);
			mHandler.obtainMessage(MSG_SUCCESS).sendToTarget();
		}
	}
	//将res里面的数据库拷到手机上
	private void copyDataBaseToPhone() {  
        DataBaseUtil util = new DataBaseUtil(this);  
        // 判断数据库是否存在  
        boolean dbExist = util.checkDataBase();  
  
        if (dbExist) {  
            Log.i("tag", "The database is exist.");  
        } else {// 不存在就把raw里的数据库写入手机  
            try {  
                util.copyDataBase();  
            } catch (IOException e) {  
                throw new Error("Error copying database");  
            }  
        }  
    }  
//	private void load_data() {
//		SQLiteDatabase db = dbOpera.getDatabase();
//		ContactsInfo user = null;
//		// 添加
//		user = new ContactsInfo();
//		user.setName("庄广洪");
//		user.setPhoneNum("13560314018");
//		user.setAddress("中山大学至善园2号537");
//		user.setRemark("他是一个程序员，懒人");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("袁宝煜");
//		user.setPhoneNum("13824475455");
//		user.setAddress("中山大学至善园2号537");
//		user.setRemark("他是一个程序员，我宿友");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("陈生水");
//		user.setPhoneNum("13824410070");
//		user.setAddress("中山大学至善园2号537");
//		user.setRemark("他是一个程序员，也是逗比，我宿友");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("陈肖");
//		user.setPhoneNum("13929514854");
//		user.setPhoneNum("13250722709");
//		user.setAddress("中山大学至善园2号537");
//		user.setRemark("他是一个逗比，我宿友");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("黄佳曼");
//		user.setPhoneNum("13124967691");
//		user.setPhoneNum("13631458974");
//		user.setAddress("广州仲恺农业工程学院");
//		user.setRemark("我女朋友");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("黄永兴");
//		user.setPhoneNum("15692414034");
//		user.setAddress("广州财经大学");
//		user.setRemark("她是一个傻子，我朋友");
//		dbOpera.insert(user);
//		// 添加
//		user = new ContactsInfo();
//		user.setName("lily");
//		user.setPhoneNum("13560314018");
//		user.setAddress("深圳广百集团");
//		user.setRemark("他是一个虚构对象");
//		dbOpera.insert(user);
//	}
}
