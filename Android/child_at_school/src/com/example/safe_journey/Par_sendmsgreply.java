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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Par_sendmsgreply extends Activity implements JsonResponse,OnItemClickListener
{

	Button b1;
	EditText e1;
	ListView l1;
	String reply;
	
	SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_sendmsgreply);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		e1=(EditText)findViewById(R.id.editText1);
		
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				reply=e1.getText().toString();
				if(reply.equalsIgnoreCase(""))
				{
					e1.setError("Enter reply");
					e1.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Par_sendmsgreply.this;
					String q="/par_msgreply?mid=" + Par_viewmsg.mid1+"&reply="+reply;
					jr.execute(q);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.par_sendcomplaint, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_msgreply"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "success..", Toast.LENGTH_LONG).show();		
					startActivity(new Intent(getApplicationContext(),Par_viewmsg.class));
					
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
