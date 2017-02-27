package com.example.ashleydunford.myapplication.clients;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MyAPIClient {
    private static final String BASE_URL = "http://assignment2adc.us-west-2.elasticbeanstalk.com";
    //private static final String BASE_URL = "http://localhost:8080";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, Header[] headers, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(context, getAbsoluteUrl(url), headers, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        System.out.println(getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        System.out.println(getAbsoluteUrl(url));
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        System.out.println(getAbsoluteUrl(url));
        client.delete(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
