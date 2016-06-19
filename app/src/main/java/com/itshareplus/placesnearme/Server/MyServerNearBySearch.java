package com.itshareplus.placesnearme.Server;

import com.itshareplus.placesnearme.Architecture.Importer.MyServerPlaceInfo;
import com.itshareplus.placesnearme.Module.RequestToServer;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public class MyServerNearBySearch {
    String TAG_LOG = MyServerNearBySearch.class.getName();
    final static String URL = "";

    MyServerNearBySearchListener listener;
    double lat, lng;
    String keyword;

    public MyServerNearBySearch(MyServerNearBySearchListener listener, double lat, double lng, String keyword) {
        this.listener = listener;
        this.lat = lat;
        this.lng = lng;
        this.keyword = keyword;
    }

    public void execute() {
        RequestParams params = new RequestParams();
        params.put("cid", "nearbysearch");
        params.put("lat", String.valueOf(lat));
        params.put("lng", String.valueOf(lng));
        params.put("keyword", keyword);
        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                parseJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.OnMyServerNearBySearchFailed("Query news feed failed!");
            }
        });
    }

    private void parseJSON(JSONObject response) {
        List<MyServerPlaceInfo> MyServerPlaceInfos = new ArrayList<>();

        try {
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject post = results.getJSONObject(i);
                MyServerPlaceInfo item = new MyServerPlaceInfo(post.getString("google_place_id"), post.getInt("id"), post.getString("name"), post.getString("address"), post.getDouble("lat"), post.getDouble("lng"), post.getString("website"), post.getString("phone_number"), post.getString("inter_phone_number"), post.getString("created_at"), post.getString("modified_at"));
                MyServerPlaceInfos.add(item); //add items
            }

        } catch (JSONException e) {
            listener.OnMyServerNearBySearchFailed("MyServerNearBySearch::Parse JSON Error!");
        }
        listener.OnMyServerNearBySearchSuccess(MyServerPlaceInfos);
    }
}
