package com.samanvay.samanvayarya.RecyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samanvay.samanvayarya.MainActivity;
import com.samanvay.samanvayarya.R;
import com.samanvay.samanvayarya.interfaces.PostInterface;
import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.List;

import io.requestly.rqinterceptor.api.RQCollector;
import io.requestly.rqinterceptor.api.RQInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.VH> {
    private List<PostType> Posts;
    private Context context;
    private static PostAdapter.OnItemClickListener listener;
    private List<CommentsType> CommentsList;
    private int opened=0;


    public PostAdapter(List<PostType> posts, Context context, List<CommentsType> commentsList) {
        Posts = posts;
        this.context = context;
        CommentsList = commentsList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.post_card_design,parent,false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PostType post=Posts.get(position);
        holder.Title.setText(post.getTitle());
        holder.Body.setText(post.getBody());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.photo_anim));

    }

    @Override
    public int getItemCount() {
        return Posts.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView Title;
        TextView Body;
        RecyclerView comments;
        CardView cardView;
        public VH(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.Title_Post_CardDesign_TV);
            Body=itemView.findViewById(R.id.Body_Post_CardDesign_TV);
            comments=itemView.findViewById(R.id.Post_comments_RecyclerView);
            cardView=itemView.findViewById(R.id.cardView_PostCardDesign);
            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if (position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(position);


                        if (opened==1){
                            comments.setVisibility(View.GONE);
                            opened=0;
                        }
                        else{
                            comments.setVisibility(View.VISIBLE);
                            CommentsAdapter commentsAdapter=new CommentsAdapter(CommentsList,context);
                            comments.setLayoutManager(new LinearLayoutManager(context));
                            comments.setAdapter(commentsAdapter);
                            commentsAdapter.notifyDataSetChanged();
                            opened=1;
                        }


                    }
                }
            });



        }
    }
    public interface OnItemClickListener{
        void onItemClick( int position);
    }
    public void setOnItemClickListener(PostAdapter.OnItemClickListener listener){
        PostAdapter.listener=listener;
    }

}
