package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchScreen extends AppCompatActivity {
    Button srch_btnSearchInv, srch_btnConfig, srch_btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        srch_btnSearchInv = (Button) findViewById(R.id.srch_btnSearchInv);
        srch_btnSearchInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchScreen.this, SearchInvitationsScreen.class));
            }
        });

        srch_btnConfig = (Button) findViewById(R.id.srch_btnConfig);
        srch_btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchScreen.this, SearchConfigScreen.class));
            }
        });

        srch_btnBack = (Button) findViewById(R.id.srch_btnBack);
        srch_btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchScreen.this, HomeScreen.class));
            }
        });
    }
}