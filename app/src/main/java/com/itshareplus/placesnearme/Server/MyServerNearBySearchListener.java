package com.itshareplus.placesnearme.Server;

import com.itshareplus.placesnearme.Architecture.Importer.MyServerPlaceInfo;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 6/20/2016.
 */
public interface MyServerNearBySearchListener {
    void OnMyServerNearBySearchStart();
    void OnMyServerNearBySearchSuccess(List<MyServerPlaceInfo> items);
    void OnMyServerNearBySearchFailed(String error_message);
}
