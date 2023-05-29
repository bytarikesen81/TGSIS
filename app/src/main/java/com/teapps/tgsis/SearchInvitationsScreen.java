package com.teapps.tgsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchInvitationsScreen extends AppCompatActivity {
    //UI Components
    Spinner srcinv_spinnerTime, srcinv_spinnerDist;
    RecyclerView srcinv_recyclerView;
    PostAdapter postAdapter;
    long minTime, maxTime, minDist, maxDist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_invitations_screen);
        minTime = 3;
        maxTime = 360;
        minDist = 1;
        maxDist = 100000;
        initializeUIComponents();
        postAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart(){
       super.onStart();
       postAdapter.startListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        postAdapter.notifyDataSetChanged();
    }

    protected void onStop(){
        super.onStop();
        postAdapter.stopListening();
    }

    private void initializeUIComponents() {
        srcinv_spinnerTime = (Spinner) findViewById(R.id.srcinv_spinnerTime);
        srcinv_spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBuf = srcinv_spinnerTime.getSelectedItem().toString();
                minTime = Long.parseLong(selectedBuf.split("-")[0]);
                maxTime = Long.parseLong(selectedBuf.split("-")[1]);
                //setupRecycler(minTime,maxTime,minDist,maxDist);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        srcinv_spinnerDist = (Spinner) findViewById(R.id.srcinv_spinnerDist);
        srcinv_spinnerDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBuf = srcinv_spinnerDist.getSelectedItem().toString();
                minDist = Long.parseLong(selectedBuf.split("-")[0]);
                maxDist = Long.parseLong(selectedBuf.split("-")[1]);
                //setupRecycler(minTime,maxTime,minDist,maxDist);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        srcinv_recyclerView = (RecyclerView) findViewById(R.id.srcinv_recyclerView);
        setupRecycler(minTime,maxTime,minDist,maxDist);
    }

    private void setupRecycler(long minTime, long maxTime, long minDist, long maxDist){
        Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("status", "INVITING").orderBy("name",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Student> options = new FirestoreRecyclerOptions.Builder<Student>()
                .setQuery(query, Student.class).build();
        srcinv_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(options, this);
        srcinv_recyclerView.setAdapter(postAdapter);
    }
}