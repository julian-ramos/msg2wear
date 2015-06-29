package com.ramos.julian.msg2wear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements MessageApi.MessageListener, GoogleApiClient.ConnectionCallbacks{

    private TextView mTextView;
    private GoogleApiClient mApiClient;
    private static final String WEAR_MESSAGE_PATH = "/message";
    String TAG = "wear-main";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
        intent = new Intent(this, wearServiceListener.class);
        startService(intent);
    }

    private void initGoogleApiClient() {
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks( this )
                .build();

        if( mApiClient != null && !( mApiClient.isConnected() || mApiClient.isConnecting() ) )
            mApiClient.connect();
    }


    public void onMessageReceived( final MessageEvent messageEvent ) {
        runOnUiThread( new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, String.format("%s << this was the message", messageEvent.getPath()));

            }
        });
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}

/*TODO
* In the next website
* It is quite nicely explained how to send data through the Data layer api. Apparently you can send messages
* either way wear-mobile mobile-wear. Then I need to implement a way to basically sync the data on the watch to the
* phone once either the app is quit or at request of the user.(Need to think about this)
* Either way remember to implement a way that the data sent has been received
* Check how big can be the data set sent through a dataamp as in the example
* Also improve the code using the example because I'm not killing the google api connection when disconnecting
* and doing some other necessary stuff to make the app work nicely
* Also check because maybe the easiest way to do all this is through the backup api
* http://developer.android.com/training/cloudsync/backupapi.html
* Also check this
* http://developer.android.com/training/sync-adapters/index.html
* it is yet another way to transfer data from the app to the web
*
* */
