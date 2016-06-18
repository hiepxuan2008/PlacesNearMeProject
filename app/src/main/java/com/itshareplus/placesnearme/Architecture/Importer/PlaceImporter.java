package com.itshareplus.placesnearme.Architecture.Importer;

/**
 * Created by Mai Thanh Hiep on 6/19/2016.
 */
public abstract class PlaceImporter {
    public abstract PlaceInfo Convert(Object input);
    public abstract boolean IsValidInput(Object input);
}
