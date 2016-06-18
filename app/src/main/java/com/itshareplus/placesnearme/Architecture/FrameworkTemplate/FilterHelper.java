package com.itshareplus.placesnearme.Architecture.FrameworkTemplate;

import com.itshareplus.placesnearme.Model.FBFeedItem;

/*
    Framework Template Method
 */

public abstract class FilterHelper {
    public abstract boolean NeedSwap(FBFeedItem item1, FBFeedItem item2);
}
