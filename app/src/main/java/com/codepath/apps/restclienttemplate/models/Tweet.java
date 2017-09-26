package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jason on 9/26/17.
 */

public class Tweet {

    // our attributes
    public String body;
    public long uid; // databse ID for tweet
    public User user;
    public String createdAt;

    // deserailize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user =  User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }
}
