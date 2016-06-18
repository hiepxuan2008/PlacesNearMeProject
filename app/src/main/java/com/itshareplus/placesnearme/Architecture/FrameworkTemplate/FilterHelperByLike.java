package com.itshareplus.placesnearme.Architecture.FrameworkTemplate;

import com.itshareplus.placesnearme.Model.FBFeedItem;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public class FilterHelperByLike extends FilterHelper {
    @Override
    public boolean NeedSwap(FBFeedItem item1, FBFeedItem item2) {
        return item1.likers.size() < item2.likers.size();
    }
}
