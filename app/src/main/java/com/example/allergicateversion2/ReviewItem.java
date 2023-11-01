package com.example.allergicateversion2;

import java.util.ArrayList;


        import java.util.ArrayList;
        import java.util.List;

public class ReviewItem {
    private String user;

    public String getComment() {
        return comment;
    }

    private String comment;

    int rating;
    public ReviewItem( String user, int rating, String comment) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    public int getRating() {
        return this.rating;
    }
    public String getUser() {
        return this.user;
    }

}
