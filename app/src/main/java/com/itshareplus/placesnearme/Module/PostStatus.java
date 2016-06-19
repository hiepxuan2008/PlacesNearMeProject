package com.itshareplus.placesnearme.Module;

import com.itshareplus.placesnearme.Model.FBFeedItem;
import com.itshareplus.placesnearme.Model.FBFeedPhoto;
import com.itshareplus.placesnearme.Model.FBFeedUser;
import com.itshareplus.placesnearme.Model.GlobalVars;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class PostStatus {
    String TAG_LOG = QueryNewsFeed.class.getName();
    final static String URL = "";

    PostStatusListener listener;
    int userId;
    String placeId;
    String status;
    String photos[];

    public PostStatus(PostStatusListener listener, int userId, String placeId, String status, String photos[]) {
        this.listener = listener;
        this.userId = userId;
        this.placeId = placeId;
        this.status = status;
        this.photos = photos;
    }

    public void execute() {
        RequestParams params = new RequestParams();
        params.put("cid", "createnewfeed");
        params.put("user_id", String.valueOf(userId));
        params.put("place_id", placeId);
        params.put("status", status);

        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                parseJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.OnPostStatusFailed("Post status failed!");
            }
        });
    }

    private void parseJSON(JSONObject response) {
        try {
            int error = response.getInt("error");
            if (error == 1) {
                String error_message = response.getString("error_message");
                listener.OnPostStatusFailed(error_message);
                return;
            }
        } catch (Exception e) {
            listener.OnPostStatusFailed("parseJSON failed");
        }
        listener.OnPostStatusSuccess();
    }
}
