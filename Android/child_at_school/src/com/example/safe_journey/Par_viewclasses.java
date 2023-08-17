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

public class Par_viewclasses extends Activity  implements JsonResponse{

	ListView l1;
	SharedPreferences sh;
	String[] cid,course,batch,sub,tutor,date,alldata;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_viewclasses);
		
		l1=(ListView)findViewById(R.id.listView1);
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_viewclasses.this;
		String q="/par_viewclasses";
		jr.execute(q);
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_viewclasses"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					cid=new String[ja.length()];
					course= new String[ja.length()];
					batch= new String[ja.length()];
					sub= new String[ja.length()];
					tutor= new String[ja.length()];
					date= new String[ja.length()];
					alldata= new String[ja.length()];
					for(int i=0;i<ja.length();i++)
					{
						cid[i]=ja.getJSONObject(i).getString("class_id");
						course[i]=ja.getJSONObject(i).getString("course");
						batch[i]=ja.getJSONObject(i).getString("batch");
						sub[i]=ja.getJSONObject(i).getString("sub_name");
						tutor[i]=ja.getJSONObject(i).getString("tutor_name");
						date[i]=ja.getJSONObject(i).getString("date_time");
						alldata[i]="\nCourse : "+course[i]+"\nBatch : "+batch[i]+"\nSubject : "+sub[i]+"\nTutor : "+tutor[i]+"\nDate : "+date[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_data,alldata));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "No classes", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.par_viewclasses, menu);
		return true;
	}

}
