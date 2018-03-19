package cs492.barcrawler.Utils;

import android.net.ParseException;
import android.net.Uri;

import com.yelp.clientlib.connection.YelpAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Billy on 3/7/18.
 */

public class YelpAPIUtils {
    private static final String YELP_API_BASE_URL = "https://api.yelp.com/v3/businesses/search";
    private static final String YELP_API_CLIENT_ID = "H3Fk8CujrwlfrT13Fh2P6w";
    private static final String YELP_API_LOCATION_PARAM = "location";
    private static final String YELP_API_LATITUDE_PARAM = "latitude";
    private static final String YELP_API_LONGITUDE_PARAM = "longitude";
    private static final String YELP_API_RADIUS_PARAM = "radius";
    private static final String YELP_API_CATEGORIES_PARAM = "categories";
    private static final String YELP_API_PRICE_PARAM = "price";
    private static final String YELP_API_SORT_BY_PARAM = "sort_by";
    private static final String YELP_API_OPEN_NOW_PARAM = "open_now";

    public static final String YELP_API_AUTH_HEADER_NAME = "Authorization";
    public static final String YELP_API_AUTH_HEADER_VALUE = "BEARER 5Sp7JRrDxKa4pEsh-JNADYa_Z2ZHRyvytSPcxN-Gi-0ySXfbTC4gI1Vr8FEblrFQoKDYgZENPw3U-PDS69F0aB_Pih4GACpYONmM2AthKeBWe8JLskKUnfRAooWgWnYx";

    public static class YelpItem implements Serializable {
        public String barName;
        public String description;
        public URL imageURL;
        public URL yelpURL;
        public int rating;
        public String price;
        public String phoneNumber;
        public String address;
        public String city;
        public String state;
        public String zipcode;
        public float distance;
        public boolean isClosed;
    }

    public static String buildYelpSearchURL(String latitude, String longitude) {
        return Uri.parse(YELP_API_BASE_URL).buildUpon()
                .appendQueryParameter(YELP_API_LATITUDE_PARAM, latitude)
                .appendQueryParameter(YELP_API_LONGITUDE_PARAM, longitude)
                .appendQueryParameter(YELP_API_CATEGORIES_PARAM, "bars,pubs")
                .appendQueryParameter(YELP_API_RADIUS_PARAM, "100")
                .appendQueryParameter(YELP_API_OPEN_NOW_PARAM, "false")
                .build()
                .toString();
    }

    public static String buildYelpSearchURL(String location, String radius, String price, String open_now) {
        return Uri.parse(YELP_API_BASE_URL).buildUpon()
                .appendQueryParameter(YELP_API_LOCATION_PARAM, location)
                .appendQueryParameter(YELP_API_CATEGORIES_PARAM, "bars,pubs")
                .appendQueryParameter(YELP_API_RADIUS_PARAM, radius)
                .appendQueryParameter(YELP_API_PRICE_PARAM, price)
                .appendQueryParameter(YELP_API_OPEN_NOW_PARAM, open_now)
                .build()
                .toString();
    }

    public static ArrayList<YelpItem> parseYelpJSONResponse(String yelpJSONResponse) {
        try {
            JSONObject yelpObj = new JSONObject(yelpJSONResponse);
            JSONArray yelpBusinessList = yelpObj.getJSONArray("businesses");

            ArrayList<YelpItem> yelpItemsList = new ArrayList<>();
            for(int i = 0; i < yelpBusinessList.length(); i++) {
                YelpItem yelpItem = new YelpItem();
                JSONObject yelpListElement = yelpBusinessList.getJSONObject(i);

                yelpItem.barName = yelpListElement.getString("name");
                yelpItemsList.add(yelpItem);
            }
            return yelpItemsList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}