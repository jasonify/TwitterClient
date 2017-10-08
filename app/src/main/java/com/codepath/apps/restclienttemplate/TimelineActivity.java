package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsPageAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

public class TimelineActivity extends AppCompatActivity implements TweetsListFragment.TweetSelectedListener{

    TweetsPageAdapter pageAdapter;
    private final int REQUEST_CODE = 20;
    ViewPager vpPager;

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

        // fragmentTweetList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
         vpPager = (ViewPager) findViewById(R.id.viewpager);
         pageAdapter = new TweetsPageAdapter(getSupportFragmentManager(), this);
        // = vpPager.getCurrentItem();

        vpPager.setAdapter(pageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);

//         tabLayout.getSelectedTabPosition()

    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras

            // Add single tweet to top of list:
            Boolean isSubmit = intent.getBooleanExtra("isSubmit", true);

            if (isSubmit) {
                Tweet tweet = (Tweet) intent.getSerializableExtra("tweet");
                int fragmentPosition = vpPager.getCurrentItem();
                if (fragmentPosition == 0) {
                    HomeTimelineFragment home =  (HomeTimelineFragment) pageAdapter.getRegisteredFragment(vpPager.getCurrentItem());
                    home.addNewTweet(tweet);

                } else {
                    if (pageAdapter.getRegisteredFragment(0) != null ) {
                        HomeTimelineFragment home =  (HomeTimelineFragment) pageAdapter.getRegisteredFragment(0);
                        home.addNewTweet(tweet);
                    }
                }

            }
        }
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager

                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void onProfileView(MenuItem item) {
        Log.d("debug", "clicked profile view");
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
    }


    @Override
    public void onTweetSelected(Tweet tweet) {
//         Toast.makeText(this, tweet.body, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name", tweet.user.screenName);
        startActivity(i);
    }
}
