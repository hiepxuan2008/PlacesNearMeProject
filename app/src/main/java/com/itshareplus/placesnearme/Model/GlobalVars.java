package com.itshareplus.placesnearme.Model;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */


public class GlobalVars {
    public enum DrawerType {KEYWORDS, SEARCH_PLACES, FAVORITE_PLACES};

    public static MyLocation location = new MyLocation(10.7629886, 106.6821975);
    public static PlaceList currentPlaceList;
    public static Place currentPlace;
    public static HashMap<Marker, Place> markerData = new HashMap<>();
    public static KeywordItem keywordItem;
    public static DrawerType drawer;
    public static String currentPhotoPath;

    public static boolean IsFakeGPS = false;
    public static MyLocation fakeLocation = new MyLocation(10.7629886, 106.6821975); //University of Science, District 5

    public static MyLocation getUserLocation() {
        if (IsFakeGPS) {
            return fakeLocation;
        } else {
            return location;
        }
    }
}
