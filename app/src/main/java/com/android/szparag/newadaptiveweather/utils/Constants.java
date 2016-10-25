package com.android.szparag.newadaptiveweather.utils;

import android.support.annotation.ColorInt;

import com.android.szparag.newadaptiveweather.R;

/**
 * Created by ciemek on 23/09/2016.
 */
public class Constants {

    public static final String ADAPTIVE_WEATHER = "ADAPTIVE_WEATHER";
    public static final String ADAPTIVE_WEATHER_REALM = "REALM";
    public static final String ADAPTIVE_WEATHER_RETROFIT = "RETROFIT";

    public static final String OPENWEATHERMAP_BASEURL = "openweathermap_baseurl";
    public static final String OPENWEATHERMAP_APIKEY = "openweathermap_apikey";

    public static final String GOOGLEMAPSSTATIC_BASEURL = "googlemapsstatic_baseurl";
    public static final String GOOGLEMAPSSTATIC_APIKEY = "googlemapsstatic_apikey";

    public static final String LAST_API_UPDATE_TIME = "last_api_update_time";

    public static final String DAY_FORWARD_KEY = "dayForward";
    public static final String DAY_FORWARD_PAGE_TITLE_KEY = "pageTitle";

    public static float KELVIN_TO_CELSIUS_SUBTRAHEND = 273.15f;

    //the reason behind existence of this class (instead of having strings in strings.xml)
    //is that this way there is no need for Android framework injection
    //in presenter and Utility classes (cleaner architecture)
    public class GoogleMapsStatic {
        public class Key {
            public static final String CENTER = "center";
            public static final String ZOOM = "zoom";
            public static final String SIZE = "size";
            public static final String SCALE = "scale";
            public static final String MAPTYPE = "maptype";
            public static final String FORMAT = "format";
            public static final String KEY = "key";
            public static final String PARAMETERS_START = "?";
            public static final String PARAMETER_CONCAT = "&";
            public static final String PARAMETER_EQUALS = "=";
        }

        public class MapType {
            public static final String ROADMAP = "roadmap";
            public static final String SATELLITE = "satellite";
            public static final String HYBRID = "hybrid";
            public static final String TERRAIN = "terrain";
        }

        public class Format {
            public static final String JPG = "jpg-baseline";
            public static final String PNG = "png";
            public static final String PNG32 = "png32";
        }

        public class Scale {
            public static final int SCALE_X1 = 1;
            public static final int SCALE_X2 = 2;
        }
        public class Size {
            public static final int SIZE_STANDARD = 640; //the only one available in free programme
        }
    }

}
