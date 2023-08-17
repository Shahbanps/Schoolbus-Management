package com.example.school_bus;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Par_viewholidays extends Activity implements JsonResponse{

	ListView l1;
	SharedPreferences sh;
	String[] hid,hname,date,alldata;
	public static String nid1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_viewholidays);
		
		l1=(ListView)findViewById(R.id.listView1);
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_viewholidays.this;
		String q="/par_viewholiday";
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_viewholiday"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					hid=new String[ja.length()];
					hname= new String[ja.length()];
					date= new String[ja.length()];
					alldata= new String[ja.length()];
					for(int i=0;i<ja.length();i++)
					{
						hid[i]=ja.getJSONObject(i).getString("holiday_id");
						hname[i]=ja.getJSONObject(i).getString("holiday_name");
						date[i]=ja.getJSONObject(i).getString("holiday_date");
						alldata[i]="\nHoliday : "+hname[i]+"\nDate : "+date[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No holidays", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getApplicationContext(),ParentHome.class));
					
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
		getMenuInflater().inflate(R.menu.par_viewholidays, menu);
		return true;
	}

}
