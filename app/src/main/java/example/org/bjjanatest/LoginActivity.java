package example.org.bjjanatest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity {

    // Email, password edittext
    EditText txtUsername;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManagement session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManagement(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        //txtPassword = (EditText) findViewById(R.id.txtPassword);


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                String emailAddress = txtUsername.getText().toString();

                // Check if email is filled
                if(emailAddress.trim().length() > 0){

                    if(isEmailValid(emailAddress)){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession(emailAddress);

                        // Staring MainActivity
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