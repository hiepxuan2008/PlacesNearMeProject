package com.itshareplus.placesnearme.Model;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 6/15/2016.
 */
public class FBFeedItem {
    public int placeId;
    public int postId;
    public String name;
    public String username;
    public int userId;
    public long time;
    public long edited_at;
    public String avatarURL;
    public String status;
    public List<FBFeedPhoto> photos;
    public List<FBFeedUser> likers;
}
