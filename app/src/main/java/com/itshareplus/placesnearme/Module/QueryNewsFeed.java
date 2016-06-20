package com.itshareplus.placesnearme.Module;

import com.itshareplus.placesnearme.Model.FBFeedItem;
import com.itshareplus.placesnearme.Model.FBFeedPhoto;
import com.itshareplus.placesnearme.Model.FBFeedUser;
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
public class QueryNewsFeed {
    String TAG_LOG = QueryNewsFeed.class.getName();
    final static String URL = "";

    QueryNewsFeedListener listener;
    String placeId;

    public QueryNewsFeed(QueryNewsFeedListener listener, String placeId) {
        this.listener = listener;
        this.placeId = placeId;
    }

    public void execute() {
        RequestParams params = new RequestParams();
        params.put("cid", "querynewfeeds");
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
                listener.OnQueryNewsFeedFailed("Query news feed failed!");
            }
        });
    }

    private void parseJSON(JSONObject response) {
        List<FBFeedItem> fbFeedItems = new ArrayList<>();

        try {
            int error = response.getInt("error");
            if (error == 1) {
                String error_message = response.getString("error_message");
                listener.OnQueryNewsFeedFailed(error_message);
                return;
            }

            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                FBFeedItem item = new FBFeedItem();

                JSONObject post = results.getJSONObject(i);
                item.postId = post.getInt("post_id");
                item.username = post.getString("username");
                item.name = post.getString("full_name");
                item.placeId = post.getInt("place_id");
                item.status = post.getString("status");
                item.time = post.getLong("created_at") - 7 * 3600;
                item.edited_at = post.getLong("modified_at");
                item.avatarURL = post.getString("avatar");

                //Add photos
                item.photos = new ArrayList<>();
                JSONArray photosArray = post.getJSONArray("photos");
                for (int j = 0; j < photosArray.length(); ++j) {
                    JSONObject photoObject = photosArray.getJSONObject(j);
                    String url = photoObject.getString("url");
                    long created_at = photoObject.getLong("created_at");
                    item.photos.add(new FBFeedPhoto(url, created_at ));
                }

                //Add likes
                item.likers = new ArrayList<>();
                JSONArray likesArray = post.getJSONArray("likes");
                for (int j = 0; j < likesArray.length(); ++j) {
                    JSONObject likeObject = likesArray.getJSONObject(j);
                    String fullName = likeObject.getString("full_name");
                    String userName = likeObject.getString("username");
                    item.likers.add(new FBFeedUser(fullName, userName));
                }

                fbFeedItems.add(item); //add items
            }

        } catch (JSONException e) {
            listener.OnQueryNewsFeedFailed("QueryNewFeed::Parse JSON Error!");
        }
        listener.OnQueryNewsFeedSuccess(fbFeedItems);
    }
}
