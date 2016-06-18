package com.itshareplus.placesnearme.GoogleMapWebService;

import android.net.Uri;
import android.util.Log;

import com.itshareplus.placesnearme.Model.MyLocation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Mai Thanh Hiep on 5/31/2016.
 */
public class GoogleMapsGeocoding extends GoogleMapWebservice{
    private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json?";
    private final String TAG_LOG = GoogleMapsGeocoding.class.getName();
    private final GoogleMapsGeocodingListener listener;

    String addressText;

    public GoogleMapsGeocoding(GoogleMapsGeocodingListener listener, String addressText) {
        this.listener = listener;
        this.addressText = addressText;
    }

    public void execute() {
        listener.onGoogleMapsGeocodingStart();
        new GoogleMapsGeocodingDownloadJsonData().execute(createURL());
    }

    private void parseJSON(String data) {
        Log.d(TAG_LOG, "parseJSON");
        MyLocation resLocation = null;

        try {
            JSONObject jsonData = new JSONObject(data);
            if (!jsonData.getString("status").equals("OK")) {
                Log.e(TAG_LOG, jsonData.getString("status"));
                throw new Exception("Query Data Failed!");
            }

            JSONArray jsonResults = jsonData.getJSONArray("results");
            JSONObject jsonResult = jsonResults.getJSONObject(0);
            JSONObject jsonGeometry = jsonResult.getJSONObject("geometry");
            JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
            double latitude = jsonLocation.getDouble("lat");
            double longitude = jsonLocation.getDouble("lng");
            resLocation = new MyLocation(latitude, longitude);

        } catch (Exception e) {
            Log.e(TAG_LOG, "error", e);
            listener.onGoogleMapsGeocodingFailed(e.getMessage());
            return;
        }
        Log.d(TAG_LOG, "Finish parseJSON successfully!");
        listener.onGoogleMapsGeocodingSuccess(resLocation);
    }

    public String createURL() {
        Uri.Builder builder = Uri.parse(URL).buildUpon();
        try {
            builder.appendQueryParameter("address", URLEncoder.encode(this.addressText, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        builder.appendQueryParameter("key", API_KEY);

        return builder.build().toString();
    }

    protected class GoogleMapsGeocodingDownloadJsonData extends DownloadRawData {

        @Override
        protected void onPostExecute(String result) {
            parseJSON(result);
        }
    }
}
