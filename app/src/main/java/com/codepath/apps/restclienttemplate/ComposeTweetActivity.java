package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ComposeTweetActivity extends AppCompatActivity {

    Button btnCancel;
    Button btnSubmit;
    EditText etBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etBody = (EditText) findViewById(R.id.etBody);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                finish();
            }
        });
    }
}
