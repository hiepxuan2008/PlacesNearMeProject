package com.itshareplus.placesnearme.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.itshareplus.placesnearme.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import com.itshareplus.placesnearme.Model.Review;
import com.itshareplus.placesnearme.Utility.PrettyTime;


/**
 * Created by Mai Thanh Hiep on 4/24/2016.
 */

public class PlaceDetailsReviewAdapter extends ArrayAdapter<Review> {
    Context context;
    List<Review> reviews;
    int resource;

    public PlaceDetailsReviewAdapter(Context context, int resource, List<Review> reviews) {
        super(context, resource, reviews);
        this.context = context;
        this.reviews = reviews;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, null);
        }

        Review item = getItem(position);
        if (item != null) {
            TextView txtUsername = (TextView) v.findViewById(R.id.txtUserName);
            TextView txtTime = (TextView) v.findViewById(R.id.txtTime);
            TextView txtReview = (TextView) v.findViewById(R.id.txtReview);
            ImageView ivAvatar = (ImageView) v.findViewById(R.id.avatar);
            TextView txtRating = (TextView) v.findViewById(R.id.ratingText);
            RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

            txtUsername.setText(item.authorName);
            txtReview.setText(item.text);
            txtTime.setText(PrettyTime.format(new Date(), new Date(item.time * 1000)));
            if (item.profilePhotoUrl != null) {
                Picasso.with(context).load(item.profilePhotoUrl).placeholder(R.drawable.no_avatar).error(R.drawable.no_avatar).into(ivAvatar);
            } else {
                ivAvatar.setImageResource(R.drawable.no_avatar);
            }

            if (item.rating < 2) {
                txtRating.setText(item.rating + " star");
            } else {
                txtRating.setText(item.rating + " stars");
            }
            ratingBar.setMax(5);
            ratingBar.setRating((float)item.rating);
        }

        return v;
    }

    public void setData(List<Review> reviews) {
        this.reviews = reviews;
    }
    @Override
    public Review getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
