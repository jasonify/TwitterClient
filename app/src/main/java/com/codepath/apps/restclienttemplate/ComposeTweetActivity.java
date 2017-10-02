package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ComposeTweetActivity extends AppCompatActivity {
    private TwitterClient client;
    Context context;

    Button btnCancel;
    Button btnSubmit;
    EditText etBody;
    ImageView ivProfile;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        context = getContext();

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etBody = (EditText) findViewById(R.id.etBody);
        client = TwitterApp.getRestClient();
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        etName = (EditText) findViewById(R.id.etName);
        etBody.setHint("Write something here");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etBody.getText().toString();
                Log.d("tweeting",  msg);
                client.postToTimeline(msg, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Tweet tweet = Tweet.fromJSON(response);
                            Intent data = new Intent();
                            data.putExtra("isSubmit", true);
                            data.putExtra("tweet", tweet);
                            setResult(RESULT_OK, data);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("tweeted failed :( :(", ":( :(");

                        }
                    }
                });
            }
        });

        client.getProfile(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    User user = User.fromJSON(response);
                    Glide.with(context).load(user.profileImageUrl).into(ivProfile);
                    etName.setText(user.name);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("tweeted failed :( :(", ":( :(");

                }
            }
        });

    }
}
