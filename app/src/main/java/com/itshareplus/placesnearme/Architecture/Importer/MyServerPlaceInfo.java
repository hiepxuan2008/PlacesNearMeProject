package com.itshareplus.placesnearme.Architecture.Importer;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class MyServerPlaceInfo {
    public String googlePlaceId;
    public String placeId;
    public String name;
    public String address;
    public double lat, lng;
    public String website;
    public String phoneNumber;
    public String internationalPhoneNumber;

    public MyServerPlaceInfo(String googlePlaceId, String placeId, String name, String address, double lat, double lng, String website, String phoneNumber, String internationalPhoneNumber) {
        this.googlePlaceId = googlePlaceId;
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.website = website;
        this.phoneNumber = phoneNumber;
        this.internationalPhoneNumber = internationalPhoneNumber;
    }
}
