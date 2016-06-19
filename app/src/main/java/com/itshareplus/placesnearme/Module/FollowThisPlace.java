package com.itshareplus.placesnearme.Module;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public class FollowThisPlace {
    String TAG_LOG = QueryNewsFeed.class.getName();
    final static String URL = "";

    FollowThisPlaceListener listener;
    int userId;
    String placeId;
    int type;

    public FollowThisPlace(FollowThisPlaceListener listener, int userId, String placeId, int type) {
        this.listener = listener;
        this.userId = userId;
        this.placeId = placeId;
        this.type = type;
    }

    public void execute() {
        RequestParams params = new RequestParams();

        if (type == 1) {
            params.put("cid", "follow_place");
        } else if (type == 0) {
            params.put("cid", "unfollow_place");
        }

        params.put("user_id", String.valueOf(userId));
        params.put("place_id", placeId);

        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                parseJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (type == 1)
                    listener.OnFollowThisPlaceFailed("Follow this place failed!");
                else
                    listener.OnFollowThisPlaceFailed("UnFollow this place failed!");
            }
        });
    }

    private void parseJSON(JSONObject response) {
        try {
            int error = response.getInt("error");
            if (error == 1) {
                String error_message = response.getString("error_message");
                listener.OnFollowThisPlaceFailed(error_message);
                return;
            }

            listener.OnFollowThisPlaceSuccess(type);
        } catch (Exception e) {
            listener.OnFollowThisPlaceFailed("parseJSON failed");
        }
    }
}

