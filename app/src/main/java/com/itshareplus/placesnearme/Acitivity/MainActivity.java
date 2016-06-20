package com.itshareplus.placesnearme.Acitivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itshareplus.placesnearme.Adapter.KeywordsAdapter;
import com.itshareplus.placesnearme.Adapter.OverviewPlaceInfoWindowAdapter;
import com.itshareplus.placesnearme.Adapter.PlacesAdapter;
import com.itshareplus.placesnearme.Architecture.Importer.GooglePlaceImporter;
import com.itshareplus.placesnearme.Architecture.Importer.MyServerPlaceImporter;
import com.itshareplus.placesnearme.Architecture.Importer.MyServerPlaceInfo;
import com.itshareplus.placesnearme.Architecture.Importer.PlaceInfo;
import com.itshareplus.placesnearme.GPS.GPSTracker;
import com.itshareplus.placesnearme.GPS.GPSTrackerListener;
import com.itshareplus.placesnearme.GoogleMapWebService.GooglePlaceSearchNearBySearch;
import com.itshareplus.placesnearme.GoogleMapWebService.GooglePlaceSearchNearBySearchListener;
import com.itshareplus.placesnearme.Manager.FavoritePlacesManager;
import com.itshareplus.placesnearme.Manager.KeywordItemManager;
import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Model.KeywordItem;
import com.itshareplus.placesnearme.Model.MyLocation;
import com.itshareplus.placesnearme.Model.Place;
import com.itshareplus.placesnearme.Model.PlaceList;
import com.itshareplus.placesnearme.Module.PlayerPrefs;
import com.itshareplus.placesnearme.R;
import com.itshareplus.placesnearme.Server.MyServerNearBySearch;
import com.itshareplus.placesnearme.Server.MyServerNearBySearchListener;
import com.itshareplus.placesnearme.Service.RegistrationIntentService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GooglePlaceSearchNearBySearchListener,
        GPSTrackerListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener, MyServerNearBySearchListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String TAG = MainActivity.class.getName();
    private GoogleMap mMap;
    private OverviewPlaceInfoWindowAdapter overviewPlaceInfoWindowAdapter;
    private GPSTracker gpsTracker;
    private List<Marker> markers = new ArrayList<>();
    private Marker userLocationMarker;

    private ProgressDialog progressDialog;

    private DrawerLayout drawerLayout;
    //Left navigation drawer
    private ListView lvKeywords;
    private KeywordsAdapter adapterKeyword;
    private ImageView btnOpenKeywordDrawer;

    //Right navigation drawer - Result Places
    private ListView lvPlaces;
    private PlacesAdapter adapterPlace;
    private ImageView btnOpenPlaceDrawer;

    //Right navigation drawer - Favorite Places
    private ImageView btnOpenFavPlaceDrawer;
    private ListView lvFavPlaces;
    private PlacesAdapter adapterFavPlace;

    //Buttons
    private ImageView btnSetting;
    private ImageView btnMyLocation;
    private ImageView btnLogin;

    boolean isGoogleDone = false, isMyServerDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        initialize();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void initialize() {
        PlayerPrefs.Initialize(getApplicationContext());

        initMap();
        initComponents();
        initGPS();
        FavoritePlacesManager.Initialize(getApplicationContext());
    }

    private void initGPS() {
        gpsTracker = new GPSTracker(this, this);
        if (gpsTracker.canGetLocation()) {
            double lat = gpsTracker.getLatitude();
            double lng = gpsTracker.getLongitude();
            Toast.makeText(getApplicationContext(), lat + " " + lng, Toast.LENGTH_SHORT).show();
            if (lat != 0 || lng != 0) {
                GlobalVars.location = new MyLocation(lat, lng);
            }
        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    private void initComponents() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawLayout);
        lvKeywords = (ListView) findViewById(R.id.lvKeywords);
        List<KeywordItem> keywordItemList = KeywordItemManager.populateDefaultKeywordItems();

        //Keywords Navigation Drawer
        adapterKeyword = new KeywordsAdapter(this, R.layout.item_place_keyword, keywordItemList);
        lvKeywords.setAdapter(adapterKeyword);
        lvKeywords.setOnItemClickListener(this);
        btnOpenKeywordDrawer = (ImageView) findViewById(R.id.btnOpenKeywordDrawer);
        btnOpenKeywordDrawer.setOnClickListener(this);

        //Search Places Navigation Drawer
        List<PlaceInfo> places = new ArrayList<>();
        adapterPlace = new PlacesAdapter(this, R.layout.item_place_overview, places);
        lvPlaces = (ListView) findViewById(R.id.lvPlaces);
        lvPlaces.setAdapter(adapterPlace);
        lvPlaces.setOnItemClickListener(this);
        btnOpenPlaceDrawer = (ImageView) findViewById(R.id.btnOpenPlaceDrawer);
        btnOpenPlaceDrawer.setOnClickListener(this);
        btnOpenPlaceDrawer.setVisibility(View.GONE);

        //Favorite Places Navigation Drawer
        lvFavPlaces = lvPlaces;
        adapterFavPlace = adapterPlace;

        btnOpenFavPlaceDrawer = (ImageView) findViewById(R.id.btnOpenFavoriteDrawer);
        btnOpenFavPlaceDrawer.setOnClickListener(this);

        //Setting Button
        btnSetting = (ImageView) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(this);

        //Login Button
        btnLogin = (ImageView) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        //My Location Button
        btnMyLocation = (ImageView) findViewById(R.id.btnMyLocation);
        btnMyLocation.setOnClickListener(this);


    }

    private void initMap() {

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        refreshUserLocation();
        mMap.setOnInfoWindowClickListener(this);

        overviewPlaceInfoWindowAdapter = new OverviewPlaceInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(overviewPlaceInfoWindowAdapter);
    }

    private void refreshUserLocation() {
        if (userLocationMarker != null)
            userLocationMarker.remove();

        MyLocation userLocation = GlobalVars.getUserLocation();
        if (userLocation != null) {
            LatLng userLatLng = new LatLng(userLocation.latitude, userLocation.longitude);
            userLocationMarker = mMap.addMarker(new MarkerOptions().position(userLatLng).title("Your location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
        }
    }

    @Override
    public void onGooglePlaceSearchStart() {

    }

    @Override
    public void onGooglePlaceSearchSuccess(PlaceList placeList) {
        isGoogleDone = true;

        List<Place> places = placeList.places;

        GooglePlaceImporter googlePlaceImporter = new GooglePlaceImporter();
        //Display on Google maps
        for (int i = 0; i < places.size(); ++i) {
            PlaceInfo place = googlePlaceImporter.Convert(places.get(i));
            GlobalVars.currentPlaceList.add(place);

            LatLng position = new LatLng(place.lat, place.lng);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.fromResource(GlobalVars.keywordItem.icon)));

            GlobalVars.markerData.put(marker, place);
            markers.add(marker);
        }

        LatLng centerPosition = getCenterLocation(places);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPosition, 15));

        //Display on list view
        adapterPlace.updateData(GlobalVars.currentPlaceList);
        btnOpenPlaceDrawer.setVisibility(View.VISIBLE);

        //Done
        if (isGoogleDone && isMyServerDone)
            progressDialog.dismiss();
    }

    private LatLng getCenterLocation(List<Place> places) {
        if (GlobalVars.getUserLocation() == null)
            return null;

        LatLng ans = new LatLng(GlobalVars.getUserLocation().latitude, GlobalVars.getUserLocation().longitude);
        return ans;
    }

    @Override
    public void onGPSTrackerLocationChanged(Location loc) {
        double lat = loc.getLatitude();
        double lng = loc.getLongitude();
        Toast.makeText(this, "TrackerLocationChanged: " + lat + "," + lng, Toast.LENGTH_SHORT).show();
        if (lat != 0 || lng != 0) {
            GlobalVars.location = new MyLocation(lat, lng);
            if (mMap != null)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));

            refreshUserLocation();
        }
    }

    @Override
    public void onGPSTrackerStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, "Status changed: " + status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        PlaceInfo place = GlobalVars.markerData.get(marker);
//        Toast.makeText(this, place.mName,
//                Toast.LENGTH_SHORT).show();

        GlobalVars.currentPlace = place;
        Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawerLayout.closeDrawers();
        if (GlobalVars.drawer == GlobalVars.DrawerType.KEYWORDS) {
            KeywordItem item = GlobalVars.keywordItem = adapterKeyword.getItem(position);

            //Reset all
            progressDialog = ProgressDialog.show(this, "", "Finding " + GlobalVars.keywordItem.text + "...");
            for (Marker maker : markers) {
                maker.remove();
            }

            isMyServerDone = isGoogleDone = false;
            //Reset common place list and request for new places
            GlobalVars.currentPlaceList = new ArrayList<>();
            new GooglePlaceSearchNearBySearch(this, GlobalVars.getUserLocation(), item.text).execute();
            new MyServerNearBySearch(this, GlobalVars.getUserLocation().latitude, GlobalVars.getUserLocation().longitude, item.text).execute();

        } else if (GlobalVars.drawer == GlobalVars.DrawerType.SEARCH_PLACES){
            PlaceInfo place = adapterPlace.getItem(position);
            GlobalVars.currentPlace = place;
            Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
            startActivity(intent);
        } else if (GlobalVars.drawer == GlobalVars.DrawerType.FAVORITE_PLACES) {
//            PlaceInfo place = FavoritePlacesManager.getInstance().places.get(position);
//            GlobalVars.currentPlace = place;
//            Intent intent = new Intent(MainActivity.this, PlaceDetailActivity.class);
//            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenKeywordDrawer:
                drawerLayout.openDrawer(Gravity.LEFT);
                GlobalVars.drawer = GlobalVars.DrawerType.KEYWORDS;
                break;
            case R.id.btnOpenPlaceDrawer:
                adapterPlace.updateData(GlobalVars.currentPlaceList);
                drawerLayout.openDrawer(Gravity.RIGHT);
                GlobalVars.drawer = GlobalVars.DrawerType.SEARCH_PLACES;
                break;
            case R.id.btnOpenFavoriteDrawer:
                //adapterFavPlace.updateData(FavoritePlacesManager.getInstance().places);
                drawerLayout.openDrawer(Gravity.RIGHT);
                GlobalVars.drawer = GlobalVars.DrawerType.FAVORITE_PLACES;
                break;
            case R.id.btnSetting: {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnLogin: {
                if (GlobalVars.getUserId() == 0) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    GlobalVars.setUserId(0);
                    btnLogin.setImageResource(R.drawable.btn_login);
                }
                break;
            }
            case R.id.btnMyLocation: {
                refreshUserLocation();
                Toast.makeText(this, "Move camera to your location done!", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null)
            refreshUserLocation();

        if (GlobalVars.getUserId() != 0) {
            btnLogin.setImageResource(R.drawable.btn_logout);
        }
    }

    @Override
    public void OnMyServerNearBySearchStart() {

    }

    @Override
    public void OnMyServerNearBySearchSuccess(List<MyServerPlaceInfo> items) {
        isMyServerDone = true;

        MyServerPlaceImporter importer = new MyServerPlaceImporter();

        for (int i = 0; i < items.size(); ++i) {
            if (importer.IsValidInput(items.get(i))) {
                PlaceInfo place = importer.Convert(items.get(i));
                GlobalVars.currentPlaceList.add(place);

                LatLng position = new LatLng(place.lat, place.lng);
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(position));

                GlobalVars.markerData.put(marker, place);
                markers.add(marker);
            }
        }

        if (isGoogleDone && isMyServerDone)
            progressDialog.dismiss();
    }

    @Override
    public void OnMyServerNearBySearchFailed(String error_message) {
        isMyServerDone = true;

        if (isGoogleDone && isMyServerDone)
            progressDialog.dismiss();
    }
}
