package com.itshareplus.placesnearme.Module;

import com.itshareplus.placesnearme.Model.FBFeedItem;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public interface PostStatusListener {
    void OnPostStatusStart();
    void OnPostStatusSuccess();
    void OnPostStatusFailed(String error_message);
}
