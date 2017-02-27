package com.example.ashleydunford.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ashleydunford.myapplication.clients.MyAPIClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

//import static com.example.ashleydunford.myapplication.R.id.author;
//import static com.example.ashleydunford.myapplication.R.string.author;

public class AddBookActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

        //Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

        List<Header> headers = new ArrayList<Header>();
        //headers.add(new BasicHeader("Accept", "application/json"));
        final String[] authornames = new String[100];

        MyAPIClient.get(AddBookActivity.this, "/authors/1", headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        System.out.println("first success");

//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                authornames[i] = response.getJSONObject(i).getString("authorName");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                        System.out.println(object.toString());
                        System.out.println("second success");
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

    public void addAuthor(View view){
        Intent intent = new Intent(this, AddAuthorActivity.class);
        startActivity(intent);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?

        final CheckBox checkbox = (CheckBox) findViewById(R.id.bookRead);
        if(checkbox.isChecked()){
            checkbox.setChecked(true);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bookType);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton) findViewById(selectedId);
        if(radioButton.isChecked()){
            radioButton.setChecked(true);
        }
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, BooksActivity.class);
        EditText editText = (EditText) findViewById(R.id.title);
        String title = editText.getText().toString();
        editText = (EditText) findViewById(R.id.author);
        String author = editText.getText().toString();
        editText = (EditText) findViewById(R.id.id);
        String id = editText.getText().toString();
        editText = (EditText) findViewById(R.id.publicationYear);
        String publicationYear = editText.getText().toString();

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.bookType);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton) findViewById(selectedId);
        String bookType = (String) radioButton.getText();

        CheckBox checkBox = (CheckBox) findViewById(R.id.bookRead);
        boolean bookRead = checkBox.isChecked();
        SeekBar seekBar = (SeekBar) findViewById(R.id.rating);
        int rating = seekBar.getProgress();

        System.out.println(title);
        System.out.println(author);
        System.out.println(id);
        System.out.println(publicationYear);
        System.out.println(bookRead);
        System.out.println(rating);
        intent.putExtra("username", BooksActivity.username);
        startActivity(intent);
        RequestParams params = new RequestParams();
        params.put("author", author);
        params.put("title", title);
        params.put("id", id);
        params.put("publicationYear", publicationYear);
        params.put("rating", rating);
        params.put("bookRead", bookRead);
        params.put("bookType", bookType);
        params.put("username", BooksActivity.username);

        MyAPIClient.post("/book", params, new JsonHttpResponseHandler(){
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
