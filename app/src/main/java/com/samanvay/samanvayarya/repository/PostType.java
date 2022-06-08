package com.samanvay.samanvayarya.repository;

import com.google.gson.annotations.SerializedName;

public class PostType {

    // defining
    @SerializedName("userId")
    private int UserID;
    @SerializedName("id")
    private int Id;
    @SerializedName("title")
    private String Title;
    @SerializedName("body")
    private String Body;
    //...

    // getters

    public int getUserID() {
        return UserID;
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }

    //...



    // setters

    public void setBody(String body) {
        Body = body;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public void setId(int id) {
        Id = id;
    }
    public void setUserID(int userID) {
        UserID = userID;
    }

    //...
}
