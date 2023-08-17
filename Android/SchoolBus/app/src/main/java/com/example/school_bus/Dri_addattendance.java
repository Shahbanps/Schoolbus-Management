package com.example.school_bus;

import org.json.JSONArray;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Dri_addattendance extends Activity implements JsonResponse{

	SharedPreferences sh;
	TextView t1,t2,t3,t4,t5;
	ImageView m1;
	Button b1;
	String aval,sname,pname,cno,attendance,image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dri_addattendance);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		t1=(TextView)findViewById(R.id.textViewaval);
		t2=(TextView)findViewById(R.id.textViewsname);
		t3=(TextView)findViewById(R.id.textViewpname);
		t4=(TextView)findViewById(R.id.textViewphone);
		t5=(TextView)findViewById(R.id.textViewatten);
		m1=(ImageView)findViewById(R.id.imageView1);
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				 final CharSequence[] items = {"Present" ,"Absent","Cancel"};

				  AlertDialog.Builder builder = new AlertDialog.Builder(Dri_addattendance.this);
				  builder.setTitle("Add Attendance!");
				  builder.setItems(items, new DialogInterface.OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int item) {

				    if (items[item].equals("Present")) 
				    {   

						JsonReq jr= new JsonReq();
						jr.json_response=(JsonResponse)Dri_addattendance.this;
						String q="/driver_addattendance?sid=" + Dri_viewstuddetails.sid1+"&astatus=Present";
						jr.execute(q);
				    }
				    if (items[item].equals("Absent")) 
				    {  
				    	
						JsonReq jr= new JsonReq();
						jr.json_response=(JsonResponse)Dri_addattendance.this;
						String q="/driver_addattendance?sid=" + Dri_viewstuddetails.sid1+"&astatus=Absent";
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
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Dri_addattendance.this;
		String q="/driver_studdetails?student_id=" + Dri_viewstuddetails.sid1;
		jr.execute(q);
		viewstudavailability();
		view_attendance();
	}
	public void view_attendance()
	{
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Dri_addattendance.this;
		String q="/driver_viewattendance?student_id=" + Dri_viewstuddetails.sid1;
		jr.execute(q);
	}
	public void viewstudavailability()
	{
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Dri_addattendance.this;
		String q="/driver_viewstudavailability?student_id=" + Dri_viewstuddetails.sid1;
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("driver_studdetails"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					sname=ja.getJSONObject(0).getString("sname");
					pname=ja.getJSONObject(0).getString("pname");
					cno=ja.getJSONObject(0).getString("phone");
					image=ja.getJSONObject(0).getString("image");
					t2.setText(sname);
					t3.setText(pname);
					t4.setText(cno);
					String path = "http://"+sh.getString("ip", "")+"/"+image.replace(" ", "%20");
					Picasso.with(getApplicationContext()).load(path).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(m1);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("driver_addattendance"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Attendance Added..", Toast.LENGTH_LONG).show();
					view_attendance();
					
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
					b1.setVisibility(View.GONE);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dri_addattendance, menu);
		return true;
	}

}
