package com.itshareplus.placesnearme.Architecture.FrameworkTemplate;

import com.itshareplus.placesnearme.Model.FBFeedItem;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */

public class FilterHelperByTimeLine extends FilterHelper{
    @Override
    public boolean NeedSwap(FBFeedItem item1, FBFeedItem item2) {
        return item1.time < item2.time;
    }
}
