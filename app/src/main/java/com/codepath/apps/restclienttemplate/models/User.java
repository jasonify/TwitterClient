package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jason on 9/26/17.
 */

public class User implements Serializable {
    public String name;
    public long uid;
    public String screenName;
    public String profileImageUrl;
    public int followers;
    public int friends;
    public String description;

    public  static User fromJSON(JSONObject json) throws JSONException{
        User user = new User();

        user.name = json.getString("name");
        user.uid = json.getLong("id");
        user.followers = json.getInt("followers_count");
        user.friends = json.getInt("friends_count");
        user.description = json.getString("description");

        user.screenName = json.getString("screen_name");
        user.profileImageUrl = json.getString("profile_image_url");
        return user;
    }
}
