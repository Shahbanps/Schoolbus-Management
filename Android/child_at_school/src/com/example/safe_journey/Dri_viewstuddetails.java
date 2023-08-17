package com.example.safe_journey;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Dri_viewstuddetails extends Activity implements JsonResponse,OnItemClickListener
{
	ListView l1;
	SharedPreferences sh;
	String[] sid,sname,alldata;
	public static String sid1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dri_viewstuddetails);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Dri_viewstuddetails.this;
		String q="/driver_studentlist?logid=" + sh.getString("logid", "");
		jr.execute(q);
	}

	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				sid=new String[ja.length()];
				sname= new String[ja.length()];
				alldata= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					sid[i]=ja.getJSONObject(i).getString("student_id");
					sname[i]=ja.getJSONObject(i).getString("first_name")+" "+ja.getJSONObject(i).getString("last_name");
					alldata[i]=sname[i]+"\n";
				}
				l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_data,alldata));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No Students", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.dri_viewstuddetails, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		sid1=sid[arg2];
		startActivity(new Intent(getApplicationContext(), Dri_addattendance.class));
		
	}

}
