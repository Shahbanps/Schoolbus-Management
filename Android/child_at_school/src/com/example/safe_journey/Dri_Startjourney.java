package com.example.safe_journey;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Dri_Startjourney extends Activity implements JsonResponse{

	Button b1,b2;
	TextView t1,t2;
	SharedPreferences sh;
	String jname,jstatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dri__startjourney);
		b1=(Button)findViewById(R.id.buttonstart);
		b2=(Button)findViewById(R.id.buttonstop);
		t1=(TextView)findViewById(R.id.textViewjourney);
		t2=(TextView)findViewById(R.id.textViewstatus);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		viewjourneystatus();
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				JsonReq JR= new JsonReq();
				JR.json_response=(JsonResponse)Dri_Startjourney.this;
				String q="/update_journeystatus?logid=" + sh.getString("logid", "")+"&status=Running";
				JR.execute(q);
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				JsonReq JR= new JsonReq();
				JR.json_response=(JsonResponse)Dri_Startjourney.this;
				String q="/update_journeystatus?logid=" + sh.getString("logid", "")+"&status=Stoped";
				JR.execute(q);
			}
		});
	}
	public void viewjourneystatus()
	{
		JsonReq JR= new JsonReq();
		JR.json_response=(JsonResponse)Dri_Startjourney.this;
		String q="/view_journeystatus?logid=" + sh.getString("logid", "");
		JR.execute(q);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dri__startjourney, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("view_journeystatus"))
			{
				String status=jo.getString("status");
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					jname=ja.getJSONObject(0).getString("route_name");
					jstatus=ja.getJSONObject(0).getString("status");
					t1.setText(jname);
					t2.setText(jstatus);
					if(jstatus.equalsIgnoreCase("Running"))
					{
						b1.setVisibility(View.GONE);
						b2.setVisibility(View.VISIBLE);
						
					}
					else if(jstatus.equalsIgnoreCase("Stoped"))
					{	
						b2.setVisibility(View.GONE);
						b1.setVisibility(View.VISIBLE);	
						
					}
					else if(jstatus.equalsIgnoreCase("assigned"))
					{	
						b2.setVisibility(View.GONE);
						
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("journeystart"))
			{
				String status=jo.getString("status");
				if(status.equalsIgnoreCase("success"))
				{
					
					Toast.makeText(getApplicationContext(), "Journey Started", Toast.LENGTH_LONG).show();
					viewjourneystatus();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("journeystop"))
			{
				String status=jo.getString("status");
				if(status.equalsIgnoreCase("success"))
				{
					
					Toast.makeText(getApplicationContext(), "Journey Stoped", Toast.LENGTH_LONG).show();
					viewjourneystatus();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
		}
	}

}
