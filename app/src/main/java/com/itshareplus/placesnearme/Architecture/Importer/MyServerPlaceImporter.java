package com.itshareplus.placesnearme.Architecture.Importer;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class MyServerPlaceImporter extends PlaceImporter {
    @Override
    public PlaceInfo Convert(Object input) {
        MyServerPlaceInfo info =(MyServerPlaceInfo)input;
        PlaceInfo result = new PlaceInfo();
        result.googlePlaceId = info.googlePlaceId;
        result.placeId = info.placeId;
        result.name = info.name;
        result.address = info.address;
        result.website = info.website;
        result.phoneNumber = info.phoneNumber;
        result.internationalPhoneNumber = info.internationalPhoneNumber;
        result.lat = info.lat;
        result.lng = info.lng;
        result.rating = "0";
        return result;
    }

    @Override
    public boolean IsValidInput(Object input) {
        try {
            MyServerPlaceInfo info = (MyServerPlaceInfo) input;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
