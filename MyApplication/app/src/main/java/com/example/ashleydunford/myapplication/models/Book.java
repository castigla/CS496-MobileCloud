package com.example.ashleydunford.myapplication.models;

/**
 * Created by ashleycastiglione on 11/6/16.
 */

import org.json.JSONException;
import org.json.JSONObject;

public class Book {

    private String id;
    private String Title;
    private String Author;
    private String PublicationYear;
    private String BookType;
    private String BookRead;
    private String Rating;

    public Book(JSONObject object) {
        try {
            this.id = object.getString("id");
            this.Title = object.getString("title");
            this.Author= object.getString("author");
            this.PublicationYear = object.getString("publicationYear");
            this.BookType = object.getString("bookType");
            this.BookRead = object.getString("bookRead");
            this.Rating = object.getString("rating");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Book(String Title, String Content) {
        this.Title = Title;
        //this.Content = Content;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getAuthor() {
        return this.Author;
    }

    public void setAuthor(String author) {
        this.Author = author;
    }

    public String getPublicationYear() {
        return this.PublicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.PublicationYear = publicationYear;
    }

    public String getBookType() {
        return this.BookType;
    }

    public void setBookType (String bookType) {
        this.BookType = bookType;
    }

    public String getBookRead() {
        return this.BookRead;
    }

    public void setBookRead(String bookRead) {
        this.BookRead = bookRead;
    }

    public String getRating() {
        return this.Rating;
    }

    public void setRating(String rating) {
        this.Rating = rating;
    }


}