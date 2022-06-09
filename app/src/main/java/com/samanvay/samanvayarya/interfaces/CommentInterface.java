package com.samanvay.samanvayarya.interfaces;

import com.samanvay.samanvayarya.repository.JsonResponseComments;


import retrofit2.Call;
import retrofit2.http.GET;

public interface CommentInterface {
    @GET("v3/fee53ac8-e6dc-4a88-8203-81fbb390fae6")
    Call<JsonResponseComments> getComments();
}
