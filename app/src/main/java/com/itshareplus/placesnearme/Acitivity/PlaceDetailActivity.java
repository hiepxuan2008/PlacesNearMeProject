package com.itshareplus.placesnearme.Acitivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.github.clans.fab.FloatingActionButton;
import com.itshareplus.placesnearme.Adapter.FBFeedItemAdapter;
import com.itshareplus.placesnearme.Adapter.ImageGalleryAdapter;
import com.itshareplus.placesnearme.Architecture.FrameworkTemplate.FeedItemsManager;
import com.itshareplus.placesnearme.Architecture.FrameworkTemplate.FilterHelperByLike;
import com.itshareplus.placesnearme.Architecture.FrameworkTemplate.FilterHelperByTimeLine;
import com.itshareplus.placesnearme.GoogleMapWebService.GoogleMapsDirections;
import com.itshareplus.placesnearme.GoogleMapWebService.GooglePlaceDetails;
import com.itshareplus.placesnearme.GoogleMapWebService.GooglePlaceDetailsListener;
import com.itshareplus.placesnearme.Manager.FavoritePlacesManager;
import com.itshareplus.placesnearme.Model.FBFeedItem;
import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Model.Place;
import com.itshareplus.placesnearme.Model.PlaceDetails;
import com.itshareplus.placesnearme.Module.FollowThisPlace;
import com.itshareplus.placesnearme.Module.FollowThisPlaceListener;
import com.itshareplus.placesnearme.Module.QueryNewsFeed;
import com.itshareplus.placesnearme.Module.QueryNewsFeedListener;
import com.itshareplus.placesnearme.Module.RequestToServer;
import com.itshareplus.placesnearme.R;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class PlaceDetailActivity extends AppCompatActivity implements
        View.OnClickListener,
        GooglePlaceDetailsListener, QueryNewsFeedListener, FollowThisPlaceListener {

    final String TAG_LOG = this.getClass().getSimpleName();
    private static final int TAKE_PICTURE_AND_UPLOAD = 2000;
    ImageView btnBack;
    ImageView btnFavorite;
    ImageView btnShare;
    ImageView btnDriving;
    ImageView btnCycling;
    ImageView btnWalking;
    ImageView btnAddToCalender;

    TextView txtDistance;
    TextView txtPlaceName;
    TextView txtPlaceAddress;
    TextView txtContact;
    TextView txtInternationalContact;
    TextView txtWebsite;

    RelativeLayout frameContact;
    RelativeLayout frameWebsite;
    HorizontalScrollView frameImageGallery;
    Place place;

    ProgressBar progressBarLoading;
    ListView listViewNewsFeed;

    ImageGalleryAdapter adapterImageGallery;
    FBFeedItemAdapter adapterNewsFeed;

    //Floating Action Buttons
    FloatingActionButton fabUploadPhoto;
    FloatingActionButton fabCommentRating;
    FloatingActionButton fabAddToCalendar;

    FloatingActionButton fabFilterByLike;
    FloatingActionButton fabFilterByTimeLine;

    FeedItemsManager feedItemsManager = new FeedItemsManager(new ArrayList<FBFeedItem>());

    int is_following = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        init();
    }
    private void init() {
        initComponents();
        place = GlobalVars.currentPlace;
        displayBasicPlaceInfo(place);
        new GooglePlaceDetails(this, place).execute();

        //Load news feed
        loadNewsFeed();
        checkFollowPlaceState();
    }

    private void loadNewsFeed() {
        new QueryNewsFeed(this, place.mPlaceId).execute();
    }

    private void initComponents() {
        frameContact = (RelativeLayout) findViewById(R.id.frameContact);
        frameWebsite = (RelativeLayout) findViewById(R.id.frameWebsite);
        frameImageGallery = (HorizontalScrollView) findViewById(R.id.frameImageGallery);
        frameContact.setVisibility(View.GONE);
        frameWebsite.setVisibility(View.GONE);
        frameImageGallery.setVisibility(View.GONE);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnFavorite = (ImageView) findViewById(R.id.btnFavorite);
        btnShare = (ImageView) findViewById(R.id.btnShare);
        btnCycling = (ImageView) findViewById(R.id.btnCycling);
        btnDriving = (ImageView) findViewById(R.id.btnDriving);
        btnWalking = (ImageView) findViewById(R.id.btnWalking);

        txtDistance = (TextView) findViewById(R.id.txtDistance);
        txtPlaceName = (TextView) findViewById(R.id.txtPlaceName);
        txtPlaceAddress = (TextView) findViewById(R.id.txtPlaceAddress);
        txtWebsite = (TextView) findViewById(R.id.txtWebsite);
        txtContact = (TextView) findViewById(R.id.txtContact);
        txtInternationalContact = (TextView) findViewById(R.id.txtInternationalContact);

        btnBack.setOnClickListener(this);
        btnFavorite.setOnClickListener(this);
        btnShare.setOnClickListener(this);

        btnCycling.setOnClickListener(this);
        btnDriving.setOnClickListener(this);
        btnWalking.setOnClickListener(this);

        txtWebsite.setOnClickListener(this);
        txtContact.setOnClickListener(this);
        txtInternationalContact.setOnClickListener(this);

        progressBarLoading = (ProgressBar) findViewById(R.id.progressBarLoading);
        progressBarLoading.setVisibility(View.VISIBLE);

        //Photos
        LinearLayout linearLayoutImageGallery = (LinearLayout) findViewById(R.id.llImageGallery);
        List<String> images = new ArrayList<>();
        adapterImageGallery = new ImageGalleryAdapter(this, linearLayoutImageGallery, images);

        //Facebook Feeds
        listViewNewsFeed = (ListView) findViewById(R.id.listViewReview);
        List<FBFeedItem> reviews = new ArrayList<>();
        adapterNewsFeed = new FBFeedItemAdapter(this, R.layout.item_feed_fb, reviews);
        listViewNewsFeed.setAdapter(adapterNewsFeed);

        //Floating Action Buttons
        fabUploadPhoto = (FloatingActionButton) findViewById(R.id.fabUploadPhoto);
        fabUploadPhoto.setOnClickListener(this);
        fabCommentRating = (FloatingActionButton) findViewById(R.id.fabCommentRating);
        fabCommentRating.setOnClickListener(this);
        fabAddToCalendar = (FloatingActionButton) findViewById(R.id.fabAddToCalendar);
        fabAddToCalendar.setOnClickListener(this);

        fabFilterByLike = (FloatingActionButton) findViewById(R.id.fabFilterByLike);
        fabFilterByLike.setOnClickListener(this);
        fabFilterByTimeLine = (FloatingActionButton) findViewById(R.id.fabFilterByTimeLine);
        fabFilterByTimeLine.setOnClickListener(this);
    }

    private void displayBasicPlaceInfo(Place place) {
        if (place == null)
            return;

        txtPlaceName.setText(place.mName);
        txtPlaceAddress.setText(place.mVicinity);
        txtDistance.setText(place.mLocation.distanceTo(GlobalVars.getUserLocation()));

//        if (FavoritePlacesManager.getInstance().IsExist(place)) {
//            btnFavorite.setImageResource(R.drawable.icon_fav_active);
//        } else {
//            btnFavorite.setImageResource(R.drawable.icon_fav_normal_stroke);
//        }
    }

    private void checkFollowPlaceState() {
        RequestParams params = new RequestParams();
        params.put("cid", "is_following_place");
        params.put("user_id", GlobalVars.getUserId());
        params.put("place_id", GlobalVars.currentPlace.mPlaceId);

        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int error = response.getInt("error");
                    if (error == 1) {
                        String error_message = response.getString("error_message");
                        Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    is_following = response.getInt("is_following");
                    updateFollowingPlaceState();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "checkFollowPlaceState::Parse JSON failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Check following status failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                this.finish();
                break;
            case R.id.btnFavorite:
                addToFavorite();
                break;
            case R.id.btnShare:
                shareThisPlace();
                break;
            case R.id.btnCycling:
                onCycling();
                break;
            case R.id.btnDriving:
                onDriving();
                break;
            case R.id.btnWalking:
                onWalking();
                break;
            case R.id.txtWebsite:
                onWebsite();
                break;
            case R.id.txtContact:
                onContact();
                break;
            case R.id.txtInternationalContact:
                onInternationalContact();
                break;
            case R.id.fabUploadPhoto: {
                onUploadPhoto();
                break;
            }
            case R.id.fabCommentRating: {
                onPostStatus();
                break;
            }
            case R.id.fabAddToCalendar: {
                onAddToCalendar();
                break;
            }
            case R.id.fabFilterByLike: {
                onFilterByLike();
                break;
            }
            case R.id.fabFilterByTimeLine: {
                onFilterByTimeLine();
                break;
            }
        }
    }

    private void onFilterByTimeLine() {
        feedItemsManager.Sort(new FilterHelperByTimeLine());
        adapterNewsFeed.update(feedItemsManager.items);
    }

    private void onFilterByLike() {
        feedItemsManager.Sort(new FilterHelperByLike());
        adapterNewsFeed.update(feedItemsManager.items);
    }

    private void onPostStatus() {
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

    private void updateFollowingPlaceState() {
        if (is_following == 1) {
            btnFavorite.setImageResource(R.drawable.icon_fav_active);
        } else {
            btnFavorite.setImageResource(R.drawable.icon_fav_normal_stroke);
        }
    }

    private void onAddToCalendar() {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, place.mPlaceDetails.formattedAddress);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000); //1 hour later
        intent.putExtra(CalendarContract.Events.TITLE, "Event going to \"" + GlobalVars.currentPlace.mName + "\"");
        intent.putExtra(CalendarContract.Events.DESCRIPTION,
                String.format("Maps: %s\nPhone: %s - %s\nWebsite: %s",
                String.format("http://maps.google.com/maps?q=loc:%f,%f8&z=20", place.mLocation.latitude, place.mLocation.longitude),
                place.mPlaceDetails.localPhoneNumber,
                place.mPlaceDetails.internationalPhoneNumber,
                place.mPlaceDetails.website
        ));
        startActivity(intent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        GlobalVars.currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void onUploadPhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePhotoIntent, TAKE_PICTURE_AND_UPLOAD);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Can't invoke Camera Intent", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE_AND_UPLOAD:
                if (resultCode == Activity.RESULT_OK) {
                    uploadPhotoToServer();
                }
                break;
        }
    }

    private void uploadPhotoToServer() {
        RequestParams params = new RequestParams();
        params.put("cid", "upload_photo");
        params.put("place_id", GlobalVars.currentPlace.mPlaceId);
        File file = new File(GlobalVars.currentPhotoPath);
        try {
            params.put("upload_file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG_LOG, "File not found!");
        }

        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("error") == 0) {
                        Toast.makeText(getApplicationContext(), "Upload Success!", Toast.LENGTH_SHORT).show();
                        reloadPhotoGallery();
                    } else {
                        String error_message = response.getString("error_message");
                        Toast.makeText(getApplicationContext(), "Upload Failed!\n" + error_message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reloadPhotoGallery() {
        RequestParams params = new RequestParams();
        params.put("cid", "query_photo");
        params.put("place_id", GlobalVars.currentPlace.mPlaceId);
        RequestToServer.sendRequest("handler.php", RequestToServer.Method.POST, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getInt("error") == 0) {
                        JSONArray jsonResults = response.getJSONArray("results");
                        List<String> images = new ArrayList<String>();
                        for (int i = 0; i < jsonResults.length(); ++i) {
                            JSONObject jsonResult = jsonResults.getJSONObject(i);
                            images.add(jsonResult.getString("link"));
                        }
                        adapterImageGallery.setData(images);
                        adapterImageGallery.notifyDataSetChanged();
                        frameImageGallery.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Failed to reload photo gallery!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onInternationalContact() {
        String phoneNumber = txtInternationalContact.getText().toString();
        phoneNumber = phoneNumber.replace(" ", "");
        Uri number = Uri.parse("tel: " + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    private void onContact() {
        String phoneNumber = txtContact.getText().toString();
        phoneNumber = phoneNumber.replace(" ", "");
        Uri number = Uri.parse("tel: " + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    private void onWebsite() {
        String url = txtWebsite.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void onWalking() {
        Intent intent = new Intent(this, PlaceDirectionActivity.class);
        intent.putExtra("mode", GoogleMapsDirections.TRAVEL_MODE_WALKING);
        startActivity(intent);
    }

    private void onDriving() {
        Intent intent = new Intent(this, PlaceDirectionActivity.class);
        intent.putExtra("mode", GoogleMapsDirections.TRAVEL_MODE_DRIVING);
        startActivity(intent);
    }

    private void onCycling() {
        Intent intent = new Intent(this, PlaceDirectionActivity.class);
        intent.putExtra("mode", GoogleMapsDirections.TRAVEL_MODE_BICYCLING);
        startActivity(intent);
    }

    private void shareThisPlace() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareTitle = "PlacesNearMe - Sharing a place";
        String shareBody = place.mName + "\r\n" +
                place.mVicinity + "\r\n" +
                String.format("http://maps.google.com/maps?q=loc:%f,%f8&z=20", place.mLocation.latitude, place.mLocation.longitude);
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void addToFavorite() {
        if (is_following == 1) {
            //Toast.makeText(this, GlobalVars.getUserId(), Toast.LENGTH_SHORT).show();
            new FollowThisPlace(this, GlobalVars.getUserId(), GlobalVars.currentPlace.mPlaceId, 0).execute();
        } else {
            //Toast.makeText(this, GlobalVars.getUserId(), Toast.LENGTH_SHORT).show();
            new FollowThisPlace(this, GlobalVars.getUserId(), GlobalVars.currentPlace.mPlaceId, 1).execute();
        }


//        if (FavoritePlacesManager.getInstance().IsExist(GlobalVars.currentPlace)) {
//            if (FavoritePlacesManager.getInstance().RemoveThisPlace(GlobalVars.currentPlace)) {
//                btnFavorite.setImageResource(R.drawable.icon_fav_normal_stroke);
//                FavoritePlacesManager.getInstance().SaveData();
//            }
//        } else {
//            if (FavoritePlacesManager.getInstance().AddThisPlace(GlobalVars.currentPlace)) {
//                btnFavorite.setImageResource(R.drawable.icon_fav_active);
//                FavoritePlacesManager.getInstance().SaveData();
//            }
//        }
    }

    @Override
    public void onGooglePlaceDetailsStart() {

    }

    @Override
    public void onGooglePlaceDetailsSuccess(Place place) {
        progressBarLoading.setVisibility(View.GONE);

        if (place == null || place.mPlaceDetails == null)
            return;

        //
        PlaceDetails placeDetails = place.mPlaceDetails;
        //Place Address
        txtPlaceAddress.setText(placeDetails.formattedAddress);
        //Website
        if (placeDetails.website != null) {
            frameWebsite.setVisibility(View.VISIBLE);
            txtWebsite.setText(placeDetails.website);
        }

        //Contact
        if (placeDetails.localPhoneNumber != null || placeDetails.internationalPhoneNumber != null) {
            frameContact.setVisibility(View.VISIBLE);
            txtContact.setText(placeDetails.localPhoneNumber);
            txtInternationalContact.setText(placeDetails.internationalPhoneNumber);
        }
    }

    @Override
    public void OnQueryNewsFeedStart() {

    }

    @Override
    public void OnQueryNewsFeedSuccess(List<FBFeedItem> fbFeedItems) {
        feedItemsManager.items = fbFeedItems;
        onFilterByTimeLine();
    }

    @Override
    public void OnQueryNewsFeedFailed(String error_message) {
        Toast.makeText(this, error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNewsFeed();
    }

    @Override
    public void OnFollowThisPlaceStart() {

    }

    @Override
    public void OnFollowThisPlaceSuccess(int type) {
        is_following = type;
        updateFollowingPlaceState();
    }

    @Override
    public void OnFollowThisPlaceFailed(String error_message) {
        Toast.makeText(this, error_message, Toast.LENGTH_SHORT).show();
    }
}
