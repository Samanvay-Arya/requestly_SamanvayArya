package com.samanvay.samanvayarya.interfaces;

import com.samanvay.samanvayarya.repository.JsonResponse;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostInterface {
    @GET("posts")
    Call<JsonResponse> getPosts();
}
