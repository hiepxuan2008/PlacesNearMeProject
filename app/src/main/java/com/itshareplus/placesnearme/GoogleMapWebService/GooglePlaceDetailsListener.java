package com.itshareplus.placesnearme.GoogleMapWebService;

import com.itshareplus.placesnearme.Model.Place;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */
public interface GooglePlaceDetailsListener {
    void onGooglePlaceDetailsStart();
    void onGooglePlaceDetailsSuccess(Place place);
}
