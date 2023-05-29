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
    public boolean validateNumber(String phoneNumber)
    {
        //validates phone numbers having 10 digits (9998887776)
        if (phoneNumber.matches("\\d{10}"))
            return true;
        //validates phone numbers having digits, -, . or spaces
        else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}"))
            return true;
        else if (phoneNumber.matches("\\d{4}[-\\.\\s]\\d{3}[-\\.\\s]\\d{3}"))
            return true;
        //validates phone numbers having digits and extension (length 3 to 5)
        else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}"))
            return true;
        //validates phone numbers having digits and area code in braces
        else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}"))
            return true;
        else if (phoneNumber.matches("\\(\\d{5}\\)-\\d{3}-\\d{3}"))
            return true;
        else if (phoneNumber.matches("\\(\\d{4}\\)-\\d{3}-\\d{3}"))
            return true;
        //return false if any of the input matches is not found
        else
            return false;
    }
}
