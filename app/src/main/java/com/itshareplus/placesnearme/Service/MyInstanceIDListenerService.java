package com.itshareplus.placesnearme.Service;
import android.app.IntentService;
import android.content.Intent;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by TUAN TU on 6/16/2016.
 */
public class MyInstanceIDListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify of changes
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}