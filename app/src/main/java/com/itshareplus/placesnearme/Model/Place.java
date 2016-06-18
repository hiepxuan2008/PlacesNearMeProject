package com.itshareplus.placesnearme.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */

@Root
public class Place {
    @Element
    public MyLocation mLocation;
    @Element(required=false)
    public String mIcon;
    @Element
    public String mName;
    @Element
    public String mPlaceId;
    @Element
    public String mVicinity;
    @Element(required=false)
    public String mPhotoReference;
    @Element(required=false)
    public String mRating;
    @Element(required=false)
    public PlaceDetails mPlaceDetails;

    public Place(MyLocation mLocation, String mIcon, String mName, String mPlaceId, String mVicinity, String mPhotoReference, String mRating, PlaceDetails mPlaceDetails) {
        this.mLocation = mLocation;
        this.mIcon = mIcon;
        this.mName = mName;
        this.mPlaceId = mPlaceId;
        this.mVicinity = mVicinity;
        this.mPhotoReference = mPhotoReference;
        this.mRating = mRating;
        this.mPlaceDetails = mPlaceDetails;
    }

    @Override
    public String toString() {
        return "Place {" + "icon=" + mIcon + ", name=" + mName + ", place_id="
                + mPlaceId + ", vicinity=" + mVicinity + ", photo="
                + mPhotoReference + ", rating=" + mRating + ", location="
                + mLocation.latitude + "," + mLocation.longitude + "}";
    }

    public Place Clone() {
        return new Place(mLocation, mIcon, mName, mPlaceId, mVicinity, mPhotoReference, mRating, mPlaceDetails);
    }
}