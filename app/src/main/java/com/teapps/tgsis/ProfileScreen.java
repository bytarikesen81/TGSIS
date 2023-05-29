package com.teapps.tgsis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Objects;

public class ProfileScreen extends AppCompatActivity {
    /*
    DEFAULTCRED
    tarik.esen@std.yildiz.edu.tr
    123456
     */
    private EditText pfsc_editName, pfsc_editSurname, pfsc_editEmail, pfsc_editPhone;
    private Spinner pfsc_spinnerdep, pfsc_spinnerdeg;
    private Button pfsc_btnUpdateStdInfo, pfsc_btnUpdatePwd, pfsc_btnUpdateImage,pfsc_btnUpdateEduInfo, pfsc_btnBack;

    private DatabaseOperations database;
    private AuthOperations authops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        initializeActivityObjects();
        initializeUIComponents();
    }

    private void initializeActivityObjects(){
        database = new DatabaseOperations();
        authops = new AuthOperations();
    }

    private void initializeUIComponents(){
        /*-----------------OGRENCI BILGILERI-----------------*/
        pfsc_editName = (EditText) findViewById(R.id.pfsc_editName);
        pfsc_editSurname = (EditText) findViewById(R.id.pfsc_editSurname);
        pfsc_editEmail = (EditText) findViewById(R.id.pfsc_editEmail);
        pfsc_editPhone = (EditText) findViewById(R.id.pfsc_editPhone);
        pfsc_btnUpdateStdInfo = (Button) findViewById(R.id.pfsc_btnUpdateStdInfo);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users")
                .document(currentUser.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    if (documentSnapshot.get("name") != null)
                        pfsc_editName.setText((String) documentSnapshot.get("name"));
                    if (documentSnapshot.get("surname") != null)
                        pfsc_editSurname.setText((String) documentSnapshot.get("surname"));
                    if (documentSnapshot.get("email") != null)
                        pfsc_editEmail.setText((String) documentSnapshot.get("email"));
                    if (documentSnapshot.get("phoneNumber") != null)
                        pfsc_editPhone.setText((String) documentSnapshot.get("phoneNumber"));
                }
                else
                   Utils.toastLongMessage(ProfileScreen.this, "User not found in the database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.toastLongMessage(ProfileScreen.this, "ERROR: " + e.getLocalizedMessage());
            }
        });

        pfsc_btnUpdateStdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                DocumentReference docRef = database.getDatabaseInstance().collection("users").document(currentUser.getUid());
                DialogInterface.OnClickListener profileUpdateDialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //UPDATE NAME
                                docRef.update("name", (String) pfsc_editName.getText().toString());
                                //UPDATE SURNAME
                                docRef.update("surname", (String) pfsc_editSurname.getText().toString());
                                //UPDATE MAIL
                                docRef.update("email", (String) pfsc_editEmail.getText().toString());
                                //UPDATE PHONE
                                if(!pfsc_editPhone.getText().toString().matches(""))
                                    docRef.update("phoneNumber", pfsc_editPhone.getText().toString());
                                else
                                    docRef.update("phoneNumber", null);
                                //LOGOUT AFTER MAKING CHANGES FOR SECURITY
                                currentUser.updateEmail(pfsc_editEmail.getText().toString());
                                FirebaseAuth.getInstance().signOut();
                                Utils.toastLongMessage(ProfileScreen.this, "Ogrenci temel bilgileri guncellendi.");
                                Utils.toastLongMessage(ProfileScreen.this, "Guvenlik icin hesaplardan cikis yapildi.");
                                startActivity(new Intent(ProfileScreen.this, LoginScreen.class));
                                break;
                            default: break;
                        }
                    }
                };
                AlertDialog.Builder profilebuilder = new AlertDialog.Builder(ProfileScreen.this);

                if(pfsc_editName.getText().toString().matches(""))
                    Utils.toastShortMessage(ProfileScreen.this, "Isim alani bos birakilamaz");
                else if(pfsc_editSurname.getText().toString().matches(""))
                    Utils.toastShortMessage(ProfileScreen.this, "Soyisim alani bos birakilamaz");
                else if(pfsc_editEmail.getText().toString().matches(""))
                    Utils.toastShortMessage(ProfileScreen.this, "E-Mail alani bos birakilamaz");
                else if(!pfsc_editEmail.getText().toString().endsWith("@std.yildiz.edu.tr"))
                    Utils.toastLongMessage(ProfileScreen.this, "Hatalı Email Formatı. Format @std.yildiz.edu.tr olmalıdır.");
                else if(!pfsc_editPhone.getText().toString().matches("") && !authops.validateNumber(pfsc_editPhone.getText().toString()))
                    Utils.toastLongMessage(ProfileScreen.this, "Hatalı Telefon Numarası Formatı.Telefon numarası başa 0 konulmayacak şekilde 10 haneli yazılmalıdır.");
                else{
                    profilebuilder.setMessage("Öğrenci bilgilerini güncellemek istediğinizden emin misiniz?\nGüvenlik protokolü gereğince hesabınızdan çıkış yapılacak.")
                            .setPositiveButton("Bilgileri Güncelle", profileUpdateDialogListener)
                            .setNegativeButton("İptal", profileUpdateDialogListener).show();
                }
            }
        });
        /*---------------------------------------------------*/

        /*-----------------SIFRE DEGISTIRME-----------------*/
        pfsc_btnUpdatePwd = (Button) findViewById(R.id.pfsc_btnUpdatePwd);
        pfsc_btnUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileScreen.this, ChangePassScreen.class));
            }
        });
        /*-------------------------------------------------*/

        /*----------------RESIM YUKLEME-------------------*/
        pfsc_btnUpdateImage = (Button) findViewById(R.id.pfsc_btnUpdateImage);
        /*------------------------------------------------*/

        /*----------EGITIM BILGILERINI GUNCELLE----------*/
        pfsc_spinnerdeg = (Spinner) findViewById(R.id.pfsc_spinnerdeg);
        pfsc_spinnerdep = (Spinner) findViewById(R.id.pfsc_spinnerdep);
        pfsc_btnUpdateEduInfo = (Button) findViewById(R.id.pfsc_btnUpdateEduInfo);
        pfsc_btnUpdateEduInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = database.getDatabaseInstance().collection("users").document(currentUser.getUid());
                DialogInterface.OnClickListener eduUpdateDialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String, Object> educationMap = new HashMap<String, Object>();
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                educationMap.put("degree", (Object) pfsc_spinnerdeg.getSelectedItem().toString());
                                educationMap.put("department", (Object) pfsc_spinnerdep.getSelectedItem().toString());
                                docRef.update("education", educationMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Utils.toastLongMessage(ProfileScreen.this, "Eğitim bilgileri başarıyla güncellendi.");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Utils.toastLongMessage(ProfileScreen.this, "HATA:"+e.getLocalizedMessage());
                                    }
                                });
                                break;
                            default: break;
                        }
                    }
                };
                AlertDialog.Builder eduBuilder = new AlertDialog.Builder(ProfileScreen.this);

                if(pfsc_spinnerdeg.getSelectedItem().toString().equals("") || pfsc_spinnerdep.getSelectedItem().equals(""))
                    Utils.toastLongMessage(ProfileScreen.this, "Eğitim bilgileri boş olamaz.");
                else{
                    eduBuilder.setMessage("Eğitim bilgilerinizi güncellemek istediğinizden emin misiniz?")
                        .setPositiveButton("Evet", eduUpdateDialogListener)
                        .setNegativeButton("Vazgeç",eduUpdateDialogListener).show();
                }
            }
        });
        /*----------------------------------------------*/

        /*----------------GERI DON-------------------*/
        pfsc_btnBack = (Button) findViewById(R.id.pfsc_btnBack);
        pfsc_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileScreen.this, HomeScreen.class));
            }
        });
        /*-------------------------------------------*/
    }

}