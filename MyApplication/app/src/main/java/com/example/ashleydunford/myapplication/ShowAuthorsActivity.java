package com.example.ashleydunford.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.ashleydunford.myapplication.adapters.BookAdapter;
import com.example.ashleydunford.myapplication.clients.MyAPIClient;
import com.example.ashleydunford.myapplication.models.Book;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ShowAuthorsActivity extends AppCompatActivity {

    private ListView bookList;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        getNotes();
    }

    public void addAuthor(View view){
        Intent intent = new Intent(this, AddAuthorActivity.class);
        startActivity(intent);

    }

    public void addBook(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AddBookActivity.class);
        EditText editText = (EditText) findViewById(R.id.title);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void getNotes() {
        List<Header> headers = new ArrayList<Header>();
        //headers.add(new BasicHeader("Accept", "application/json"));

        MyAPIClient.get(ShowAuthorsActivity.this, "/authors/1", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<Book> bookArray = new ArrayList<Book>();
                        BookAdapter bookAdapter = new BookAdapter(ShowAuthorsActivity.this, bookArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                bookAdapter.add(new Book(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        bookList = (ListView) findViewById(R.id.list_notes);
                        bookList.setAdapter(bookAdapter);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                        System.out.println(object.toString());
                    }

                    @Override
                    public void onFailure(int statusCode,
                                          cz.msebera.android.httpclient.Header[] headers,
                                          java.lang.Throwable throwable,
                                          org.json.JSONObject errorResponse) {
                        System.out.println(errorResponse.toString());
                        System.out.println(throwable.getMessage());
                    }
                });
    }
}