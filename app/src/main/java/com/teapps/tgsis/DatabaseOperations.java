package com.teapps.tgsis;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseOperations {
    private FirebaseFirestore databaseInstance;
    public DatabaseOperations(){
        this.databaseInstance = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDatabaseInstance() {
        return databaseInstance;
    }

    public void setDatabaseInstance(FirebaseFirestore databaseInstance) {
        this.databaseInstance = databaseInstance;
    }

    public void saveStudentInfo(Student student, Activity activeContext){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = databaseInstance.collection("users")
                .document(currentUser.getUid());
        documentReference.set(student).addOnCompleteListener(new OnCompleteListener<Void>() {
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


    /*
    public void loadStudentInfo(Student activeStudent, Activity activeContext){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = databaseInstance.collection("users")
                .document(currentUser.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String, Object> educationMap;
                String statusStream;
                if(documentSnapshot.exists()) {
                    if (documentSnapshot.get("name") != null)
                        activeStudent.setName((String) documentSnapshot.get("name"));
                    if(documentSnapshot.get("surname") != null)
                        activeStudent.setSurname((String) documentSnapshot.get("surname"));
                    if(documentSnapshot.get("email") != null)
                        activeStudent.setEmail((String) documentSnapshot.get("email"));
                    if(documentSnapshot.get("password") != null)
                        activeStudent.setPassword((String) documentSnapshot.get("password"));
                    if(documentSnapshot.get("image") != null)
                        activeStudent.setImage((Bitmap) documentSnapshot.get("image"));
                    if(documentSnapshot.get("phoneNumber") != null)
                        activeStudent.setPhoneNumber((String) documentSnapshot.get("phoneNumber"));
                    if(documentSnapshot.get("education") != null) {
                        educationMap = (HashMap<String, Object>) documentSnapshot.get("education");
                        activeStudent.setEducation(new Education((String) educationMap.get("department"), (String) educationMap.get("degree")) );
                    }
                    /*
                    if(documentSnapshot.get("status") != null){
                        statusStream = (String) documentSnapshot.get("status");
                        if(statusStream.matches("SEARCHING"))
                            activeStudent.setStatus(Status.SEARCHING);
                        else if(statusStream.matches("INVITING"))
                            activeStudent.setStatus(Status.INVITING);
                        else if(statusStream.matches("PAIRED"))
                            activeStudent.setStatus(Status.PAIRED);
                    }
                    if(documentSnapshot.get("distance") != null)
                        activeStudent.setDistance((double) documentSnapshot.get("distance"));
                    if(documentSnapshot.get("time") != null)
                        activeStudent.setTime((long) documentSnapshot.get("time"));
                    if(documentSnapshot.get("homeStatus") != null)
                        activeStudent.setHomeStatus((boolean) documentSnapshot.get("homeStatus"));
                    if(documentSnapshot.get("pairedStudentID") != null)
                        activeStudent.setPairedStudentID((String) documentSnapshot.get("pairedStudentID"));
                }
                else
                    Toast.makeText(null, "User not found in the database", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(null, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }*/
}
