package com.teapps.tgsis;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class AuthOperations {
    private FirebaseAuth databaseAuth;
    private boolean result;

    public AuthOperations(){
        this.databaseAuth = FirebaseAuth.getInstance();
    }
    public boolean validateCredentials(String email, String password){
        if(validateMail(email) && password.length() >= 6) return true;
        else return false;
    }
    private boolean validateMail(String email)
    {
        String emailReg = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailReg);
        if (email == null) return false;
        return pat.matcher(email).matches();
    }
}
