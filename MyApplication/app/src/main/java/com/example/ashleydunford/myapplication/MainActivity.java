package com.example.ashleydunford.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ashleydunford.myapplication.clients.MyAPIClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ashleycastiglione on 11/29/16.
 */

public class MainActivity extends AppCompatActivity implements LocationListener {

    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_main);
        layout.addView(textView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    2);
            System.out.println("Entered permission if block");
            // return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
                2000,
                10, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        EditText txtLat = (EditText) findViewById(R.id.location);
        if(location != null) {
            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    EditText txtLat = (EditText) findViewById(R.id.location);
                    if(location != null) {
                        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
                    }


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onLocationChanged(Location location) {
        EditText txtLat = (EditText) findViewById(R.id.location);
        if(location != null) {
            txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, BooksActivity.class);
        EditText editText = (EditText) findViewById(R.id.username);
        String username = editText.getText().toString();
        editText = (EditText) findViewById(R.id.password);
        String password = editText.getText().toString();
//        editText = (EditText) findViewById(R.id.id);
//        String id = editText.getText().toString();
        editText = (EditText) findViewById(R.id.location);
        String location = editText.getText().toString();

        System.out.println(username);
        System.out.println(password);
//        System.out.println(id);
        System.out.println(location);

        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        RequestParams params = new RequestParams();
//        params.put("id", id);
        params.put("username", username);
        params.put("password", password);
        params.put("location", location);

        MyAPIClient.post("/user", params, new JsonHttpResponseHandler() {
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

    public void createUser(View view) {
        // Do something in response to button

        EditText editText = (EditText) findViewById(R.id.username);
        String username = editText.getText().toString();
        editText = (EditText) findViewById(R.id.password);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.location);
        String location = editText.getText().toString();


        System.out.println(username);
        System.out.println(password);
        System.out.println(location);

        //intent.putExtra(EXTRA_MESSAGE, message);

        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        params.put("location", location);

        final Intent intent = new Intent(this, BooksActivity.class);


        MyAPIClient.post("/user", params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                System.out.println("Success?");
                System.out.println(object.toString());
                String username = null;
                try {
                    username = object.getString("username");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("username", username);
                startActivity(intent);
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

    public void signIn(View view) {
        // Do something in response to button

        EditText editText = (EditText) findViewById(R.id.username);
        String username = editText.getText().toString();
        editText = (EditText) findViewById(R.id.password);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.location);
        String location = editText.getText().toString();

        System.out.println(username);
        System.out.println(password);
        System.out.println(location);

        //intent.putExtra(EXTRA_MESSAGE, message);
        List<Header> headers = new ArrayList<Header>();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        final Intent intent = new Intent(this, BooksActivity.class);

        MyAPIClient.get(MainActivity.this, "/user", headers.toArray(new Header[headers.size()]),
                params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                System.out.println("Success?");
                System.out.println(object.toString());
                String username = null;
                try {
                    username = object.getString("username");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("username", username);
                startActivity(intent);
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




