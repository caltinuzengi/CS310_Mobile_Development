package com.example.trail_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cs310newstrail.News;

import java.util.List;

public class NewsDetailsActivity extends AppCompatActivity {


    ImageView newsImage;
    TextView newsHeading, newsDate, newsText;
    int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            com.example.cs310newstrail.News currentNews = (News) message.obj;

            newsHeading.setText(currentNews.getTitle());
            newsDate.setText(currentNews.getDate());
            newsText.setText(currentNews.getText());
            newsId = currentNews.getId();

            NewsRepository repo = new NewsRepository();
            repo.downloadImage(((NewsApplication)getApplication()).srv, imgHandler, currentNews.getImg());

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bitmap img = (Bitmap) message.obj;
            newsImage.setImageBitmap(img);
            return true;
        }
    });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF424242"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        String cName = getIntent().getStringExtra("categoryName");
        int newsId = getIntent().getIntExtra("id", 1);

        setTitle(cName);

        ActionBar currentBar = getSupportActionBar();
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeButtonEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.back_arrow);

        newsImage = findViewById(R.id.newsImage);
        newsHeading = findViewById(R.id.newsHeading);
        newsDate = findViewById(R.id.newsDate);
        newsText = findViewById(R.id.newsText);

        NewsRepository repo = new NewsRepository();
        repo.GetNewsById(((NewsApplication)getApplication()).srv, dataHandler, newsId);





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        if(item.getItemId() == R.id.showComment){
            Intent i = new Intent(this,ShowCommentActivity.class);
            i.putExtra("id", newsId);
            this.startActivity(i);
        }

        return true;
    }


}