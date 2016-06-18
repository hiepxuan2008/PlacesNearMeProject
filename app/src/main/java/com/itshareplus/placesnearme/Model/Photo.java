package com.itshareplus.placesnearme.Model;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */
public class Photo {
    String mPhotoReference;
    int mWidthMax;
    int mHeightMax;

    public Photo(String mPhotoReference, int mWidthMax, int mHeightMax) {
        this.mPhotoReference = mPhotoReference;
        this.mWidthMax = mWidthMax;
        this.mHeightMax = mHeightMax;
    }
}