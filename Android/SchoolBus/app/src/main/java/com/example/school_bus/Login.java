package com.example.school_bus;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity implements JsonResponse {

	EditText e1,e2;
	TextView t1;
	Button b1;
	String users,pass,login_type;
	public static String logid,type;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		b1=(Button)findViewById(R.id.button1);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				users=e1.getText().toString();
				pass=e2.getText().toString();	
				
				//Toast.makeText(getApplicationContext(), users, Toast.LENGTH_LONG).show();
				JsonReq JR= new JsonReq();
				JR.json_response=(JsonResponse)Login.this;
				String q="/login?username=" + users + "&password=" + pass;
				JR.execute(q);
				
				//Toast.makeText(getApplicationContext(), q, Toast.LENGTH_LONG).show();
				
			}
		});
	}
	  public void response(JSONObject jo) {
	        // TODO Auto-generated method stub
	        try {
	            String status = jo.getString("status");
	            Log.d("result", status);
	            if (status.equalsIgnoreCase("success")) {
	                JSONArray ja = (JSONArray) jo.getJSONArray("data");
	                logid = ja.getJSONObject(0).getString("login_id");
	                type = ja.getJSONObject(0).getString("user_type");
	                SharedPreferences.Editor ed = sh.edit();
	                ed.putString("logid", logid);
	                ed.commit();

	                //startService(new Intent(getApplicationContext(), LocationService.class));

	                if (type.equals("drivers"))
	                {
	                    startActivity(new Intent(getApplicationContext(), DriverHome.class));
	                 //   startService(new Intent(getApplicationContext(), notification.class));
	                    startService(new Intent(getApplicationContext(), LocationService.class));
	                }
	                else if (type.equals("parents"))
	                    startActivity(new Intent(getApplicationContext(), ParentHome.class));
	                else
	                    Toast.makeText(getApplicationContext(), "Login failed..!!", Toast.LENGTH_LONG).show();
	            } else {
	                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
