package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by jason on 10/3/17.
 */

public class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {
    EndlessRecyclerViewScrollListener scrollListener;
    LinearLayoutManager linearLayoutManager;
    TweetAdapter tweetAdapter;
    ArrayList<Tweet> tweets;
    RecyclerView rvTweets;


    public interface  TweetSelectedListener {
        public void onTweetSelected(Tweet tweet);
    }
    // inflation happens onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        tweets = new ArrayList<>();
        tweetAdapter = new TweetAdapter(tweets, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setAdapter(tweetAdapter);

        return v;
    }

    public void addItems(JSONArray response) {
        for(int i = 0 ; i < response.length(); i++) {
            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNewTweet(Tweet tweet){
        tweets.add(0, tweet);
        tweetAdapter.notifyItemInserted(0);
        rvTweets.smoothScrollToPosition(0);
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);

        ((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }
}
