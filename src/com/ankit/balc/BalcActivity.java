package com.ankit.balc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BalcActivity extends Activity implements View.OnClickListener {
	TableLayout tl;
	EditText item, amount,savename;
	Button add, remove, save,btncancel,btnsave;
	String st_item, st_amount,dbname;
	Integer count;
	Float total_amount;
	TextView tv_item, tv_amount, total;
	Dialog dialog;
   DatePicker datePicker1;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		total_amount = 0.0f;
		item = (EditText) findViewById(R.id.item);
		amount = (EditText) findViewById(R.id.amount);
		add = (Button) findViewById(R.id.add);
		remove = (Button) findViewById(R.id.remove);
		save = (Button) findViewById(R.id.save);
		total = (TextView) findViewById(R.id.total);
		tl = (TableLayout) findViewById(R.id.tl);
		add.setOnClickListener(this);
		remove.setOnClickListener(this);
		save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add:
			st_item = item.getText().toString().trim();
			st_amount = amount.getText().toString().trim();
			if (st_item.length() > 0 && st_amount.length() > 0) {
				TableRow tr = new TableRow(BalcActivity.this);
				TextView tv1 = new TextView(BalcActivity.this);
				TextView tv2 = new TextView(BalcActivity.this);
				tv1.setText(st_item);
				tv2.setText(st_amount);
				tv1.setLayoutParams(new TableRow.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tr.addView(tv1);
				tv2.setLayoutParams(new TableRow.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				tr.addView(tv2);
				tl.addView(tr, new TableLayout.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				total_amount += Float.valueOf(st_amount);
				total.setText(total_amount.toString());
				item.setText("");
				amount.setText("");
			}
			else
			{
				if(st_item.length()==0)
				{
					Toast.makeText(BalcActivity.this, "Please Insert Item",
							Toast.LENGTH_SHORT).show();
				}
				else{
					Toast.makeText(BalcActivity.this, "Please Insert Amount",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.remove:
			int childCount=tl.getChildCount();
			if (childCount > 1) {
				TextView tv1 = new TextView(BalcActivity.this);
				TableRow tr = new TableRow(BalcActivity.this);
				tr = (TableRow) tl.getChildAt(childCount - 1);
				tv1 = (TextView) tr.getChildAt(1);
				total_amount -= Float.valueOf(tv1.getText().toString());
				total.setText(total_amount.toString());
				tl.removeViewAt(childCount - 1);
			} else {
				Toast.makeText(BalcActivity.this, "No Record to Delete",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.save:
			if (tl.getChildCount() > 1) {
				showDialog();
			} else {
				Toast.makeText(BalcActivity.this, "No Record is Inserted",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btncancel:
			dialog.dismiss();
			break;
		case R.id.btnsave:
			int   day  = datePicker1.getDayOfMonth();
			int   month= datePicker1.getMonth();
			int   year = datePicker1.getYear();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    String db_date = sdf.format(new Date(year-1900, month, day));
			if(true)
			{
			TextView tv1=new TextView(BalcActivity.this);
			TextView tv2=new TextView(BalcActivity.this);
			TableRow tr = new TableRow(BalcActivity.this);
			String db_item,db_amount;
			SQLiteDatabase db=openOrCreateDatabase("BalCdb",MODE_PRIVATE,null);
			db.execSQL("CREATE TABLE IF NOT EXISTS record (item VARCHAR(20),amount VARCHAR(10),date VARCHAR(10));");
			count=tl.getChildCount();
			for(int i=1;i<count;i++)
			{
				tr=(TableRow)tl.getChildAt(i);
				tv1=(TextView)tr.getChildAt(0);
				tv2=(TextView)tr.getChildAt(1);
				db_item=tv1.getText().toString();
				db_amount=tv2.getText().toString();
				db.execSQL("INSERT INTO record VALUES('"+db_item+"','"+db_amount+"','"+db_date+"');");
			}
			db.close();
			Toast.makeText(BalcActivity.this, "Records Inserted",
					Toast.LENGTH_SHORT).show();
			dialog.dismiss();
			}
			else
			{
				Toast.makeText(BalcActivity.this, "Please insert the File-Name",
						Toast.LENGTH_SHORT).show();
			}
			break;
			}
			
		}


	private void showDialog() {
		dialog=new Dialog(BalcActivity.this);
		dialog.setContentView(R.layout.dialog);
		datePicker1=(DatePicker)dialog.findViewById(R.id.datePicker1);
		btncancel=(Button)dialog.findViewById(R.id.btncancel);
		btnsave=(Button)dialog.findViewById(R.id.btnsave);
		dialog.setCancelable(true);
		dialog.setTitle("Save Record");
		btncancel.setOnClickListener(BalcActivity.this);
		btnsave.setOnClickListener(BalcActivity.this);
		dialog.show();
		
	}
}