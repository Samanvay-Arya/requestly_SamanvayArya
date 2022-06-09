package com.samanvay.samanvayarya.interfaces;

import com.samanvay.samanvayarya.repository.JsonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("posts")
    Call<JsonResponse> getPosts();
}
