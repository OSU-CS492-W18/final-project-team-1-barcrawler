package cs492.barcrawler;



import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.util.Log;

import cs492.barcrawler.Utils.YelpAPIUtils;

public class BarListActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<String> {

    private static final String YELP_URL_KEY = "yelpURL";
    private static final int YELP_LOADER_ID = 0;

    // loading elements
    private ProgressBar mLoadingIndicator;
    private TextView mLoadingErrorMessage;

    private String userLatitude;
    private String userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_list);

        // get our loading indicator and error message in the activity_bar_list.xml
        mLoadingIndicator = findViewById(R.id.bar_list_loading_indicator);
        mLoadingErrorMessage = findViewById(R.id.loading_error_message);


        loadBars();
    }

    public void loadBars() {

        mLoadingIndicator.setVisibility(View.VISIBLE);

        String userLatitude = "44.563694";
        String userLongitude = "-123.262556";

        String yelpURL = YelpAPIUtils.buildYelpSearchURL(userLatitude, userLongitude);
        Bundle loaderArgs = new Bundle();
        loaderArgs.putString(YELP_URL_KEY, yelpURL);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(YELP_LOADER_ID, loaderArgs, this);
        Log.d("BAR LIST ACTIVITY", "FINISHED LOAD BARS FUNCTION");
    }

    // we are starting our loader
    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        Log.d("BAR LIST ACTIVITY", "CREATED THE LOADER");
        String yelpURL = null;
        if (bundle != null) {
            yelpURL = bundle.getString(YELP_URL_KEY);
            Log.d("BAR LIST ACTIVITY", "YELP URL: " + yelpURL);
        }
        return new BarLoader(this, yelpURL);
    }

    // our loading is done
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("BAR LIST ACTIVITY", "GOT BARS FROM LOADER");
        // hide our loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        //if we did not get any data back
        if (data != null) {
            // make the error message invisible
            mLoadingErrorMessage.setVisibility(View.INVISIBLE);
        } else {
            // make the error message visible
            mLoadingErrorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // nothing to do
    }
}
