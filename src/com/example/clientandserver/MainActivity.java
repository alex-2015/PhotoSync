package com.example.clientandserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;




import android.net.NetworkInfo.DetailedState;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button startService, stopService, send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startService = (Button) findViewById(R.id.startService);
		stopService = (Button) findViewById(R.id.stopService);
		send = (Button) findViewById(R.id.send);

		startService.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// start the background activity here and ping the notification
				// bar
				Log.i("main", "clicked start service");

				startService(new Intent(getBaseContext(), myService.class));

			}
		});

		stopService.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// stop the background activity here and stop pinging
				Log.i("main", "clicked stop service");
				stopService(new Intent(getBaseContext(), myService.class));

			}
		});

		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// stop the background activity here and stop pinging
				Log.i("main", "clicked send");
				MyClientTask myClientTask = new MyClientTask("192.168.233.128",12345);
				myClientTask.execute();
			}
		});

	}
	
public class MyClientTask extends AsyncTask<Void, Void, Void> {
		
		String dstAddress;
		int dstPort;
		
		MyClientTask(String addr, int port){
			Log.i("asyncTask","inside async constructor");
			dstAddress = addr;
			dstPort = port;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			
			try {
				Log.i("asyncTask","inside async");
		        Socket s = new Socket(dstAddress,dstPort);
//		        Log.i("aynctask","after socket");
		        //outgoing stream redirect to socket
//		        OutputStream out = s.getOutputStream();
//		        Log.i("aynctask","afeter output stream");
//		        	
//		        PrintWriter output = new PrintWriter(out);
//		        Log.i("aynctask","after printwriter");
//		        
//		        output.println("Hello Android!");
//		        Log.i("aynctask","after println");
//		        
////		        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
//		        Log.i("aynctask","after buffered reader");
		        
		        OutputStream outputStream;
		        outputStream = s.getOutputStream();
	            PrintStream printStream = new PrintStream(outputStream);
	            printStream.print("OTHER CODE");
	            printStream.close();
		        //read line(s)
//		        String st = input.readLine();
//		        Log.i("aynctask","after readline");
		        
		        //. . .
		        //Close connection
		        s.close();
//		        Log.i("aynctask","after close");
		        


		} catch (UnknownHostException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		} catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
//			Log.i("async","in post execute");
			super.onPostExecute(result);
		}

		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
