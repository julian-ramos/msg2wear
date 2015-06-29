package com.ramos.julian.msg2wear;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class wearServiceListener extends WearableListenerService {
    private static final String START_ACTIVITY = "/start_activity";
    String TAG="wear-Service";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if( messageEvent.getPath().equalsIgnoreCase( START_ACTIVITY ) ) {
            Intent intent = new Intent( this, MainActivity.class );
            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            Log.d(TAG, String.format("Received the message! %s",messageEvent.getPath()));
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

}
