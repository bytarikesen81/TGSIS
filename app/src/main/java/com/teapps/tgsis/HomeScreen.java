package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {
    private Button hs_posts, hs_media, hs_grads, hs_options, hs_signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        this.hs_posts = (Button) findViewById(R.id.hs_posts);
        hs_posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeToPosts = new Intent(HomeScreen.this, PostsMainScreen.class);
                startActivity(homeToPosts);
            }
        });

        this.hs_media = (Button) findViewById(R.id.hs_media);
        this.hs_grads = (Button) findViewById(R.id.psh_back);
        this.hs_options = (Button) findViewById(R.id.hs_options);

        this.hs_signout = (Button) findViewById(R.id.hs_signout);
        hs_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Utils.toastShortMessage(HomeScreen.this,"Çıkış yapıldı.");
                Intent homeToLogin = new Intent(HomeScreen.this, LoginScreen.class);
                startActivity(homeToLogin);
            }
        });
    }
}