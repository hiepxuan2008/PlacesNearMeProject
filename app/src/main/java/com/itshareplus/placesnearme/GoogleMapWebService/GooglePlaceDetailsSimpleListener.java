package com.itshareplus.placesnearme.GoogleMapWebService;

import com.itshareplus.placesnearme.Architecture.Importer.PlaceInfo;
import com.itshareplus.placesnearme.Model.Place;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public interface GooglePlaceDetailsSimpleListener {
    void onGooglePlaceDetailsSimpleStart();
    void onGooglePlaceDetailsSimpleFailed();
    void onGooglePlaceDetailsSimpleSuccess(PlaceInfo place);
}
