package com.teapps.tgsis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InviteScreen extends AppCompatActivity {
    //NEW INVITATION SCREEN UI ATTRIBUTES
    private EditText newinv_titleInput, newinv_descInput, newinv_addrInput, newinv_timeInput, newinv_distInput;
    private Button newinv_btnPost, newinv_btnBack;

    //CURRENT INVITATION SCREEN UI ATTRIBUTES
    private TextView curinv_titleInfo, curinv_descInfo, curinv_addrInfo, curinv_timeInfo, curinv_distInfo;
    private Button curinv_btnCancel, curinv_btnBack;

    //ACTIVITY GENERAL OBJECTS
    private long time;
    private long distance;
    private String statusStream;

    private FirebaseUser currentUser;
    private DocumentReference docRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeActivityObjects();
        initializeUI();
    }

    private void initializeActivityObjects(){
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        docRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());
    }

    private void initializeUI(){
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    statusStream = (String)documentSnapshot.get("status");

                    //VIEW CURRENT INVITATIONS
                    if(statusStream.matches("INVITING")){
                        setContentView(R.layout.activity_currentinvite_screen);
                        initializeCurInvitationUIComponents();
                    }
                    //CREATE NEW INVITATION
                    else{
                        setContentView(R.layout.activity_newinvite_screen);
                        initializeNewInvitationUIComponents();
                    }
                }
                else{
                    Utils.toastLongMessage(InviteScreen.this, "Kullanıcı durum bilgisi veritabanında bulunamadı");
                    startActivity(new Intent(InviteScreen.this, HomeScreen.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener(){
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.toastLongMessage(InviteScreen.this, "Kullanıcı ilan bilgisi yüklenirken bir hata oluştu:"+e.getLocalizedMessage());
                startActivity(new Intent(InviteScreen.this, HomeScreen.class));
            }
        });
    }

    private void initializeNewInvitationUIComponents(){
        newinv_titleInput = (EditText) findViewById(R.id.newinv_titleInput);
        newinv_descInput = (EditText) findViewById(R.id.newinv_descInput);
        newinv_addrInput = (EditText) findViewById(R.id.newinv_addrInput);
        newinv_timeInput = (EditText) findViewById(R.id.newinv_timeInput);
        newinv_distInput = (EditText) findViewById(R.id.newinv_distInput);

        newinv_btnPost = (Button) findViewById(R.id.newinv_btnPost);
        newinv_btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener invCreationDialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        DocumentReference docRef = database.collection("users").document(currentUser.getUid());
                        HashMap<String, Object> postMap;
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                postMap = new HashMap<String,Object>();
                                postMap.put("title", newinv_titleInput.getText().toString());
                                postMap.put("description", newinv_descInput.getText().toString());
                                postMap.put("address", newinv_addrInput.getText().toString());
                                docRef.update("post", postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Utils.toastLongMessage(InviteScreen.this, "İlanınız başarıyla oluşturuldu.\nİlanınızı yine 'İlan Ver' menüsünden görüntüleyebilir, güncelleyebilir veya kaldırabilirsiniz.");
                                        docRef.update("distance", distance);
                                        docRef.update("time", time);
                                        docRef.update("status", "INVITING");
                                        docRef.update("homeStatus", true);
                                        startActivity(new Intent(InviteScreen.this, HomeScreen.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Utils.toastLongMessage(InviteScreen.this, "İlan verilirken bir hata oluştu:"+e.getLocalizedMessage());
                                    }
                                });
                                break;
                            default:break;
                        }
                    }
                };
                AlertDialog.Builder postbuilder = new AlertDialog.Builder(InviteScreen.this);

                try{
                    if(newinv_titleInput.getText().toString().matches("") || newinv_descInput.getText().toString().matches("") || newinv_addrInput.getText().toString().matches(""))
                        Utils.toastLongMessage(InviteScreen.this, "İlan alanları boş olamaz.");
                    else if(newinv_timeInput.getText().toString().matches(""))
                        Utils.toastLongMessage(InviteScreen.this, "Zaman alanı boş olamaz.");
                    else if(newinv_distInput.getText().toString().matches(""))
                        Utils.toastLongMessage(InviteScreen.this, "Mesafe alanı boş olamaz.");
                    else{
                        time = Long.parseLong(newinv_timeInput.getText().toString());
                        distance = Long.parseLong(newinv_distInput.getText().toString());

                        if(time <= 0  || time > 365)
                            Utils.toastLongMessage(InviteScreen.this, "Belirlenen paylaşım süresi izin verilen aralığın dışında.\nPaylaşım süresi en az 3, en fazla 365 gün olabilir.");
                        else if(distance <= 0 || distance > 100000)
                            Utils.toastLongMessage(InviteScreen.this, "Evinizin kampüse uzaklığı izin verilen menzilin dışında.\nİlan vermek için evinizin kampüsten en fazla 100 km uzaklıkta olması ve negatif/0 değeri girilmemesi gerekmektedir.");
                        else{
                            postbuilder.setMessage("Verdiğiniz bilgiler üzerinden ev ilanı vermek istediğinizden emin misiniz?\nİlanınızı iptal edene kadar sadece eşleşme tekliflerini alacak, kimseye eşleşme teklif edemeyeceksiniz.")
                                    .setPositiveButton("İlan Ver", invCreationDialogListener)
                                    .setNegativeButton("Vazgeç", invCreationDialogListener).show();
                        }
                    }
                }catch (NumberFormatException e){
                    Utils.toastLongMessage(InviteScreen.this, "Zaman ve mesafe alanlarına uygun olmayan formatta veri girildi.");
                }
            }
        });

        newinv_btnBack = (Button) findViewById(R.id.newinv_btnBack);
        newinv_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InviteScreen.this, HomeScreen.class));
            }
        });
    }
    private void initializeCurInvitationUIComponents(){
        curinv_titleInfo = (TextView) findViewById(R.id.curinv_titleInfo);
        curinv_descInfo = (TextView) findViewById(R.id.curinv_descInfo);
        curinv_addrInfo = (TextView) findViewById(R.id.curinv_addrInfo);
        curinv_distInfo = (TextView) findViewById(R.id.curinv_distInfo);
        curinv_timeInfo = (TextView) findViewById(R.id.curinv_timeInfo);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap<String, Object> post;
                if(documentSnapshot.exists()){
                    post = (HashMap<String, Object>) documentSnapshot.get("post");
                    curinv_titleInfo.setText("İlan Başlığı:" + (String)post.get("title"));
                    curinv_descInfo.setText("İlan Açıklaması:"+ (String)post.get("description"));
                    curinv_addrInfo.setText("İlan Adresi:" + (String)post.get("address"));
                    curinv_timeInfo.setText("İlan Süresi(Gün):"+  String.valueOf(documentSnapshot.get("time")));
                    curinv_distInfo.setText("Kampüse Uzaklık(m):"+ String.valueOf(documentSnapshot.get("distance")));
                }
                else{
                    Utils.toastLongMessage(InviteScreen.this, "Ilan verileri bulunamadı.");
                    startActivity(new Intent(InviteScreen.this, HomeScreen.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.toastLongMessage(InviteScreen.this, "Ilan verileri veritabanından çekilirken bir hata oluştu:" + e.getLocalizedMessage());
            }
        });


        curinv_btnCancel = (Button) findViewById(R.id.curinv_btnCancel);
        curinv_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener invCancelDialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        HashMap<String, Object> post;
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                HashMap<String, Object> postMap = new HashMap<String, Object>();
                                postMap.put("title", null);
                                postMap.put("description", null);
                                postMap.put("address", null);
                                docRef.update("post", postMap);
                                docRef.update("time", 0);
                                docRef.update("distance", 0);
                                docRef.update("homeStatus", false);
                                docRef.update("status", "SEARCHING");
                                startActivity(new Intent(InviteScreen.this, HomeScreen.class));
                                break;
                            default:break;
                        }
                    }
                };
                AlertDialog.Builder postCanceller = new AlertDialog.Builder(InviteScreen.this);
                postCanceller.setMessage("Mevcut ilanınızı iptal etmek istediğinizden emin misiniz?\nİlanınızı iptal ettiğinizde yeni ilanlara eşleşme talebinde bulunabileceksiniz.")
                        .setPositiveButton("İptal Et", invCancelDialogListener)
                        .setNegativeButton("Vazgeç", invCancelDialogListener).show();
            }
        });
        curinv_btnBack = (Button) findViewById(R.id.curinv_btnBack);
        curinv_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InviteScreen.this, HomeScreen.class));
            }
        });
    }
}
