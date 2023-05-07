package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PostsMainScreen extends AppCompatActivity {
    private Button psh_create, psh_view, psh_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_main_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        this.psh_create = (Button) findViewById(R.id.psh_create);
        psh_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeToCreate = new Intent(PostsMainScreen.this, PostsCreateScreen.class);
                startActivity(homeToCreate);
            }
        });

        this.psh_view = (Button) findViewById(R.id.psh_view);

        this.psh_back = (Button) findViewById(R.id.psh_back);
        psh_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postsToHome = new Intent(PostsMainScreen.this, HomeScreen.class);
                startActivity(postsToHome);
            }
        });
    }
}