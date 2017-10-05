package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView etName;
    TextView tvCharacterCount;

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
        etName = (TextView) findViewById(R.id.etName);
        tvCharacterCount = (TextView) findViewById(R.id.tvCharacterCount);
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

        client.getUserInfo(null, new JsonHttpResponseHandler(){
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


        etBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String msg = etBody.getText().toString();
                String count = "" + (140 - msg.length());
                tvCharacterCount.setText(count);
            }
        });

    }
}
