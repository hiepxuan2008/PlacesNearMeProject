package com.itshareplus.placesnearme.Model;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class FBFeedPhoto {
    public String url;
    public long created_at;

    public FBFeedPhoto(String url, long created_at) {
        this.url = url;
        this.created_at = created_at;
    }
}
