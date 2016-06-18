package com.itshareplus.placesnearme.GPS;

import android.location.Location;
import android.os.Bundle;

/**
 * Created by Mai Thanh Hiep on 4/21/2016.
 */
public interface GPSTrackerListener {
    void onGPSTrackerLocationChanged(Location location);
    void onGPSTrackerStatusChanged(String provider, int status, Bundle extras);
}
