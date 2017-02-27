package com.example.ashleydunford.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashleydunford.myapplication.clients.MyAPIClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddAuthorActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_add_author);
        layout.addView(textView);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, BooksActivity.class);
        EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        editText = (EditText) findViewById(R.id.gender);
        String gender = editText.getText().toString();
        editText = (EditText) findViewById(R.id.id);
        String id = editText.getText().toString();
        editText = (EditText) findViewById(R.id.userId);
        String userId = editText.getText().toString();



        System.out.println(name);
        System.out.println(gender);
        System.out.println(id);
        System.out.println(userId);
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("gender", gender);
        params.put("id", id);
        params.put("userId", userId);


        MyAPIClient.post("/author", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                System.out.println("Success?");
                System.out.println(object.toString());
            }

            @Override
            public void onFailure(int statusCode,
                                  Header[] headers,
                                  java.lang.Throwable throwable,
                                  org.json.JSONObject errorResponse) {
                System.out.println(errorResponse.toString());
                System.out.println(throwable.getMessage());
            }

        });

    }

}
