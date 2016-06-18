package com.itshareplus.placesnearme.Manager;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.itshareplus.placesnearme.Model.ListPlace;
import com.itshareplus.placesnearme.Model.Place;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mai Thanh Hiep on 5/30/2016.
 */

public class FavoritePlacesManager {
    static FavoritePlacesManager _instance;
    static Context context;

    public List<Place> places;

    String fileName = null;

    private FavoritePlacesManager() {
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/favorites.xml";
        LoadData();
    }

    //Initialize: Set Context and LoadData()
    public static void Initialize(Context _context) {
        context = _context;
        _instance = new FavoritePlacesManager();
    }

    public static FavoritePlacesManager getInstance() {
        if (_instance == null) {
            Log.e(FavoritePlacesManager.class.getName(), "You must invoke Initialize() first!");
        }

        return _instance;
    }

    public void LoadData() {
//        Serializer serializer = new Persister();
//        File source = new File(fileName);
//        ListPlace listPlace = null;
//        try {
//            listPlace = serializer.read(ListPlace.class, source);
//            places = listPlace.places;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        if (places == null) {
            places = new ArrayList<>();
        }
    }

    public void SaveData() {
        Serializer serializer = new Persister();
        File result = new File(fileName);
        ListPlace listPlace = new ListPlace();
        listPlace.places = places;
        try {
            serializer.write(listPlace, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean IsExist(Place place) {
        for (Place p : places) {
            if (p.mPlaceId.equals(place.mPlaceId))
                return true;
        }
        return false;
    }

    public boolean AddThisPlace(Place place) {
        if (place == null)
            return false;

        if (IsExist(place))
            return false;

        Place clone = place.Clone();
        places.add(place);
        return true;
    }

    public boolean RemoveThisPlace(Place place) {
        if (place == null)
            return false;

        for (int i = 0; i < places.size(); ++i)
            if (places.get(i).mPlaceId.equals(place.mPlaceId)) {
                places.remove(i);
                break;
            }
        return true;
    }
}
