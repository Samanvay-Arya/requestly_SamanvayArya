package com.samanvay.samanvayarya.repository;

import com.google.gson.annotations.SerializedName;

public class CommentsType {

    // defining
    @SerializedName("name")
    String Name;

    @SerializedName("email")
    String Email;

    @SerializedName("body")
    String Body;

    //...

    // getters
    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getBody() {
        return Body;
    }
    // ...



    //  Setters

    public void setBody(String body) {
        Body = body;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    //...


}
