package com.samanvay.samanvayarya.interfaces;

import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CommentInterface {
    @GET("posts/1/comments")
    Call<List<CommentsType>> getComments();
}
