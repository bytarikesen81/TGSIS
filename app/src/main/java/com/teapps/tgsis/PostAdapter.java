package com.teapps.tgsis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;

public class PostAdapter extends FirestoreRecyclerAdapter<Student, PostViewHolder> {
    Context context;
    HashMap<String, Object> postTable;
    FirebaseUser currentUser;
    DocumentReference docRefCurUser;

    public PostAdapter(@NonNull FirestoreRecyclerOptions<Student> options, Context context) {
        super(options);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        docRefCurUser = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Student std) {
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(std.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                postTable = (HashMap<String, Object>) documentSnapshot.get("post");
                holder.rec_twtitle.setText((String)postTable.get("title"));
                holder.rec_twdesc.setText((String)postTable.get("description"));
                holder.rec_twaddr.setText((String)postTable.get("address"));
                holder.rec_twtime.setText(String.valueOf("Paylaşım Süresi:"+std.getTime()+" Gün"));
                holder.rec_twdist.setText(String.valueOf("Kampüs Mesafesi:"+std.getDistance()+" m"));
            }
        });

        holder.rec_btnPair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRefCurUser.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        long time = 0, dist = 0;
                        String status = (String)documentSnapshot.get("status");
                        time = (long)documentSnapshot.get("time");
                        dist = (long)documentSnapshot.get("distance");
                        if(time != std.getTime())
                            Toast.makeText(context.getApplicationContext(), "İlanın paylaşım süresi kriterlerinizi karşılamıyor.\nİlan üzerinden eşleşme yapmak için lütfen zaman kriterinizi ilana uygun hale getirin.", Toast.LENGTH_LONG).show();
                        else if(dist != std.getDistance())
                            Toast.makeText(context.getApplicationContext(), "İlanın kampüse olan mesafesi kriterlerinizi karşılamıyor.\nİlan üzerinden eşleşme yapmak için lütfen mesafe kriterinizi ilana uygun hale getirin.", Toast.LENGTH_LONG).show();
                        else if(!status.matches("SEARCHING"))
                            Toast.makeText(context.getApplicationContext(), "İlan üzerinden eşleşmek için lütfen öncelikle mevcut ilanınızı veya eşleşmenizi iptal edin.", Toast.LENGTH_LONG).show();
                        else if(std.getPairedStudentID().matches((String)documentSnapshot.get("uid")))
                            Toast.makeText(context.getApplicationContext(), "Bu ilan için zaten daha önce eşleşme isteği gönderdiniz.", Toast.LENGTH_LONG).show();
                        else{
                            docRefCurUser.update("pairedStudentID", std.getUid());
                            docRef.update("pairedStudentID", (String)documentSnapshot.get("uid"));
                            Toast.makeText(context.getApplicationContext(), "Eşleşme isteği gönderildi", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_post_item, parent, false);
        return new PostViewHolder(postView);
    }
}
