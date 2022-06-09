package com.samanvay.samanvayarya.RecyclerAdapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.samanvay.samanvayarya.R;
import com.samanvay.samanvayarya.repository.CommentsType;


import java.util.List;
import java.util.Objects;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.VH> {
    private final List<CommentsType> CommentsList;
    private final Context context;
     int postId;

    public CommentsAdapter(List<CommentsType> commentsList, Context context, int postId) {
        CommentsList = commentsList;
        this.context = context;
        this.postId = postId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comments_card_design,parent,false);
        return new VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CommentsType comment=CommentsList.get(position);
        Boolean aBoolean=Integer.parseInt(comment.getName())==postId;
        Log.d("TempList", "onClick: "+ aBoolean);

        if (aBoolean){
//            String name=comment.getName();
            Log.d("TempList", "onClick: boolean is true");
        holder.Name.setText("Name:  "+ Objects.requireNonNull(comment.getName()));
        holder.Email.setText(Objects.requireNonNull(comment.getEmail()));
            holder.Body.setText("Comment:  "+Objects.requireNonNull(comment.getBody()));
        }
        else{
            Log.d("TempList", "onClick: else ");
            holder.constraintLayout.setVisibility(View.GONE);
        }
        Log.d("TempList", "onClick: out ");

    }

    @Override
    public int getItemCount() {
        if ((CommentsList!=null)){
            return CommentsList.size();
        }
        else {
            return 0;
        }


    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView Name, Email, Body;
        ConstraintLayout constraintLayout;
        public VH(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.Name_Comments_CardDesign_TV);
            Email=itemView.findViewById(R.id.Email_Comments_CardDesign_TV);
            Body=itemView.findViewById(R.id.Body_Comments_CardDesign_TV);
            constraintLayout=itemView.findViewById(R.id.ConstraintLayout_CommentsCardDesign);
        }
    }
}
