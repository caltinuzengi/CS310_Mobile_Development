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

import com.example.cs310newstrail.News;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.LogRecord;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    private Context ctx;
    private List<com.example.cs310newstrail.News> data;

    public NewsAdapter(Context ctx, List<News> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.news_row_layout,parent,false);

        NewsHolder holder = new NewsHolder(root);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position) {

        holder.txtHeading.setText(data.get(holder.getAdapterPosition()).getTitle());
        NewsApplication app = (NewsApplication) ((AppCompatActivity)ctx).getApplication();

        holder.downloadImage(app.srv, data.get(holder.getAdapterPosition()).getImg());

        holder.row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ctx, NewsDetailsActivity.class);
                i.putExtra("id", data.get(holder.getAdapterPosition()).getId());
                i.putExtra("categoryName", data.get(holder.getAdapterPosition()).getCategoryName());
                ctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView txtDate;
        TextView txtHeading;
        CardView row;
        boolean imgDownloaded;

        android.os.Handler imgHandler = new android.os.Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                Bitmap img = (Bitmap)msg.obj;
                image.setImageBitmap(img);
                imgDownloaded = true;
                return true;
            }
        });

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.newsImg);
            txtDate = itemView.findViewById(R.id.newsDate);
            txtHeading = itemView.findViewById(R.id.newsHeading);
            row = itemView.findViewById(R.id.row_layout);
        }

        public void downloadImage(ExecutorService srv, String path){

            if (!imgDownloaded){

                NewsRepository repo = new NewsRepository();
                repo.downloadImage(srv,imgHandler,path);


            }
        }
    }
}
