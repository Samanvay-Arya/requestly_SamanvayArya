package com.samanvay.samanvayarya.repository;

import com.google.gson.annotations.SerializedName;

public class CommentsType {
    // defining

    @SerializedName("username")
    String Name;
    @SerializedName("email")
    String Email;
    @SerializedName("postId")
    String PostId;
    @SerializedName("body")
    String Body;
    @SerializedName("id")
    String Id;

    // getters

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPostId() {
        return PostId;
    }

    public String getBody() {
        return Body;
    }

    public String getId() {
        return Id;
    }

    // ...


    // setters

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public void setBody(String body) {
        Body = body;
    }

    public void setId(String id) {
        Id = id;
    }
    // ...

}
