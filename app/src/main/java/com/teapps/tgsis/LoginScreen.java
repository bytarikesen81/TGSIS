package com.teapps.tgsis;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    private EditText emailInput, passInput;
    private Button lgn_submit, lgn_sgnup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeUIComponents();
    }

    private void initializeUIComponents() {
        this.emailInput = (EditText) findViewById(R.id.lgn_emailInput);
        this.passInput = (EditText) findViewById(R.id.lgn_passInput);


        this.lgn_submit = (Button) findViewById(R.id.lgn_submit);
        this.lgn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailInput.getText().toString().matches("") || passInput.getText().toString().matches("")){
                    Utils.toastLongMessage(LoginScreen.this, "Email ve şifre alanları boş bırakılamaz.");
                }
                else{
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailInput.getText().toString(), passInput.getText().toString()).addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Utils.toastLongMessage(LoginScreen.this,"Giris Basarili");
                                Intent loginToHome = new Intent(LoginScreen.this, HomeScreen.class);
                                startActivity(loginToHome);
                            }
                            else{
                                Utils.toastLongMessage(LoginScreen.this,"Yanlis e-posta veya sifre.");
                            }
                        }
                    });
                }
            }
        });

        this.lgn_sgnup = (Button) findViewById(R.id.lgn_sgnup);
        this.lgn_sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginToSignup = new Intent(LoginScreen.this, SignupScreen.class);
                startActivity(loginToSignup);
            }
        });
    }
}