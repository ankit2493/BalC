package com.ankit.balc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

public class ViewDat extends Activity {

	private TextSwitcher mSwitcher;
	Button btnNext, btnPre;
   String textToShow;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view);
		Date date=new Date(); 
	   textToShow =""+(date.getYear()+1900);
		btnNext = (Button) findViewById(R.id.buttonNext);
		btnPre = (Button) findViewById(R.id.buttonPre);
		mSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher);

		mSwitcher.setFactory(new ViewFactory() {

			public TextView makeView() {
				// TODO Auto-generated method stub
				// create new textView and set the properties like clolr, size
				// etc
				TextView myText = new TextView(ViewDat.this);
				myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
				myText.setTextSize(36);
				myText.setTextColor(Color.BLUE);
				myText.setText(textToShow);
				return myText;
			}
		});

		final Animation in = AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in);
		
		final Animation out = AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out);

		// set the animation type of textSwitcher
		mSwitcher.setInAnimation(in);
		mSwitcher.setOutAnimation(out);
		btnNext.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Integer year = Integer.valueOf(textToShow);
				year++;
				// If index reaches maximum reset it
				textToShow = year.toString();
				mSwitcher.setText(textToShow);
			}
		});

		btnPre.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Integer year = Integer.valueOf(textToShow);
				year--;
				// If index reaches maximum reset it
				textToShow = year.toString();
				mSwitcher.setText(textToShow);
			}
		});

	}
}
