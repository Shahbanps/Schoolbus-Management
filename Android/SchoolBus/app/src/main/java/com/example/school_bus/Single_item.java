package com.example.school_bus;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Single_item extends ArrayAdapter<String>
{
	String[] dname,phone,busno,image;
	Activity activity;
	

	public Single_item(Activity activity,String[] dname,String[] phone,String[] busno,String[] image) 
	{
		super(activity,R.layout.singleitem, image);
		// TODO Auto-generated constructor stub
		
		this.activity=activity;
		this.dname=dname;
		this.phone=phone;
		this.busno=busno;
		this.image=image;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		LayoutInflater inflate=activity.getLayoutInflater();
		View vi=inflate.inflate(R.layout.singleitem, null,true);
		
		TextView t1=(TextView) vi.findViewById(R.id.textViewdname);
		TextView t2=(TextView) vi.findViewById(R.id.textViewph);
		TextView t3=(TextView) vi.findViewById(R.id.textViewbno);
		
		t1.setText(dname[position]);
		t2.setText(phone[position]);
		t3.setText(busno[position]);
		
		ImageView im1=(ImageView) vi.findViewById(R.id.imageView1img);
		SharedPreferences	sh = PreferenceManager.getDefaultSharedPreferences(getContext());
		String path = "http://"+sh.getString("ip", "")+"/"+image[position].replace(" ", "%20");
		Picasso.with(activity).load(path).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(im1);
		
		return vi;
	}

	private Context getApplicationContext() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}