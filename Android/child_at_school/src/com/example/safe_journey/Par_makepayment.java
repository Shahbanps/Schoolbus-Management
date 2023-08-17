package com.example.safe_journey;

import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Par_makepayment extends Activity implements JsonResponse{

	Button b1;
	EditText e1;
	ListView l1;
	String amt;
	
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_par_makepayment);

		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		e1=(EditText)findViewById(R.id.editText1);
		
		b1=(Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				amt=e1.getText().toString();
				if(amt.equalsIgnoreCase(""))
				{
					e1.setError("Enter amt");
					e1.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Par_makepayment.this;
					String q="/par_makepayment?sid=" + Par_viewstudlist.sid1+"&amt="+amt;
					jr.execute(q);
				}
			}
		});
	}
	@Override
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("par_makepayment"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "success..", Toast.LENGTH_LONG).show();		
					startActivity(new Intent(getApplicationContext(),ParentHome.class));
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.par_makepayment, menu);
		return true;
	}
}
