package com.egongil.numva_android_app.src.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.egongil.numva_android_app.src.config.ApplicationClass;

public class ConnectionReceiver extends BroadcastReceiver {

    public static ConnectionReceiverListener connectionReceiverListener;

    public ConnectionReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

//        boolean isConnected;
//        if(cm.isDefaultNetworkActive()){
//            //인터넷 됨
//            isConnected=true;
//        }
//        else{
//            //인터넷 안됨
//            isConnected=false;
//        }

        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();

        if(connectionReceiverListener!=null){
            connectionReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) ApplicationClass.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

//        if(cm.isDefaultNetworkActive()){
//            return true;
//        }
//        else if(!cm.isDefaultNetworkActive()){
//            return false;
//        }
//        else{
//            return false;
//        }

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

}
