package com.itshareplus.placesnearme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itshareplus.placesnearme.Architecture.Importer.PlaceInfo;
import com.itshareplus.placesnearme.Model.MyLocation;
import com.itshareplus.placesnearme.R;

import java.util.List;

import com.itshareplus.placesnearme.Model.GlobalVars;
import com.itshareplus.placesnearme.Model.Place;

/**
 * Created by Mai Thanh Hiep on 4/25/2016.
 */
public class PlacesAdapter extends ArrayAdapter<PlaceInfo> {
    Context context;
    List<PlaceInfo> places;
    int resource;

    public PlacesAdapter(Context context, int resource, List<PlaceInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.places = objects;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public PlaceInfo getItem(int position) {
        return places.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, null);
        }

        PlaceInfo place = getItem(position);
        if (place != null) {
            ImageView imageViewIcon = (ImageView)v.findViewById(R.id.ivIcon);
            TextView txtPlaceName = (TextView)v.findViewById(R.id.txtPlaceName);
            TextView txtDistance = (TextView) v.findViewById(R.id.txtDistance);
            TextView txtPlaceAddress = (TextView) v.findViewById(R.id.txtPlaceAddress);

            imageViewIcon.setImageResource(GlobalVars.keywordItem.icon);
            txtPlaceName.setText(place.name);
            txtPlaceAddress.setText(place.address);
            txtDistance.setText(new MyLocation(place.lat, place.lat).distanceTo(GlobalVars.location));
        }
        return v;
    }

    public void updateData(List<PlaceInfo> places) {
        this.places = places;
        this.notifyDataSetChanged();
    }
}
