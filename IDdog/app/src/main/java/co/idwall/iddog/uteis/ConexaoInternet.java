package co.idwall.iddog.uteis;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConexaoInternet {
	public static boolean temInternet(Context c){
    	ConnectivityManager manager = (ConnectivityManager) c.getSystemService(Activity.CONNECTIVITY_SERVICE);
    	Boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
    	Boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
    	NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    	if(is3g) {
    		return true;
    	} else if (isWifi) {
    		return true;
    	} else if (networkInfo != null && networkInfo.isConnected()) {
    		return true;
    	} else {
    	  return false;
    	}
    }
}
