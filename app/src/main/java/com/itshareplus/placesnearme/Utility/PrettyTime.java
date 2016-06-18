package com.itshareplus.placesnearme.Utility;

import java.util.Date;

/**
 * Created by Mai Thanh Hiep on 4/24/2016.
 */
public class PrettyTime {

    public static String format(Date now, Date past) {
        return TimeAgo.toDuration(now.getTime() - past.getTime());
    }

   // private String
}
