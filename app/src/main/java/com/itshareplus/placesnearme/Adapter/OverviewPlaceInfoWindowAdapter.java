package com.itshareplus.placesnearme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.itshareplus.placesnearme.R;

import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Model.Place;

/**
 * Created by Mai Thanh Hiep on 4/25/2016.
 */
public class OverviewPlaceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;

    public OverviewPlaceInfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View v = LayoutInflater.from(context).inflate(R.layout.info_window_place_overview, null);
        if (v == null)
            return null;

        Place place = GlobalVars.markerData.get(marker);
        if (place == null)
            return null;

        TextView txtPlaceName = (TextView) v.findViewById(R.id.txtPlaceName);
        TextView txtPlaceAddress = (TextView) v.findViewById(R.id.txtPlaceAddress);
        TextView txtRating = (TextView) v.findViewById(R.id.txtRating);
        TextView txtDistance = (TextView) v.findViewById(R.id.txtDistance);

        txtPlaceName.setText(place.mName);
        txtPlaceAddress.setText(place.mVicinity);
        txtDistance.setText(place.mLocation.distanceTo(GlobalVars.location));

        if (place.mRating != null) {
            txtRating.setVisibility(View.VISIBLE);
            txtRating.setText("Rating " + place.mRating);
        } else {
            txtRating.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
