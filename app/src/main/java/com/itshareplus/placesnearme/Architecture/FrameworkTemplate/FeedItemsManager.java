package com.itshareplus.placesnearme.Architecture.FrameworkTemplate;

import com.itshareplus.placesnearme.Model.FBFeedItem;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */

public class FeedItemsManager {
    public List<FBFeedItem> items;

    public FeedItemsManager(List<FBFeedItem> items) {
        this.items = items;
    }

    public void Sort(FilterHelper helper) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++)
                if (helper.NeedSwap(items.get(i), items.get(j)))
                {
                    FBFeedItem temp = items.get(i);
                    items.set(i, items.get(j));
                    items.set(j, temp);
                }
        }
    }
}
