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

import com.samanvay.samanvayarya.R;
import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.VH> {
    private final List<PostType> Posts;
    private final Context context;
    private static PostAdapter.OnItemClickListener listener;
    private final List<CommentsType> CommentsList;
//    List<CommentsType> TempList;
    private int opened=0,postId;



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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PostType post=Posts.get(position);
        holder.Title.setText( post.getTitle());
        holder.Body.setText(post.getBody());
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.photo_anim));

    }

    @Override
    public int getItemCount() {
        if ((Posts!=null)){
            return Posts.size();
        }
        else {
            return 0;
        }
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView Title,Body,ShowComments;
        RecyclerView comments;
        CardView cardView;
        public VH(@NonNull View itemView) {
            super(itemView);
            Title=itemView.findViewById(R.id.Title_Post_CardDesign_TV);
            Body=itemView.findViewById(R.id.Body_Post_CardDesign_TV);
            comments=itemView.findViewById(R.id.Post_comments_RecyclerView);
            cardView=itemView.findViewById(R.id.cardView_PostCardDesign);
            ShowComments=itemView.findViewById(R.id.ShowComments_PostCardDesign);

            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();

                    if (position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(position);


                        if (opened==1){
                            comments.setVisibility(View.GONE);
                            ShowComments.setText("Show Comments");
                            opened=0;
                        }
                        else{
                            postId=Posts.get(position).getId();
                            comments.setVisibility(View.VISIBLE);
//                            if (CommentsList!=null){
//                                Log.d("TempList", "onClick: CommentList is not null");
//                                for (int i=0;i<CommentsList.size();i++){
//                                    if(Integer.parseInt(CommentsList.get(i).getName())==postId){
//                                        TempList.add(CommentsList.get(i));
//                                        Log.d("TempList", "onClick: added "+i);
//                                    }
//                                }
//                            }
//                            Boolean bool=postId==2;

                            CommentsAdapter commentsAdapter=new CommentsAdapter(CommentsList, context, postId);
                            comments.setLayoutManager(new LinearLayoutManager(context));
                            comments.setAdapter(commentsAdapter);
                            commentsAdapter.notifyDataSetChanged();
                            ShowComments.setText("Hide"+postId);
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
