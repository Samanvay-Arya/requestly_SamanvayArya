package com.samanvay.samanvayarya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samanvay.samanvayarya.RecyclerAdapters.PostAdapter;
import com.samanvay.samanvayarya.interfaces.CommentInterface;
import com.samanvay.samanvayarya.interfaces.PostInterface;
import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.ArrayList;
import java.util.List;

import io.requestly.rqinterceptor.api.RQCollector;
import io.requestly.rqinterceptor.api.RQInterceptor;
import kotlin.collections.UCollectionsKt;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private PostAdapter adapter;
    private RecyclerView Post_recyclerView;
    private List<PostType> postList;
    private List<CommentsType> CommentsList;
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Post_recyclerView=findViewById(R.id.MainActivity_Post_RecyclerView);
        loadingDialog=new LoadingDialog(MainActivity.this);
        loadingDialog.startLoadingDialog();



        RQCollector UCollectionsKt = new RQCollector(MainActivity.this,"PYCpuxBT06zNMqAbuByG");
        RQInterceptor rqInterceptor=new RQInterceptor.Builder(MainActivity.this)
                                                      .collector(UCollectionsKt)
                                                       .build();
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(rqInterceptor)
                .build();

//        textViewResult=findViewById(R.id.TextViewMainActivity);



        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        CommentInterface commentInterface=retrofit.create(CommentInterface.class);
        Call<List<CommentsType>> callComments=commentInterface.getComments();

        callComments.enqueue(new Callback<List<CommentsType>>() {
            @Override
            public void onResponse(Call<List<CommentsType>> call, Response<List<CommentsType>> response) {
                if (!response.isSuccessful()){

                    return;
                }
                if(response.isSuccessful()){
                    Log.d("response", "onResponse: successful ");
                }
                CommentsList=response.body();

            }

            @Override
            public void onFailure(Call<List<CommentsType>> call, Throwable t) {
                Log.d("response", "onResponse: failure");

            }
        });

        PostInterface postInterface=retrofit.create(PostInterface.class);
        Call<List<PostType>> call=postInterface.getPosts();
        call.enqueue(new Callback<List<PostType>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<PostType>> call, Response<List<PostType>> response) {
                if (!response.isSuccessful()){

                    return;
                }
                if(response.isSuccessful()){
                    Log.d("response", "onResponse: successful ");
                }
                postList=response.body();

                fillPost();

            }

            @Override
            public void onFailure(Call<List<PostType>> call, Throwable t) {
                Log.d("response", "onResponse: failure");
            }
        });


    }



    private void fillPost() {


        adapter=new PostAdapter(postList,MainActivity.this, CommentsList);
        Post_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        Post_recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        loadingDialog.dismissDialog();
        adapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });



    }
}