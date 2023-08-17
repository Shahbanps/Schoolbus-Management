package com.example.safe_journey;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Par_viewstudlist extends Activity implements JsonResponse,OnItemClickListener
{
	ListView l1;
	SharedPreferences sh;
	String[] sid,sname,alldata;
	public static String sid1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_viewstudlist);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_viewstudlist.this;
		String q="/parent_studentlist?logid=" + sh.getString("logid", "");
		jr.execute(q);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.par_viewstudlist, menu);
		return true;
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		sid1=sid[arg2];
		
		 final CharSequence[] items = {"Student details" ,"Make payment","Cancel"};

		  AlertDialog.Builder builder = new AlertDialog.Builder(Par_viewstudlist.this);
		  builder.setTitle("Select Option!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int item) {

		    if (items[item].equals("Student details")) 
		    {   
		    	startActivity(new Intent(getApplicationContext(), Par_trackbus.class));
		    	
		    }
		    if (items[item].equals("Make payment")) 
		    {  
		    	Intent i1=new Intent(getApplicationContext(),Par_makepayment.class);
				startActivity(i1);
		    }
		if (items[item].equals("Cancel")) {
                   dialog.dismiss();
               }
		    }
		   
		  });
		  builder.show();
		
	}
}
