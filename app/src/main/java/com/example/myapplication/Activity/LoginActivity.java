package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    GeneralHandling generalHandling = new GeneralHandling();

    TextView txtTitleLogin, txtLogNotify;
    EditText edtUsername, edtEmail, edtPassword;
    Button btnLogin, btnCancel;

    boolean login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        login = intent.getBooleanExtra("login", false);
        // ...
//      Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        addControls();
        init();
        addEvents();
    }

    @SuppressLint("SetTextI18n")
    private void addEvents() {
        edtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!generalHandling.isUserNameValid(edtUsername.getText().toString().trim())) {
                    edtUsername.setError(getString(R.string.strRequiredUsername));
                } else {
                    // your code here
                    edtUsername.setError(null);
                }
            }
        });
        edtEmail.setOnFocusChangeListener((view, b) -> {
            // your code here
            if (!generalHandling.isEmailValid(edtEmail.getText().toString().trim())) {
                edtEmail.setError(getString(R.string.strRequiredEmail));
            } else {
                // your code here
                edtEmail.setError(null);
            }
        });
        edtPassword.setOnFocusChangeListener((view, b) -> {
            // your code here
            if (edtPassword.getText().toString().trim().length() < 6) {
                edtPassword.setError(getString(R.string.strRequiredPassword));
            } else {
                // your code here
                edtPassword.setError(null);
            }
        });
        btnLogin.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            if (email.equals("")) {
                edtEmail.requestFocus();
                return;
            } else if (password.equals("")) {
                edtPassword.requestFocus();
                return;
            }
            if (login) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            // Sign in success, update UI with the signed-in user's information
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            // If sign in fails, display a message to the user.
                            Link log_Link = new Link(getString(R.string.strHeaderSignUp))
                                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                    .setUnderlined(false)                                       // optional, defaults to true
                                    .setBold(true)                                            // optional, defaults to false
                                    .setOnClickListener(s -> {
                                        login = true;
                                        init();
                                    });
                            txtLogNotify.setText(getString(R.string.strSignInFailed) + getString(R.string.strHeaderSignUp));
                            LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                        });

            } else {
                String username = edtUsername.getText().toString();
                if (username.equals("")) {
                    edtUsername.requestFocus();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Log", "createUserWithEmail:success");
                                Link log_Link = new Link(getString(R.string.strHeaderSignIn))
                                        .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                        .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                        .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                        .setUnderlined(false)                                       // optional, defaults to true
                                        .setBold(true)                                            // optional, defaults to false
                                        .setOnClickListener(s -> {
                                            login = true;
                                            init();
                                        });
                                txtLogNotify.setText(getString(R.string.strSignUpSuccess) + " " + getString(R.string.strHeaderSignIn));
                                LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("Log", "createUserWithEmail:failure", task.getException());
                                Link log_Link = new Link(getString(R.string.strHeaderSignIn))
                                        .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                        .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                        .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                        .setUnderlined(false)                                       // optional, defaults to true
                                        .setBold(true)                                            // optional, defaults to false
                                        .setOnClickListener(s -> {
                                            login = true;
                                            init();
                                        });
                                txtLogNotify.setText(getString(R.string.strSignUpFailed) + " " + getString(R.string.strHeaderSignIn));
                                LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                            }
                        })
                        .addOnSuccessListener(authResult -> {
                            String displayName = username;
                            String phone = "";
                            String avatar = "";
                            User user = new User(Objects.requireNonNull(authResult.getUser()).getUid(), displayName, password, email, phone, avatar);
                            UserDao userDao = new UserDao();
                            userDao.save(user, userDao.getNewKey(), new TaskListener() {
                                @Override
                                public void OnSuccess() {
                                    Log.d("Info", "Register successed for user UID: " + authResult.getUser().getUid());
                                }

                                @Override
                                public void OnFail() {
                                    Log.d("Info", "Register failed for user UID: " + authResult.getUser().getUid());
                                }
                            });
                        });
            }
        });
        btnCancel.setOnClickListener(view -> finish());
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        if (login) {
            edtUsername.setVisibility(View.GONE);
            txtTitleLogin.setText(getString(R.string.strHeaderSignIn));
            btnLogin.setText(getString(R.string.strHeaderSignIn));
            Link log_Link = new Link(getString(R.string.strHeaderSignUp))
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                            // optional, defaults to false
                    .setOnClickListener(s -> {
                        login = false;
                        init();
                    });
            txtLogNotify.setText(getString(R.string.strIsSignIn) + " " + getString(R.string.strHeaderSignUp));
            LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
        } else {
            edtUsername.setVisibility(View.VISIBLE);
            txtTitleLogin.setText(getString(R.string.strHeaderSignUp));
            btnLogin.setText(getString(R.string.strHeaderSignUp));
            Link log_Link = new Link(getString(R.string.strHeaderSignIn))
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                            // optional, defaults to false
                    .setOnClickListener(s -> {
                        login = true;
                        init();
                    });
            txtLogNotify.setText(getString(R.string.strIsSignUp) + " " + getString(R.string.strHeaderSignIn));
            LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
        }
    }

    private void addControls() {
        txtTitleLogin = findViewById(R.id.txtTitleLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        txtLogNotify = findViewById(R.id.txtLogNotify);
        txtLogNotify.setText("");
    }
}