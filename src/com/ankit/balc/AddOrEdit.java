package com.ankit.balc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class AddOrEdit extends Activity implements OnClickListener {
	Dialog dialog,dialog2;
	DatePicker datePicker1;
	Integer db_date;
	Float total_amount;
	EditText item, amount;
	Button add, remove, save,main_date;
	TextView total;
	TableLayout tl;
	SQLiteDatabase db;
	Boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		main_date = (Button) findViewById(R.id.main_date);
		add.setOnClickListener(this);
		remove.setOnClickListener(this);
		save.setOnClickListener(this);
		main_date.setOnClickListener(this);
		flag=false;
		showDialog();
	}

	private void showDialog() {
		// TODO Auto-generated method stub
		dialog = new Dialog(AddOrEdit.this);
		dialog.setContentView(R.layout.dialog);
		Button btncancel = (Button) dialog.findViewById(R.id.btncancel);
		Button btnsave = (Button) dialog.findViewById(R.id.btnsave);
		datePicker1 = (DatePicker) dialog.findViewById(R.id.datePicker1);
		btnsave.setText("OK");
		btncancel.setOnClickListener(AddOrEdit.this);
		btnsave.setOnClickListener(AddOrEdit.this);
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add:
			String st_item,
			st_amount;
			st_item = item.getText().toString().trim();
			st_amount = amount.getText().toString().trim();
			if (st_item.length() > 0 && st_amount.length() > 0) {
				TableRow tr = new TableRow(AddOrEdit.this);
				TextView tv1 = new TextView(AddOrEdit.this);
				TextView tv2 = new TextView(AddOrEdit.this);
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
			} else {
				if (st_item.length() == 0) {
					Toast.makeText(AddOrEdit.this, "Please Insert Item",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddOrEdit.this, "Please Insert Amount",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.remove:
			int childCount = tl.getChildCount();
			if (childCount > 1) {
				TextView tv1 = new TextView(AddOrEdit.this);
				TableRow tr = new TableRow(AddOrEdit.this);
				tr = (TableRow) tl.getChildAt(childCount - 1);
				tv1 = (TextView) tr.getChildAt(1);
				total_amount -= Float.valueOf(tv1.getText().toString());
				total.setText(total_amount.toString());
				tl.removeViewAt(childCount - 1);
			} else {
				Toast.makeText(AddOrEdit.this, "No Record to Delete",
						Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.btncancel:
			dialog.dismiss();
			if(!flag)
			{
			finish();
			}
			else
			{
			
			}
			break;
		case R.id.btnsave:
			flag=true;
			setDateString();
			dialog.dismiss();
			clearTable();
			getContent();
			break;
		case R.id.save:
			savecontentt();
			break;
		case R.id.main_date:
			/*Intent intent = getIntent();
			finish();
	         startActivity(intent);
	         break;*/
			showDialog();
			break;
		}
	}

	
	private void clearTable() {
		// TODO Auto-generated method stub
		tl.removeViews(1, tl.getChildCount()-1);
		total_amount=0.0f;
		
	}

	private void getContent() {
		// TODO Auto-generated method stub
		openDatabase();
		Cursor c = db.rawQuery("SELECT * FROM record where date=" + db_date,
				null);
		Cursor d=db.rawQuery("SELECT * FROM recordTotal where date=" + db_date,
				null);
		if(d.moveToFirst())
		{
		Toast.makeText(AddOrEdit.this,d.getString(d.getColumnIndex("total")),
				Toast.LENGTH_LONG).show();
		}
		Integer co = c.getCount();
		if (co > 0) {
			if (c.moveToFirst()) {
				do {
					TableRow tr = new TableRow(AddOrEdit.this);
					TextView tv1 = new TextView(AddOrEdit.this);
					TextView tv2 = new TextView(AddOrEdit.this);
					tv1.setText(c.getString(c.getColumnIndex("item")));
					tv2.setText(c.getString(c.getColumnIndex("amount")));
					total_amount += Float.valueOf(tv2.getText().toString());
					tv1.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					tr.addView(tv1);
					tv2.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					tr.addView(tv2);
					tl.addView(tr,
							new TableLayout.LayoutParams(
									LayoutParams.FILL_PARENT,
									LayoutParams.WRAP_CONTENT));
					// get the data into array,or class variable
				} while (c.moveToNext());
				total.setText(total_amount.toString());
			}
		}
		db.close();
	}

	
	
	private void savecontentt() {
		// TODO Auto-generated method stub
		int count = tl.getChildCount();
		if(count>1)
		{
		showDialog2();
		deletecontent();
		TextView tv1 = new TextView(AddOrEdit.this);
		TextView tv2 = new TextView(AddOrEdit.this);
		TableRow tr = new TableRow(AddOrEdit.this);
		String db_item,	db_amount;
		for (int i = 1; i < count; i++) {
			tr = (TableRow) tl.getChildAt(i);
			tv1 = (TextView) tr.getChildAt(0);
			tv2 = (TextView) tr.getChildAt(1);
			db_item = tv1.getText().toString();
			db_amount = tv2.getText().toString();
			db.execSQL("INSERT INTO record VALUES('" + db_item + "','"
					+ db_amount + "'," + db_date + ");");
		}
		db.execSQL("INSERT INTO recordTotal VALUES('" + db_date + "','"+total.getText().toString()+"');");
		db.close();
		Toast.makeText(AddOrEdit.this, "Records Inserted",
				Toast.LENGTH_SHORT).show();
		dialog2.dismiss();
		}
		else
		{
		Toast.makeText(AddOrEdit.this, "No Record To Insert",
					Toast.LENGTH_SHORT).show();
		}
		
	}

	private void deletecontent() {
		// TODO Auto-generated method stub
		db = openOrCreateDatabase("BalCdb", MODE_PRIVATE, null);
		db.execSQL("DELETE FROM record WHERE date="+db_date);
		db.execSQL("DELETE FROM recordTotal WHERE date="+db_date);
	}

	private void showDialog2() {
		// TODO Auto-generated method stub
		dialog2= new Dialog(AddOrEdit.this);
		dialog2.setContentView(R.layout.notcancel);
		dialog2.setCancelable(false);
		dialog2.show();
	}


	private void openDatabase() {
		// TODO Auto-generated method stub
		db = openOrCreateDatabase("BalCdb", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS record (item VARCHAR(20),amount VARCHAR(10),date NUMBER(10));");
		db.execSQL("CREATE TABLE IF NOT EXISTS recordTotal (date NUMBER(10),total VARCHAR(10));");
	}

	private void setDateString() {
		// TODO Auto-generated method stub
		int day = datePicker1.getDayOfMonth();
		int month = datePicker1.getMonth();
		int year = datePicker1.getYear();
		SimpleDateFormat simple = new SimpleDateFormat("dd-MMM-yyyy");
		String string_date = simple.format(new Date(year - 1900, month, day));
		main_date.setText(string_date);
		SimpleDateFormat database = new SimpleDateFormat("yyyyMMdd");
		db_date = Integer.valueOf(database.format(new Date(year - 1900, month,
				day)));
	}

}
