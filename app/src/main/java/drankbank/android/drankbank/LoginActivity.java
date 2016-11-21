package drankbank.android.drankbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Veronica on 11/21/16.
 *
 * Handles activity from login/registration screen
 */

public class LoginActivity extends AppCompatActivity {
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
                    startActivity(new Intent(LoginActivity.this, PostsActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /*
    Registers user so long as id and password aren't empty
     */
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
