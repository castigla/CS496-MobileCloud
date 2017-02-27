package com.example.ashleydunford.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.ashleydunford.myapplication.BooksActivity;
import com.example.ashleydunford.myapplication.R;
import com.example.ashleydunford.myapplication.clients.MyAPIClient;
import com.example.ashleydunford.myapplication.models.Book;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class BookAdapter extends ArrayAdapter<Book> {
    private static class ViewHolder {
        TextView id;
        TextView title;
        TextView author;
        TextView publicationYear;
        TextView bookType;
        TextView bookRead;
        TextView rating;
    }

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, R.layout.item_book, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_book, parent, false);

            viewHolder.id = (TextView) convertView.findViewById(R.id.value_note_id);
            viewHolder.title = (TextView) convertView.findViewById(R.id.value_note_title);
//            viewHolder.author = (TextView) convertView.findViewById(R.id.value_author);
//            viewHolder.publicationYear = (TextView) convertView.findViewById(R.id.label_publicationYear);
//            viewHolder.bookType = (TextView) convertView.findViewById(R.id.label_bookType);
//            viewHolder.bookRead = (TextView) convertView.findViewById(R.id.label_bookRead);
//            viewHolder.rating = (TextView) convertView.findViewById(R.id.label_rating);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.id.setText(book.getId());
        viewHolder.title.setText(book.getTitle());
//        viewHolder.author.setText(book.getAuthor());
//        viewHolder.publicationYear.setText(book.getPublicationYear());
//        viewHolder.bookType.setText(book.getBookType());
//        viewHolder.bookRead.setText(book.getBookRead());
//        viewHolder.rating.setText(book.getRating());

        Button deleteBtn = (Button)convertView.findViewById(R.id.delete);
        Button editBtn = (Button)convertView.findViewById(R.id.edit);
        final Intent intent = new Intent(this.getContext(), BooksActivity.class);
        final Context context = this.getContext();
        final String username = BooksActivity.username;
        intent.putExtra("username", username);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("delete button listener");
                System.out.println(book.getTitle());
                String id = book.getId();
                RequestParams params = new RequestParams();
                params.put("id", id);

                MyAPIClient.delete("/book", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                        System.out.println("Success?");
                        System.out.println(object.toString());
                        context.startActivity(intent);
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
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("edit button listener");
                System.out.println(book.getTitle());
                String id = book.getId();
                String title = viewHolder.title.getText().toString();
                System.out.println(viewHolder.title.getText().toString());
                RequestParams params = new RequestParams();
                params.put("id", id);
                params.put("title", title);
               // "bookRead" : true, "author" : "hmmm", "rating" : 64, "publicationYear" : 1982, "id" : 32, "title" : "hmmm", "bookType" : "Non-Fiction"

                MyAPIClient.put("/book", params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers1, JSONObject object) {
                        System.out.println("Success?");
                        System.out.println(object.toString());
                        context.startActivity(intent);
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
        });

        return convertView;
    }
}
