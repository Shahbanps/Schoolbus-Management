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

public class Par_viewmsg extends Activity implements JsonResponse,OnItemClickListener
{
	ListView l1;
	SharedPreferences sh;
	String[] mid,msg,date,alldata;
	public static String mid1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_viewmsg);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		l1=(ListView)findViewById(R.id.listView1);
		l1.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Par_viewmsg.this;
		String q="/par_viewmsg?logid=" + sh.getString("logid", "");
		jr.execute(q);
	}
	
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_viewmsg"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					mid=new String[ja.length()];
					msg= new String[ja.length()];
					date= new String[ja.length()];
					alldata= new String[ja.length()];
					for(int i=0;i<ja.length();i++)
					{
						mid[i]=ja.getJSONObject(i).getString("message_id");
						msg[i]=ja.getJSONObject(i).getString("message_description");
						date[i]=ja.getJSONObject(i).getString("message_date");
						alldata[i]="\n"+msg[i]+"\nDated : "+date[i]+"\n";
					}
					l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list_data,alldata));
				}
				else
				{
					
					Toast.makeText(getApplicationContext(), "No messages", Toast.LENGTH_LONG).show();
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.par_viewmsg, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mid1=mid[arg2];
		 final CharSequence[] items = {"Send Reply" ,"Cancel"};

		  AlertDialog.Builder builder = new AlertDialog.Builder(Par_viewmsg.this);
		  builder.setTitle("Select Option!");
		  builder.setItems(items, new DialogInterface.OnClickListener() {
		   @Override
		   public void onClick(DialogInterface dialog, int item) {

		    if (items[item].equals("Send Reply")) 
		    {   
		    	startActivity(new Intent(getApplicationContext(),Par_sendmsgreply.class));
		    }
			if (items[item].equals("Cancel"))
			{
	            dialog.dismiss();
	        }
			}
		  });
		  builder.show();
	}
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),ParentHome.class);			
		startActivity(b);
		
	}
}
