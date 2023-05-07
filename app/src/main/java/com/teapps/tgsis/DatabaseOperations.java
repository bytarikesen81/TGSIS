package com.teapps.tgsis;

import android.app.Activity;
import android.media.Image;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DatabaseOperations {
    private FirebaseFirestore databaseInstance;
    private User activeUser;
    public DatabaseOperations(){
        activeUser = new User();
        this.databaseInstance = FirebaseFirestore.getInstance();
    }

    public void saveUserInfo(User user, Activity activeContext){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid());
        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.toastLongMessage(activeContext,"User info added to the database.");
                }
                else{
                    Utils.toastLongMessage(activeContext,task.getException().getLocalizedMessage());
                }
            }
        });
    }

    public User loadUserInfo(Activity activeContext){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    if(!documentSnapshot.get("education").equals(null))
                        activeUser.setEducation((ArrayList<Education>) documentSnapshot.get("education"));
                    if(!documentSnapshot.get("entryDate").equals(null))
                        activeUser.setEntryDate((String) documentSnapshot.get("entryDate"));
                    if(!documentSnapshot.get("gradutationDate").equals(null))
                        activeUser.setGraduationDate((String) documentSnapshot.get("gradutationDate"));
                    if(!documentSnapshot.get("mail").equals(null))
                        activeUser.setMail((String)documentSnapshot.get("mail"));
                    if(!documentSnapshot.get("media").equals(null))
                        activeUser.setMedia((ArrayList<UserMedia>) documentSnapshot.get("media"));
                    if(!documentSnapshot.get("name").equals(null))
                        activeUser.setName((String) documentSnapshot.get("name"));
                    if(!documentSnapshot.get("surname").equals(null))
                        activeUser.setSurname((String) documentSnapshot.get("surname"));
                    if(!documentSnapshot.get("password").equals(null))
                        activeUser.setPassword((String) documentSnapshot.get("password"));
                    if(!documentSnapshot.get("phoneNumber").equals(null))
                        activeUser.setPhoneNumber((String) documentSnapshot.get("phoneNumber"));
                    if(!documentSnapshot.get("profileImage").equals(null))
                        activeUser.setPosts((ArrayList<Post>) documentSnapshot.get("profileImage"));
                    if(!documentSnapshot.get("profileImage").equals(null))
                        activeUser.setProfileImage((Image) documentSnapshot.get("profileImage"));
                    if(!documentSnapshot.get("work").equals(null))
                        activeUser.setWork((Work) documentSnapshot.get("work"));
                }
                else
                    Utils.toastLongMessage(activeContext, "User not found in the database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.toastLongMessage(activeContext, e.getLocalizedMessage());
            }
        });
        return activeUser;
    }
}
