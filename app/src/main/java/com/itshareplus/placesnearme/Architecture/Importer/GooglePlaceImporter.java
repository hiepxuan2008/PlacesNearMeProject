package com.itshareplus.placesnearme.Architecture.Importer;

import com.itshareplus.placesnearme.Model.Place;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class GooglePlaceImporter extends PlaceImporter {
    @Override
    public PlaceInfo Convert(Object input) {
        Place info =(Place)input;
        PlaceInfo result = new PlaceInfo();
        result.googlePlaceId = info.mPlaceId;
        result.placeId = -1;
        result.name = info.mName;
        result.address = info.mVicinity;
        result.website = info.mPlaceDetails != null ? info.mPlaceDetails.website : null;
        result.phoneNumber = info.mPlaceDetails != null ? info.mPlaceDetails.localPhoneNumber : null;
        result.internationalPhoneNumber = info.mPlaceDetails != null ? info.mPlaceDetails.internationalPhoneNumber : null;
        result.lat = info.mLocation.latitude;
        result.lng = info.mLocation.longitude;
        result.rating = info.mRating;
        return result;
    }

    @Override
    public boolean IsValidInput(Object input) {
        try {
            Place info = (Place) input;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
