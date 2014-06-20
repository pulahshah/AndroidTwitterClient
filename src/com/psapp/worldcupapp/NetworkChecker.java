package com.psapp.worldcupapp;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker extends Activity{
	private static Crouton crouton;
	static Activity a;
	
	public static boolean checkConnection(Context context){
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
	}
	
	
	public static void showCrouton(Activity ab){
		a = ab;
		crouton = Crouton.makeText((Activity) ab, "No network connection!", Style.ALERT)
			    .setConfiguration(new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build());
		crouton.show();
	}
	
	public static void hideCrouton(){
		if(crouton != null){
			crouton.hide();
			Crouton.clearCroutonsForActivity(a);
		    
		}
	}
}
