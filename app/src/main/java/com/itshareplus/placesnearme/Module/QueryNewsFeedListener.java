package com.itshareplus.placesnearme.Module;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */

import com.itshareplus.placesnearme.Model.FBFeedItem;

import java.util.List;

public interface QueryNewsFeedListener {
    void OnQueryNewsFeedStart();
    void OnQueryNewsFeedSuccess(List<FBFeedItem> fbFeedItems);
    void OnQueryNewsFeedFailed(String error_message);
}
