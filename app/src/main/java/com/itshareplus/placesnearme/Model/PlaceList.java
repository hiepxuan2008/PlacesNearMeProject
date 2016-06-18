package com.itshareplus.placesnearme.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/23/2016.
 */

public class PlaceList {
    public List<Place> places;
    public String next_page_token;

    public PlaceList() {
        places = new ArrayList<Place>();
    }

    public boolean canLoadMore() {
        return next_page_token != null;
    }
}