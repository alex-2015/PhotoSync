package com.example.clientandserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class myService extends Service

{
	int lim = 0;
	
	public class serverThread extends Thread
	{
		@Override
		public void run()
		{
			
			if (lim == 1) {
				
				try {
					Log.i("service","inside server try");
					Boolean end = false;
					ServerSocket ss = new ServerSocket(12345);
					while (!end) {
						Log.i("service","inside server while");
						// Server is waiting for client here, if needed
						Socket s = ss.accept();
						BufferedReader input = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						PrintWriter output = new PrintWriter(s.getOutputStream(),
								true); // Autoflush
						String st = input.readLine();
						Log.i("Tcp Example", "From client: " + st);
						output.println("Good bye and thanks for all the fish :)");
						s.close();
						if (lim == 0) {
							end = true;
						}
					}
					ss.close();

				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Log.i("service", "one service already started");

			}

			
			
		}
		
	}
	
	
	@Override
	public void onCreate() {

		Log.i("service", "creating service");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i("service", "service onBind");
		return null;
	}

	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// / mod
		if(lim==0)
		{
			lim++;
		Thread t = new serverThread();
		t.start();
			
		}
		else
		{
			Log.i("service", "server alread running");
		}
		// / mod

		return START_STICKY;
		// when you return start sticky the service will run until you
		// explicitly stop it
	}

	@Override
	public void onDestroy() {
	
		lim=0;
		Log.i("service", "I guess it is stopping now");
		Toast.makeText(this, "service stopped", Toast.LENGTH_LONG).show();
		super.onDestroy();

	}

}
