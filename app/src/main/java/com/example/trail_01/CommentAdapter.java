package com.example.trail_01;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private Context ctx;
    private List<Comment> data;

    public CommentAdapter(Context ctx, List<Comment> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.comment_row_layout, parent, false);

        CommentHolder holder = new CommentHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {

        holder.txtUserName.setText(data.get(holder.getAdapterPosition()).getName());
        holder.txtComment.setText(data.get(holder.getAdapterPosition()).getText());
        NewsApplication app = (NewsApplication) ((AppCompatActivity) ctx).getApplication();


    }


    public class CommentHolder extends RecyclerView.ViewHolder {

        TextView txtUserName;
        TextView txtComment;
        CardView row;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName = itemView.findViewById(R.id.userName);
            txtComment = itemView.findViewById(R.id.commentText);
            row = itemView.findViewById(R.id.comment_row_layout);
        }

    }
}
