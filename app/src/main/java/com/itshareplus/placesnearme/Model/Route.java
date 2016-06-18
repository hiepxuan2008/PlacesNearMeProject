package com.itshareplus.placesnearme.Model;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/24/2016.
 */
public class Route {
    public String summary;
    public List<Leg> legs; // A route with no waypoints will contain exactly one leg within the legs array
    public String overview_polyline;
    public String copyrights;
}