package com.example.school_bus;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Par_trackbus extends Activity implements JsonResponse
{
	SharedPreferences sh;
	TextView t1,t2,t3,t4,t5,t6;
	ImageView m1;
	Button b1,b2;
	String aval,sname,dname,cno,jstatus,attendance,image;
	public static String blati,blongi;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_trackbus);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		t1=(TextView)findViewById(R.id.textViewaval);
		t2=(TextView)findViewById(R.id.textViewsname);
		t3=(TextView)findViewById(R.id.textViewdname);
		t4=(TextView)findViewById(R.id.textViewphone);
		t5=(TextView)findViewById(R.id.textViewatten);
		t6=(TextView)findViewById(R.id.textViewjstatus);
		m1=(ImageView)findViewById(R.id.imageView1);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 final CharSequence[] items = {"Present" ,"Absent","Cancel"};

				  AlertDialog.Builder builder = new AlertDialog.Builder(Par_trackbus.this);
				  builder.setTitle("Add Availability!");
				  builder.setItems(items, new DialogInterface.OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int item) {

				    if (items[item].equals("Present")) 
				    {   
						JsonReq jr= new JsonReq();
						jr.json_response=(JsonResponse)Par_trackbus.this;
						String q="/par_addavailability?sid=" + Par_viewstudlist.sid1+"&astatus=Present";
						jr.execute(q);
				    }
				    if (items[item].equals("Absent")) 
				    {  
						JsonReq jr= new JsonReq();
						jr.json_response=(JsonResponse)Par_trackbus.this;
						String q="/par_addavailability?sid=" + Par_viewstudlist.sid1+"&astatus=Absent";
						jr.execute(q);
				    }
				if (items[item].equals("Cancel")) {
		                    dialog.dismiss();
		                }
				    }
				  });
				  builder.show();
			}
		});
		b2=(Button)findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				String url2 = "http://www.google.com/maps?q="+blati+","+blongi;
		        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
		        startActivity(in);
			}
		});
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_trackbus.this;
		String q="/parent_studentdetails?sid=" + Par_viewstudlist.sid1;
		jr.execute(q);
		
		view_attendance();
		viewstudavailability();
	}
	public void view_attendance()
	{
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_trackbus.this;
		String q="/driver_viewattendance?student_id=" + Par_viewstudlist.sid1;
		jr.execute(q);
	}
	public void viewstudavailability()
	{
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_trackbus.this;
		String q="/driver_viewstudavailability?student_id=" + Par_viewstudlist.sid1;
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("parent_studentdetails"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					sname=ja.getJSONObject(0).getString("sname");
					
					dname=ja.getJSONObject(0).getString("dname");
					cno=ja.getJSONObject(0).getString("contact_no");
					jstatus=ja.getJSONObject(0).getString("status");
					blati=ja.getJSONObject(0).getString("latitude");
					blongi=ja.getJSONObject(0).getString("longitude");
					image=ja.getJSONObject(0).getString("image");
					t2.setText(sname);
					t3.setText(dname);
					t4.setText(cno);
					t6.setText(jstatus);
					String path = "http://"+sh.getString("ip", "")+"/"+image.replace(" ", "%20");
					Picasso.with(getApplicationContext()).load(path).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(m1);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("par_addavailability"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Availability Added..", Toast.LENGTH_LONG).show();
					viewstudavailability();
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Already Added", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("driver_viewattendance"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					attendance=ja.getJSONObject(0).getString("astatus");
					t5.setText(attendance);
					
				}
				else
				{
					t5.setText("Not Added");
				}
			}
			else if(method.equalsIgnoreCase("driver_viewstudavailability"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					aval=ja.getJSONObject(0).getString("status");
					t1.setText(aval);
					b1.setVisibility(View.GONE);
				}
				else
				{
					t1.setText("Not Added");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.par_trackbus, menu);
		return true;
	}

}
