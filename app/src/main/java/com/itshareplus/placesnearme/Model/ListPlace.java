package com.itshareplus.placesnearme.Model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 5/30/2016.
 */
@Root
public class ListPlace {
    @ElementList
    public List<Place> places;
}
