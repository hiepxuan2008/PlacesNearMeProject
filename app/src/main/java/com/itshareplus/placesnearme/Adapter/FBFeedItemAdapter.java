package com.itshareplus.placesnearme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itshareplus.placesnearme.Model.FBFeedItem;
import com.itshareplus.placesnearme.Model.Review;
import com.itshareplus.placesnearme.R;
import com.itshareplus.placesnearme.Utility.PrettyTime;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 6/15/2016.
 */
public class FBFeedItemAdapter extends ArrayAdapter<FBFeedItem> {
    Context context;
    List<FBFeedItem> items;
    int resource;

    public FBFeedItemAdapter(Context context, int resource, List<FBFeedItem> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, null);
        }

        FBFeedItem item = getItem(position);
        if (item != null) {
            TextView txtName = (TextView) v.findViewById(R.id.txtFacebookName);
            TextView txtTime = (TextView) v.findViewById(R.id.txtPostTime);
            TextView txtStatus = (TextView) v.findViewById(R.id.txtStatus);
            ImageView ivAvatar = (ImageView) v.findViewById(R.id.avatar);

            txtName.setText(item.name);
            txtTime.setText(PrettyTime.format(new Date(), new Date(item.time * 1000)));
            txtStatus.setText(item.status);

            if (item.avatarURL != null) {
                Picasso.with(context).load(item.avatarURL).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(ivAvatar);
            } else {
                ivAvatar.setImageResource(R.drawable.no_avatar);
            }
        }

        return v;
    }

    public void setData(List<FBFeedItem> items) {
        this.items = items;
    }
    @Override
    public FBFeedItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public List<FBFeedItem> getItems() {
        return items;
    }
}
