package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity {
    private Button hs_btnInvite, hs_btnSearch, hs_btnPairs, hs_btnProfile, hs_btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        this.hs_btnInvite = (Button) findViewById(R.id.hs_btnInvite);
        hs_btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, InviteScreen.class));
            }
        });

        this.hs_btnSearch = (Button) findViewById(R.id.hs_btnSearch);
        this.hs_btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this, SearchScreen.class));
            }
        });

        this.hs_btnPairs = (Button) findViewById(R.id.hs_btnPairs);

        this.hs_btnProfile = (Button) findViewById(R.id.hs_btnProfile);
        hs_btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, ProfileScreen.class));
            }
        });

        this.hs_btnLogout = (Button) findViewById(R.id.hs_btnLogout);
        hs_btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Utils.toastShortMessage(HomeScreen.this,"Çıkış yapıldı.");
                startActivity(new Intent(HomeScreen.this, LoginScreen.class));
            }
        });
    }
}