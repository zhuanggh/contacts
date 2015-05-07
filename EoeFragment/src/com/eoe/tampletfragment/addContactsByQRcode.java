package com.eoe.tampletfragment;


import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

public class addContactsByQRcode extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		//Toast.makeText(this, "Äã¿ÉÒÔÉ¨Ãè¶şÎ¬Âë", Toast.LENGTH_SHORT).show();
		Intent  startScan = new Intent(addContactsByQRcode.this, CaptureActivity.class);
		startActivityForResult(startScan, 0);
		super.onCreate(arg0);
	}

}
