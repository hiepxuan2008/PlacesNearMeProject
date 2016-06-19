package com.itshareplus.placesnearme.Module;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public interface FollowThisPlaceListener {
    void OnFollowThisPlaceStart();
    void OnFollowThisPlaceSuccess(int type);
    void OnFollowThisPlaceFailed(String error_message);
}
