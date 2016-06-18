package com.itshareplus.placesnearme.GoogleMapWebService;

import com.itshareplus.placesnearme.Model.MyLocation;
import com.itshareplus.placesnearme.Model.Route;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 5/31/2016.
 */
public interface GoogleMapsGeocodingListener {
    void onGoogleMapsGeocodingStart();
    void onGoogleMapsGeocodingSuccess(MyLocation location);
    void onGoogleMapsGeocodingFailed(String message);
}
