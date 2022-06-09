package com.samanvay.samanvayarya;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.samanvay.samanvayarya.RecyclerAdapters.PostAdapter;
import com.samanvay.samanvayarya.interfaces.CommentInterface;
import com.samanvay.samanvayarya.interfaces.PostInterface;
import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.JsonResponse;
import com.samanvay.samanvayarya.repository.JsonResponseComments;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import io.requestly.rqinterceptor.api.RQCollector;
import io.requestly.rqinterceptor.api.RQInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView Post_recyclerView;
    private List<PostType> postList;
    private List<CommentsType> CommentsList;
    LoadingDialog loadingDialog;
    OkHttpClient okHttpClient;
    Button retry;
    ImageView noInternet;

    //url for post api call
    //https://dummyjson.com/posts


    //url for comments api call
    //https://run.mocky.io/v3/4f9357f0-fb81-4222-a668-c3ac3e8dea64


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // defining views of main activity

        Post_recyclerView=findViewById(R.id.MainActivity_Post_RecyclerView);
        loadingDialog=new LoadingDialog(MainActivity.this);
        retry=findViewById(R.id.RetryButton_MainActivity);
        noInternet=findViewById(R.id.imageView_NoInternet_MainActivity);


        // ...


        // start progress bar
        loadingDialog.startLoadingDialog();
        // ...
        if (isNetworkConnected()){

            //Initialising 'requestly' Interceptor
            RQCollector UCollectionsKt = new RQCollector(MainActivity.this,"PYCpuxBT06zNMqAbuByG");
            RQInterceptor rqInterceptor=new RQInterceptor.Builder(MainActivity.this)
                    .collector(UCollectionsKt)
                    .build();
            okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(rqInterceptor)
                    .build();

            // ...


            CallCommentApi();
        }
        else{
            loadingDialog.dismissDialog();
            retry.setVisibility(View.VISIBLE);
            noInternet.setVisibility(View.VISIBLE);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity cur=MainActivity.this;
                    cur.recreate();
                }


            });
        }


    }

    private void CallCommentApi() {
        Retrofit CommentsRetrofit=new Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CommentInterface commentInterface=CommentsRetrofit.create(CommentInterface.class);
        Call<JsonResponseComments> callComments=commentInterface.getComments();

        callComments.enqueue(new Callback<JsonResponseComments>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponseComments> call, @NonNull Response<JsonResponseComments> response) {
                if (!response.isSuccessful()){
                    return;
                }
                Log.d("response", "onResponse: Comments successful ");
                // taking response as JsonResponseComments
                JsonResponseComments jsonResponseComments=response.body();
                // ...

                // Filling the CommentList by jsonResponseComments (through getComments method defined in CommentsInterface)
                CommentsList=new ArrayList<>(Arrays.asList(jsonResponseComments.getComments()));
                // ...

                // Calling Post Api Method for posts to load in recyclerView
                CallPostApi();
                // ...





            }

            @Override
            public void onFailure(@NonNull Call<JsonResponseComments> call, @NonNull Throwable t) {
                Log.d("response", "onResponse: Comments failure");
                loadingDialog.dismissDialog();

            }
        });
    }

    private void CallPostApi() {
        Retrofit PostRetrofit=new Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostInterface postInterface=PostRetrofit.create(PostInterface.class);
        Call<JsonResponse> call1=postInterface.getPosts();
        call1.enqueue(new Callback<JsonResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<JsonResponse> call, @NonNull Response<JsonResponse> response) {
                if (!response.isSuccessful()){

                    return;
                }
                Log.d("response", "onResponse: Post successful ");
                JsonResponse jsonResponse=response.body();
                postList=new ArrayList<>(Arrays.asList(jsonResponse.getPosts()));
                loadingDialog.dismissDialog();

                fillPost();

            }

            @Override
            public void onFailure(@NonNull Call<JsonResponse> call, @NonNull Throwable t) {
                Log.d("response", "onResponse: Post failure");
                fillPost();
            }
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void fillPost() {


        PostAdapter adapter = new PostAdapter(postList, MainActivity.this, CommentsList);
        Post_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        Post_recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
        adapter.setOnItemClickListener(position -> {
        });



    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}