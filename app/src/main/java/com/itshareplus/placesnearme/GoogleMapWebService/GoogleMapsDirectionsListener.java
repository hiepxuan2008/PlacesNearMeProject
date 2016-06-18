package com.itshareplus.placesnearme.GoogleMapWebService;

import java.util.List;

import com.itshareplus.placesnearme.Model.Route;

/**
 * Created by Mai Thanh Hiep on 4/24/2016.
 */
public interface GoogleMapsDirectionsListener {
    void onGoogleMapsDirectionsStart();
    void onGoogleMapsDirectionsSuccess(List<Route> routes);
}
