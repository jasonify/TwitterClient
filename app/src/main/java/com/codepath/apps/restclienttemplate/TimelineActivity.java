package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    TweetsListFragment fragmentTweetList;
    private TwitterClient client;

    private final int REQUEST_CODE = 20;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.miNew) {
            presentComposeTweet();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void presentComposeTweet() {
        Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient();
        fragmentTweetList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);


       populateTimeline(1);
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            /*

            // Add single tweet to top of list:
            Boolean isSubmit = intent.getBooleanExtra("isSubmit", true);

            if (isSubmit) {
                Tweet tweet = (Tweet) intent.getSerializableExtra("tweet");
                tweets.add(0, tweet);
                tweetAdapter.notifyItemInserted(0);
                rvTweets.smoothScrollToPosition(0);
            }
            */
        }
    }

    // LAST ID:
    private void populateTimeline(long sinceId) {
        client.getHomeTimeline(sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                fragmentTweetList.addItems(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Debug", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Debug", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("Debug", errorResponse.toString());
                throwable.printStackTrace();
            }
        });
    }
}
