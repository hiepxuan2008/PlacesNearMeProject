package com.itshareplus.placesnearme.Acitivity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itshareplus.placesnearme.GoogleMapWebService.GoogleMapsGeocoding;
import com.itshareplus.placesnearme.GoogleMapWebService.GoogleMapsGeocodingListener;
import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Model.MyLocation;
import com.itshareplus.placesnearme.R;

public class SettingActivity extends AppCompatActivity implements OnMapReadyCallback, CompoundButton.OnCheckedChangeListener, View.OnClickListener, GoogleMapsGeocodingListener, GoogleMap.OnMarkerDragListener {

    ImageView btnSearch;
    ImageView btnUpdateLocation;
    EditText txtLatitude;
    EditText txtLongitude;
    EditText txtSearch;
    GoogleMap mMap;
    Switch switchFakeGPS;
    Marker currentMarker;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initMap();
        initComponents();
        updateStates();
    }

    private void initComponents() {
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnUpdateLocation = (ImageView) findViewById(R.id.btnUpdateLocation);
        btnUpdateLocation.setOnClickListener(this);

        txtLatitude = (EditText) findViewById(R.id.etLatitude);
        txtLongitude = (EditText) findViewById(R.id.etLongitude);
        txtSearch = (EditText) findViewById(R.id.etSearch);

        switchFakeGPS = (Switch) findViewById(R.id.switchFakeGPS);
        switchFakeGPS.setOnCheckedChangeListener(this);
        switchFakeGPS.setChecked(GlobalVars.IsFakeGPS);
        txtLatitude.setText(String.valueOf(GlobalVars.fakeLocation.latitude));
        txtLongitude.setText(String.valueOf(GlobalVars.fakeLocation.longitude));
    }

    private void updateStates() {
        if (!switchFakeGPS.isChecked()) {
            txtSearch.setEnabled(false);
            txtLongitude.setEnabled(false);
            txtLatitude.setEnabled(false);
        } else {
            txtSearch.setEnabled(true);
            txtLongitude.setEnabled(true);
            txtLatitude.setEnabled(true);
        }
    }

    private void initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.settingMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerDragListener(this);

        updateCurrentMarker(GlobalVars.getUserLocation());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            GlobalVars.IsFakeGPS = true;
            Toast.makeText(this, "Turn on Fake GPS", Toast.LENGTH_SHORT).show();
            updateStates();
        } else {
            GlobalVars.IsFakeGPS = false;
            Toast.makeText(this, "Turn off Fake GPS", Toast.LENGTH_SHORT).show();
            updateStates();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                doSearch();
                break;
            case R.id.btnUpdateLocation:
                doUpdateLocation();
                break;
        }
    }

    private void doUpdateLocation() {
        double lat = Double.valueOf(txtLatitude.getText().toString());
        double lng = Double.valueOf(txtLongitude.getText().toString());

        updateCurrentMarker(new MyLocation(lat, lng));
    }

    private void doSearch() {
        String search = txtSearch.getText().toString();
        if (search.isEmpty()) {
            Toast.makeText(this, "You have to enter address text!", Toast.LENGTH_SHORT).show();
            return;
        }

        new GoogleMapsGeocoding(this, search).execute();
    }

    @Override
    public void onGoogleMapsGeocodingStart() {
        currentMarker.remove();
        progressDialog = ProgressDialog.show(this, null, "Querying location...");
    }

    @Override
    public void onGoogleMapsGeocodingSuccess(MyLocation location) {
        if (location == null)
            return;

        txtLatitude.setText(String.valueOf(location.latitude));
        txtLongitude.setText(String.valueOf(location.longitude));

        updateCurrentMarker(location);

        GlobalVars.fakeLocation.latitude = location.latitude;
        GlobalVars.fakeLocation.longitude = location.longitude;
        progressDialog.dismiss();
    }

    private void updateCurrentMarker(MyLocation location) {
        LatLng startPosition = new LatLng(location.latitude, location.longitude);
        currentMarker = mMap.addMarker(new MarkerOptions().position(startPosition).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPosition, 15));
        currentMarker.setDraggable(true);
    }

    @Override
    public void onGoogleMapsGeocodingFailed(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng pos = marker.getPosition();
        txtLatitude.setText(String.valueOf(pos.latitude));
        txtLongitude.setText(String.valueOf(pos.longitude));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng pos = marker.getPosition();
        GlobalVars.fakeLocation.latitude = pos.latitude;
        GlobalVars.fakeLocation.longitude = pos.longitude;
    }
}
