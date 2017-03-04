package com.example.buttonsjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;

public class MainActivity extends Activity {
	private ToggleButton btnOne;
	private ToggleButton btnTwo;
	private SeekBar seekbar;
	private boolean btnoneSrv;
	private boolean btntwoSrv;
	private int seekbarSrv;//this im not sure yet
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this is to allow some connections in main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	setContentView(R.layout.activity_main);
	    if (networkInfo != null && networkInfo.isConnected()) {
	    	Log.d("app","is connected");
	       	connect_to_server();
	       	set_buttons();
	    } else {
	    	Log.d("app","else part");
	    	AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

	        dlgAlert.setMessage("No internet connection!");
	        dlgAlert.setTitle("Error Message...");
	        dlgAlert.setPositiveButton("OK", null);
	        dlgAlert.setCancelable(true);
	        Log.d("app","before settings the click listener");
	        dlgAlert.setPositiveButton("Ok",
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                    	exit();
	                    }
	                });
	        dlgAlert.create().show();
	    }
	    
        //check internet and get info from server
        
    }

    private void set_buttons() {
    	Log.d("app","set button");
		btnOne = (ToggleButton)findViewById(R.id.btnOne);
		Log.d("app","button found");
		btnOne.setChecked(btnoneSrv);
		Log.d("app","button set as true");
		btnTwo = (ToggleButton) findViewById(R.id.btnTwo);
		btnTwo.setChecked(btntwoSrv);
		seekbar = (SeekBar) findViewById(R.id.seekBar1);
		seekbar.setProgress(seekbarSrv);
	}

	private void connect_to_server(String link) {
		// TODO Auto-generated method stub
		//Log.d("app","connect to server");
		// connect to srv and get data.JSON
		String data = "test";
    	InputStream inputStream = null;
    	//Log.d(logTag,"init of local variables");
    	try {
    		 // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            Log.d(logTag,"create httpclient");
            // make GET request to the given URL
            HttpGet httpget = new HttpGet(link);
            Log.d(logTag,"make get request");
            // receive response as inputStream
            HttpResponse response = httpclient.execute(httpget);
            Log.d(logTag,"execute post");
            BufferedReader reader = 	new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            Log.d(logTag,"get entity");
            String line;
            while((line = reader.readLine())!= null){
            	data += line;
            }
            //inputStream = entity.getContent();
            //Log.d(logTag,"receive response as inputstream");
            // convert inputstream to string
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			data = e.getMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			data = e.getMessage();
		}
		btnoneSrv = true;
		btntwoSrv = true;
		seekbarSrv = 50;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void exit(){
    	Log.d("app","call the exit");
    	this.finish();
    	android.os.Process.killProcess(android.os.Process.myPid());
    	super.onDestroy();
    }
    
    public void saving(View v){
    	//see if conected;
    	//set the modify values on srv
    	Log.d("app","test save1 function");
    	btnOne.setChecked(false);
    	Log.d("app","the save button was clicked");
    }
}
