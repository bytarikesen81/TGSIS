package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchConfigScreen extends AppCompatActivity {
    EditText con_timeInput, con_distInput;
    Button con_btnSave, con_btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_config_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents() {
        con_timeInput = (EditText) findViewById(R.id.con_timeInput);
        con_distInput = (EditText) findViewById(R.id.con_distInput);
        con_btnSave = (Button) findViewById(R.id.con_btnSave);
        con_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());
                long time, dist;

                if(con_timeInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SearchConfigScreen.this, "Zaman alanı boş bırakılamaz.");
                else if(con_distInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SearchConfigScreen.this, "Mesafe alanı boş bırakılamaz.");
                else{
                    time = Long.parseLong(con_timeInput.getText().toString());
                    dist = Long.parseLong(con_distInput.getText().toString());
                    docRef.update("time", time);
                    docRef.update("distance", dist);
                    if(time <= 0  || time > 365)
                        Utils.toastLongMessage(SearchConfigScreen.this, "Belirlenen paylaşım süresi izin verilen aralığın dışında.\nPaylaşım süresi en az 3, en fazla 365 gün olabilir.");
                    else if(dist <= 0 || dist > 100000)
                        Utils.toastLongMessage(SearchConfigScreen.this, "Evinizin kampüse uzaklığı izin verilen menzilin dışında.\nİlan vermek için evinizin kampüsten en fazla 100 km uzaklıkta olması ve negatif/0 değeri girilmemesi gerekmektedir.");
                    else{
                        Utils.toastLongMessage(SearchConfigScreen.this, "Ayarlar güncellendi.");
                        startActivity(new Intent(SearchConfigScreen.this, SearchScreen.class));
                    }
                }
            }
        });

        con_btnBack = (Button) findViewById(R.id.con_btnBack);
        con_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchConfigScreen.this, SearchScreen.class));
            }
        });
    }
}