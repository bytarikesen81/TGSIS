package com.teapps.tgsis;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.type.DateTime;

import java.io.IOException;

public class SignupScreen extends AppCompatActivity {
    private EditText sgup_emailInput, sgup_passInput,
    sgup_nameInput, sgup_surnameInput,
    sgup_regdateInput, sgup_graddateInput;

    private Button sgupif_upload, sgup_submit, sgup_back;

    private ImageView sgupif_image;
    private ActivityResultLauncher<Intent> mgetContent;
    private AuthOperations authop;
    private DatabaseOperations database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        initializeActivityObjects();
        initializeUIComponents();
    }

    private void initializeActivityObjects() {
       mgetContent = registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
               new ActivityResultCallback<ActivityResult>() {
                   @Override
                   public void onActivityResult(ActivityResult result) {
                       if (result.getResultCode() == Activity.RESULT_OK) {
                           sgupif_image.setVisibility(View.VISIBLE);
                           Uri resData = result.getData().getData();
                           String[] projection = {MediaStore.Images.Media.DATA};
                           Cursor cursor = getContentResolver().query(resData,
                                   projection, null, null, null);
                           if (cursor.moveToFirst()) {
                               if (Build.VERSION.SDK_INT >= 29) {
                                   // You can replace '0' by 'cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)'
                                   // Note that now, you read the column '_ID' and not the column 'DATA'
                                   resData = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getInt(0));
                                   cursor.close();

                                   // now that you have the media URI, you can decode it to a bitmap
                                   try (ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(resData, "r")) {
                                       if (pfd != null) {
                                           Bitmap bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                                           sgupif_image.setImageBitmap(bitmap);
                                           Utils.toastLongMessage(SignupScreen.this, "Fotoğraf yüklendi");
                                       }
                                       else{
                                           Utils.toastLongMessage(SignupScreen.this, "PFD null");
                                       }
                                   } catch (IOException ex) {
                                       Utils.toastLongMessage(SignupScreen.this, ex.getLocalizedMessage());
                                   }
                               } else {
                                   Utils.toastShortMessage(SignupScreen.this, "API sürümü yetersiz.");
                               }
                           }
                           else
                               Utils.toastLongMessage(SignupScreen.this, "Fotoğraf yüklenirken bir hata oluştu");
                       }
                   }
               });
        database = new DatabaseOperations();
       /*
       mgetContent = registerForActivityResult(
               new ActivityResultContracts.StartActivityForResult(),
               result -> {
                   try {
                       if (result.getResultCode() == Activity.RESULT_OK
                               && result.getData() != null) {
                           Uri selectedImage = result.getData().getData();
                           String[] filePathColumn = {MediaStore.Images.Media.DATA};

                           // Get the cursor
                           Cursor cursor = getContentResolver().query(selectedImage,
                                   filePathColumn, null, null, null);
                           // Move to first row
                           cursor.moveToFirst();

                           int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                           ImageStreamDecodeString = cursor.getString(columnIndex);
                           cursor.close();
                           sgupif_image.setImageBitmap(BitmapFactory.decodeFile(ImageStreamDecodeString));
                       } else {
                           ImageStreamDecodeString = null;

                       }
                   } catch (Exception e) {
                       ImageStreamDecodeString = null;
                       Utils.toastLongMessage(SignupScreen.this, "Fotoğraf yüklenirken bir hata oluştu.");
                   }
               });*/
    }
    private void initializeUIComponents(){
        sgup_emailInput = (EditText) findViewById(R.id.sgup_emailInput);
        sgup_passInput = (EditText) findViewById(R.id.sgup_passInput);
        sgup_nameInput = (EditText) findViewById(R.id.sgup_nameInput);
        sgup_surnameInput = (EditText) findViewById(R.id.sgup_surnameInput);
        sgup_regdateInput = (EditText) findViewById(R.id.sgup_regdateInput);
        sgup_graddateInput = (EditText) findViewById(R.id.sgup_graddateInput);

        sgupif_upload = (Button) findViewById(R.id.sgupif_upload);
        sgupif_image = (ImageView) findViewById(R.id.sgupif_image);
        sgupif_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mgetContent.launch(intent);
            }
        });

        sgup_submit = (Button) findViewById(R.id.sgup_submit);
        sgup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sgup_emailInput.getText().toString().matches("") || sgup_passInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SignupScreen.this, "Mail veya şifre alanları mutlaka doldurulmak zorundadır.");
                else if(sgup_nameInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SignupScreen.this, "İsim alanı boş bırakılamaz");
                else if(sgup_surnameInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SignupScreen.this, "Soyisim alanı boş bırakılamaz");
                else if(sgup_regdateInput.getText().toString().matches("") || sgup_graddateInput.getText().toString().matches(""))
                    Utils.toastLongMessage(SignupScreen.this, "Tarih alanları boş bırakılamaz");
                else{
                    //KULLANICI KAYDI//
                    //Kullanıcı hesabını kaydet
                    Utils.toastLongMessage(SignupScreen.this, "Kayıt oluşturuluyor..");
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(sgup_emailInput.getText().toString(), sgup_passInput.getText().toString()).addOnCompleteListener(SignupScreen.this,
                            task -> {
                                if(task.isSuccessful()){
                                    //Kullanıcı verisini kaydet, eski kullanici varsa cikisini yapip yeni kullaniciyi sisteme dahil et
                                    FirebaseAuth.getInstance().signOut();
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(sgup_emailInput.getText().toString(), sgup_passInput.getText().toString()).addOnCompleteListener(SignupScreen.this, new OnCompleteListener<AuthResult>(){
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                //Yeni kullanici basariyla sisteme girdiginde verilerini database'e kaydet
                                                Utils.toastLongMessage(SignupScreen.this,"Giris Basarili");
                                                database.saveUserInfo(new User(sgup_nameInput.getText().toString(), sgup_surnameInput.getText().toString(),
                                                        sgup_regdateInput.getText().toString(), sgup_graddateInput.getText().toString(),
                                                        sgup_emailInput.getText().toString(), sgup_passInput.getText().toString()), SignupScreen.this);
                                            }
                                            else{
                                                Utils.toastLongMessage(SignupScreen.this,task.getException().getLocalizedMessage());
                                            }
                                        }
                                    });
                                    Utils.toastLongMessage(SignupScreen.this, "Başarıyla kayıt oluşturuldu.");

                                    //Veri kaydetme işlemi bittikten sonra kullanıcıyı tekrardan sign out et ve yeniden login ekranına dön
                                    FirebaseAuth.getInstance().signOut();
                                    Intent loginAfterSigningUp = new Intent(SignupScreen.this, LoginScreen.class);
                                    startActivity(loginAfterSigningUp);
                                }
                                else
                                    Utils.toastLongMessage(SignupScreen.this, task.getException().getLocalizedMessage());
                            }
                    );
                }

            }
        });
        sgup_back = (Button) findViewById(R.id.sgup_back);
        sgup_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signupToLogin = new Intent(SignupScreen.this, LoginScreen.class);
                startActivity(signupToLogin);
            }
        });

    }

}
