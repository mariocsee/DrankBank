package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import drankbank.android.drankbank.data.User;

/**
 * Created by Veronica on 11/21/16.
 *
 * Handles activity from login/registration screen
 * Stores user in Firebase
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView (R.id.etPassword)
    EditText etPassword;

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }


    @OnClick(R.id.btnLogin)
    void loginClick() {
        if (!isFormValid()){
            //does nothing it form is not valid
            return;
        }
        showProgressDialog();
        //else, uses authenticator's method to allow them to sign in
        firebaseAuth.signInWithEmailAndPassword (
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //if login successful...
                if (task.isSuccessful()) {
                    //Open new Activity
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    hideProgressDialog();
                    finish();
                } else {
                    hideProgressDialog();
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("TAG_LOGIN", "Error: " + task.getException().getMessage());
                }
            }
        });
    }


    //Registers user so long as id and password aren't empty
    @OnClick(R.id.btnRegister)
    void registerClick() {
        if (!isFormValid()) {
            return;
        }
        showProgressDialog();
        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser fbUser = task.getResult().getUser();
                            fbUser.updateProfile(new UserProfileChangeRequest.Builder().
                                    setDisplayName(usernameFromEmail(fbUser.getEmail())).build());

                            User user = new User(usernameFromEmail(fbUser.getEmail()), fbUser.getEmail());
                            database.child("users").child(fbUser.getUid()).setValue(user);

                            Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_SHORT).show();
                            //Opens new Activity
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        hideProgressDialog();

    }

    /*
    Checks to see if id and password fields aren't empty, else show an error method
     */
    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            return false;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            return false;
        }
        return true;
    }

    /*
    Splits username before the "@" symbol as the username
     */
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }
}
