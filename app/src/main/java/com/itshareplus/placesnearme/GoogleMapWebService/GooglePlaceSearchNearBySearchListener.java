package com.itshareplus.placesnearme.GoogleMapWebService;

import com.itshareplus.placesnearme.Model.PlaceList;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */
public interface GooglePlaceSearchNearBySearchListener {
    void onGooglePlaceSearchStart();
    void onGooglePlaceSearchSuccess(PlaceList placeList);
}
