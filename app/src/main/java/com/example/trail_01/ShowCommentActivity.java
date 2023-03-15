package com.example.trail_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

public class ShowCommentActivity extends AppCompatActivity {

    ProgressBar prg;
    RecyclerView recView;
    int newsId;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            List<Comment> data = (List<Comment>) message.obj;
            CommentAdapter adp = new CommentAdapter(ShowCommentActivity.this, data);
            recView.setAdapter(adp);
            recView.setVisibility(View.VISIBLE);
            prg.setVisibility(View.INVISIBLE);

            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_comment);

        newsId = getIntent().getIntExtra("id", 1);

        setTitle("Comments");

        ActionBar currentBar = getSupportActionBar();
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeButtonEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.back_arrow);

        recView = findViewById(R.id.recComments);
        prg = findViewById(R.id.progressBarList);
        recView.setLayoutManager(new LinearLayoutManager(this));
        prg.setVisibility(View.VISIBLE);
        recView.setVisibility(View.INVISIBLE);

        CommentRepository repo = new CommentRepository();
        repo.GetCommentsByNewsId(((NewsApplication)getApplication()).srv, dataHandler, newsId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_comments_menu, menu);

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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        if(item.getItemId() == R.id.postComment){
            Intent i = new Intent(this,PostCommentActivity.class);
            i.putExtra("id", newsId);
            this.startActivity(i);
        }

        return true;
    }
}