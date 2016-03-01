package example.org.bjjanatest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class LoginActivity extends Activity {

    // Email edittext
    static EditText txtEmail;

    // login button
    static Button btnLogin;

    // Alert Dialog Manager
    static AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    static SessionManagement session;

    //PostEmail objec
    static PostEmail postEmailObj;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManagement(getApplicationContext());

        // Email input text
        txtEmail = (EditText) findViewById(R.id.txtUsername);

        //initialize postemailobj
        postEmailObj = new PostEmail("",session);

        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String emailAddress = txtEmail.getText().toString();


                // Check if email is filled
                if(emailAddress.trim().length() > 0){

                    if(isEmailValid(emailAddress)){

                        // Creating user login session
                        // For testing i am stroing email, email as follow
                        // Use user real data
                        session.createLoginSession(emailAddress);
                        postEmailObj.setEmailAddress(emailAddress);
                        postEmailObj.execute();
                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // not valid email
                        alert.showAlertDialog(LoginActivity.this, "Sign up failed.", "Please enter a valid email address.", false);
                    }
                }else{
                    // empty fields
                    alert.showAlertDialog(LoginActivity.this, "Sign up failed.", "Please enter a valid email address.", false);
                }

            }
        });
    }


    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onBackPressed() {

    }

}