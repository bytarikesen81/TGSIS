package com.teapps.tgsis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostsCreateScreen extends AppCompatActivity {
    private EditText psc_titleInput, psc_contentInput, psc_dateInput;
    private Button psc_submit;

    private DatabaseOperations database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_create_screen);
        database = new DatabaseOperations();
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        psc_titleInput = (EditText)findViewById(R.id.psc_titleInput);
        psc_contentInput = (EditText)findViewById(R.id.psc_contentInput);
        psc_dateInput = (EditText)findViewById(R.id.psc_dateInput);

        psc_submit  = (Button)findViewById(R.id.psc_submit);
        psc_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(psc_titleInput.getText().toString().matches("") || psc_contentInput.getText().toString().matches("") ||
                        psc_dateInput.getText().toString().matches(""))){
                    User curUserObj = database.loadUserInfo(PostsCreateScreen.this);
                    Post newPost = new Post(curUserObj, psc_titleInput.getText().toString(), psc_contentInput.getText().toString(), psc_dateInput.getText().toString());
                    curUserObj.getPosts().add(newPost);

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users")
                            .document(currentUser.getUid());
                    documentReference.update("posts", curUserObj.getPosts());
                }
                else{
                    Utils.toastLongMessage(PostsCreateScreen.this, "Alanlar boş olamaz.");
                }
            }
        });
    }
}