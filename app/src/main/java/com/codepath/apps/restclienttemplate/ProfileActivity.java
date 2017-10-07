package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    TextView name;
    TextView description;
    TextView followers;
    TextView following;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String screenName = getIntent().getStringExtra("screen_name");
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.flContainer, userTimelineFragment);
        ft.commit();
        name = (TextView) findViewById(R.id.tvProfileName);
        description = (TextView) findViewById(R.id.tvProfileDescription);
        image = (ImageView) findViewById(R.id.ivProfilePicture);
        followers = (TextView) findViewById(R.id.tvFollowers);
        following = (TextView) findViewById(R.id.tvFollowing);

        client = TwitterApp.getRestClient();
        client.getUserInfo(screenName, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = null;
                try {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle(user.screenName);
                    name.setText(user.name);
                    description.setText(user.description);
                    followers.setText(NumberFormat.getNumberInstance(Locale.US).format( user.followers) + " Followers");
                    following.setText(NumberFormat.getNumberInstance(Locale.US).format(user.friends) + " Following:");

                    Glide.with(ProfileActivity.this).load(user.profileImageUrl).into(image);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }
        });
    }
}
