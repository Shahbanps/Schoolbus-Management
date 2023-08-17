package com.example.safe_journey;

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

public class Par_view_driverss extends Activity implements JsonResponse{

	ListView l1;
	SharedPreferences sh;
	String[] did,dname,phone,image,busno,alldata;
	public static String nid1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_view_driverss);
		

		l1=(ListView)findViewById(R.id.listView1);
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_view_driverss.this;
		String q="/par_viewdrivers";
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_viewdrivers"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					did=new String[ja.length()];
					dname= new String[ja.length()];
					phone= new String[ja.length()];
					busno= new String[ja.length()];
					image= new String[ja.length()];
					//alldata= new String[ja.length()];
					for(int i=0;i<ja.length();i++)
					{
						did[i]=ja.getJSONObject(i).getString("driver_id");
						dname[i]=ja.getJSONObject(i).getString("first_name")+" "+ja.getJSONObject(i).getString("last_name");
						phone[i]=ja.getJSONObject(i).getString("contact_no");
						busno[i]=ja.getJSONObject(i).getString("register_number");
						image[i]=ja.getJSONObject(i).getString("image");
						//alldata[i]="\nHoliday : "+hname[i]+"\nDate : "+date[i]+"\n";
					}
					l1.setAdapter(new Single_item(Par_view_driverss.this,dname,phone,busno,image));
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No drivers", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.par_view_driverss, menu);
		return true;
	}

}
