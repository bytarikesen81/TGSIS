package com.teapps.tgsis;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView rec_twtitle, rec_twdesc, rec_twaddr, rec_twtime, rec_twdist;
    Button rec_btnPair;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeUIComponents(itemView);
    }
    private void initializeUIComponents(@NonNull View itemView){
        rec_twtitle = (TextView) itemView.findViewById(R.id.rec_twtitle);
        rec_twdesc = (TextView) itemView.findViewById(R.id.rec_twdesc);
        rec_twaddr = (TextView) itemView.findViewById(R.id.rec_twaddr);
        rec_twtime = (TextView) itemView.findViewById(R.id.rec_twtime);
        rec_twdist = (TextView) itemView.findViewById(R.id.rec_twdist);
        rec_btnPair = (Button) itemView.findViewById(R.id.rec_btnPair);
    }
}
