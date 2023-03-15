package com.example.trail_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;

public class PostCommentActivity extends AppCompatActivity {

    EditText userName, commentTxt;
    Button btnPostComment;
    ProgressBar prg;

    Handler postCommentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {

            String retVal = message.obj.toString();
            commentTxt.setText(retVal);

            prg.setVisibility(View.VISIBLE);



            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        int newsId = getIntent().getIntExtra("id", 1);

        setTitle("Comments");

        ActionBar currentBar = getSupportActionBar();
        currentBar.setDisplayHomeAsUpEnabled(true);
        currentBar.setHomeButtonEnabled(true);
        currentBar.setHomeAsUpIndicator(R.drawable.back_arrow);


        userName = findViewById(R.id.editTextUserName);
        commentTxt = findViewById(R.id.editTextComment);
        btnPostComment = findViewById(R.id.postComment);

        btnPostComment.setOnClickListener(v->{
            //prg.setVisibility(View.VISIBLE);
            CommentRepository repo = new CommentRepository();
            ExecutorService srv = ((NewsApplication)getApplication()).srv;

            if(userName.getText().toString().matches("") ||
                    commentTxt.getText().toString().matches("")){
                Toast.makeText(this, "There is missing type!", Toast.LENGTH_SHORT).show();
            }else{
                repo.postComment(srv, postCommentHandler,
                        userName.getText().toString(),
                        commentTxt.getText().toString(),
                        String.valueOf(newsId));

                finish();

            }

        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.post_comments_menu, menu);

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

        return true;
    }

}