package com.samanvay.samanvayarya.RecyclerAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samanvay.samanvayarya.R;
import com.samanvay.samanvayarya.repository.CommentsType;
import com.samanvay.samanvayarya.repository.PostType;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.VH> {
    private List<CommentsType> CommentsList;
    private Context context;

    public CommentsAdapter(List<CommentsType> commentsList, Context context) {
        CommentsList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comments_card_design,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CommentsType comment=CommentsList.get(position);
        holder.Name.setText(comment.getName());
        holder.Email.setText(comment.getEmail());
        holder.Body.setText(comment.getBody());


    }

    @Override
    public int getItemCount() {
        return CommentsList.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView Name, Email, Body;
        public VH(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.Name_Comments_CardDesign_TV);
            Email=itemView.findViewById(R.id.Email_Comments_CardDesign_TV);
            Body=itemView.findViewById(R.id.Body_Comments_CardDesign_TV);
        }
    }
}
