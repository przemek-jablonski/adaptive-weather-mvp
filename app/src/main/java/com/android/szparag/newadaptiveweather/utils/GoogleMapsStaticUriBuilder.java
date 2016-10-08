package com.android.szparag.newadaptiveweather.utils;

/**
 * Created by ciemek on 07/10/2016.
 */
public class GoogleMapsStaticUriBuilder {

    private StringBuilder   builder;
    private String          googleMapsStaticApiKey;

    public GoogleMapsStaticUriBuilder(String googleMapsStaticBaseUrl, String googleMapsStaticApiKey) {
        builder = new StringBuilder();
        this.googleMapsStaticApiKey = googleMapsStaticApiKey;
        setAuthority(googleMapsStaticBaseUrl);
    }

    public GoogleMapsStaticUriBuilder setAuthority(String googleMapsStaticBaseUrl) {
        builder = new StringBuilder();
        builder.append(googleMapsStaticBaseUrl);
        builder.append(Constants.GoogleMapsStatic.Key.PARAMETERS_START);
        return this;
    }

    public GoogleMapsStaticUriBuilder appendQueryParameter(CharSequence key, CharSequence value) {
        builder.append(Constants.GoogleMapsStatic.Key.PARAMETER_CONCAT);
        builder.append(key);
        builder.append(Constants.GoogleMapsStatic.Key.PARAMETER_EQUALS);
        builder.append(value);
        return this;
    }

    public GoogleMapsStaticUriBuilder appendLocationCenterQueryParameter(float gpsLatValue, float gpsLonValue) {
        return appendQueryParameter(
                Constants.GoogleMapsStatic.Key.CENTER,
                Utils.makeLocationGpsString(gpsLatValue, gpsLonValue, true)
        );
    }

    public GoogleMapsStaticUriBuilder appendZoomQueryParameter(int zoomValue) {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.ZOOM,
                Integer.toString(zoomValue)
        );
        return this;
    }

    public GoogleMapsStaticUriBuilder appendMapSizeParameter(int sizeHorizontal, int sizeVertical) {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.SIZE,
                Utils.makeStringGoogleMapsSize(sizeHorizontal, sizeVertical)
        );
        return this;
    }

    public GoogleMapsStaticUriBuilder appendMapScaleParameter(int mapScale) {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.SCALE,
                Integer.toString(mapScale)
        );
        return this;
    }

    public GoogleMapsStaticUriBuilder appendMapTypeParameter(String mapType) {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.MAPTYPE,
                mapType
        );
        return this;
    }

    public GoogleMapsStaticUriBuilder appendFormatParameter(String format) {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.FORMAT,
                format
        );
        return this;
    }

    private GoogleMapsStaticUriBuilder appendApiKey() {
        appendQueryParameter(
                Constants.GoogleMapsStatic.Key.KEY,
                googleMapsStaticApiKey
        );
        return this;
    }

    public String getString() {
        return appendApiKey().builder.toString();
    }

//    public Uri getUri() {}
}

