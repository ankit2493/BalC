package com.ankit.balc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends Activity implements View.OnClickListener {

	Button add_record,view_record;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		add_record=(Button)findViewById(R.id.add_record);
		view_record=(Button)findViewById(R.id.view_record);
		add_record.setOnClickListener(this);
		view_record.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.add_record:
			intent=new Intent("android.com.ankit.balc.ADDOREDIT");
			startActivity(intent);
			break;
		case R.id.view_record:
			intent=new Intent("android.com.ankit.balc.VIEWDAT");
			startActivity(intent);
			break;
		}
	}
}
 
