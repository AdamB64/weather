package uk.ac.rgu.socweather.data;

import android.net.Uri;

import androidx.annotation.NonNull;

/**
 * Some utility functions
 */
public class Utils {
    /**
     * Builds and returns a Uri based on the parameters
     * @param base The base of the URI
     * @param paramName The name of the single parameter that will be added to the URI
     * @param paramValue The value of that parameter
     * @return A URI of the form <base>?<paramName>:<paramValue>
     */
    @NonNull
    public static Uri buildUri(String base, String paramName, String paramValue) {
        Uri geoLocation = Uri.parse(base);
        // create a URI Builder and add the parameter
        Uri.Builder uriBuilder = geoLocation.buildUpon();
        uriBuilder.appendQueryParameter(paramName, paramValue);
        return uriBuilder.build();
    }
}