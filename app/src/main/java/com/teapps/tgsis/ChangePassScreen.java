package com.teapps.tgsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChangePassScreen extends AppCompatActivity {
    private EditText chpass_oldpassInput, chpass_reoldpassInput, chpass_newpassInput;
    private Button chpass_btnChange, chpass_btnBack;

    private String currentPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents(){
        chpass_oldpassInput = (EditText) findViewById(R.id.chpass_oldpassInput);
        chpass_reoldpassInput = (EditText) findViewById(R.id.chpass_reoldpassInput);
        chpass_newpassInput = (EditText) findViewById(R.id.chpass_newpassInput);
        chpass_btnChange = (Button) findViewById(R.id.chpass_btnChange);
        chpass_btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid());

                DialogInterface.OnClickListener passUpdateDialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                docRef.update("password", chpass_newpassInput.getText().toString());
                                currentUser.updatePassword(chpass_newpassInput.getText().toString());
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(ChangePassScreen.this, LoginScreen.class));
                                break;
                            default: break;
                        }
                    }
                };

                AlertDialog.Builder changePass = new AlertDialog.Builder(ChangePassScreen.this);

                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            currentPass = (String) documentSnapshot.get("password");
                            if(chpass_oldpassInput.getText().toString().matches("") || chpass_reoldpassInput.getText().toString().matches("") || chpass_newpassInput.getText().toString().matches(""))
                                Utils.toastLongMessage(ChangePassScreen.this, "Şifre alanları boş bırakılamaz.");
                            else if(!chpass_oldpassInput.getText().toString().matches(currentPass))
                                Utils.toastLongMessage(ChangePassScreen.this, "Hatalı Şifre");
                            else if(!chpass_oldpassInput.getText().toString().matches(chpass_reoldpassInput.getText().toString()))
                                Utils.toastLongMessage(ChangePassScreen.this, "Şifreler eşleşmiyor");
                            else if(chpass_newpassInput.getText().toString().matches(currentPass))
                                Utils.toastLongMessage(ChangePassScreen.this, "Yeni şifre, eski şifre ile aynı olamaz");
                            else if(chpass_newpassInput.getText().toString().length() < 6)
                                Utils.toastLongMessage(ChangePassScreen.this, "Şifre 6 karakterden daha kısa olamaz");
                            else{
                                changePass.setMessage("Şifrenizi değiştirmek istediğinizden emin misiniz?\nHesabınızdan çıkış yapılacak ve giriş ekranına yönlendirileceksiniz.")
                                        .setPositiveButton("Evet", passUpdateDialogListener)
                                        .setNegativeButton("Vazgeç",passUpdateDialogListener).show();
                            }
                        }
                        else
                            Utils.toastLongMessage(ChangePassScreen.this, "HATA: Kullanıcı parametreleri veritabanında bulunamadı.");
                    }
                }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.toastLongMessage(ChangePassScreen.this, "Veritabanına bağlanırken bir hata oluştu:"+e.getLocalizedMessage());
                        startActivity(new Intent(ChangePassScreen.this, ProfileScreen.class));
                    }
                });
            }
        });
        chpass_btnBack = (Button) findViewById(R.id.chpass_btnBack);
        chpass_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChangePassScreen.this, ProfileScreen.class));
            }
        });
    }
}