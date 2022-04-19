package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    TextView txtTitleLogin, txtLogNotify;
    EditText edtEmail, edtPassword;
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

    private void addEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (login) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Log", "signInWithEmail:success");
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("Log", "signInWithEmail:failure", task.getException());
                                        Link log_Link = new Link("đăng ký")
                                                .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                                .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                                .setUnderlined(false)                                       // optional, defaults to true
                                                .setBold(true)                                            // optional, defaults to false
                                                .setOnClickListener(new Link.OnClickListener() {
                                                    @Override
                                                    public void onClick(@NotNull String s) {
                                                        login = true;
                                                        init();
                                                    }
                                                });
                                        txtLogNotify.setText("Đăng nhập tài khoản thất bại!Vui lòng thử lại hoặc đăng ký");
                                        LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                                    }
                                }
                            });
                    finish();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Log", "createUserWithEmail:success");
                                        Link log_Link = new Link("Đăng nhập")
                                                .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                                .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                                .setUnderlined(false)                                       // optional, defaults to true
                                                .setBold(true)                                            // optional, defaults to false
                                                .setOnClickListener(new Link.OnClickListener() {
                                                    @Override
                                                    public void onClick(@NotNull String s) {
                                                        login = true;
                                                        init();
                                                    }
                                                });
                                        txtLogNotify.setText("Đăng ký tài khoản thành công!Đăng nhập");
                                        LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.d("Log", "createUserWithEmail:failure", task.getException());
//                                        Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                                Toast.LENGTH_SHORT).show();
                                        Link log_Link = new Link("đăng nhập")
                                                .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                                                .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                                                .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                                                .setUnderlined(false)                                       // optional, defaults to true
                                                .setBold(true)                                            // optional, defaults to false
                                                .setOnClickListener(new Link.OnClickListener() {
                                                    @Override
                                                    public void onClick(@NotNull String s) {
                                                        login = true;
                                                        init();
                                                    }
                                                });
                                        txtLogNotify.setText("Đăng ký tài khoản thất bại!Vui lòng thử lại hoặc đăng nhập");
                                        LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
                                    }
                                }
                            });
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init() {
        if (login) {
            txtTitleLogin.setText("Login");
            btnLogin.setText("Login");
            Link log_Link = new Link("Đăng ký")
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                            // optional, defaults to false
                    .setOnClickListener(new Link.OnClickListener() {
                        @Override
                        public void onClick(@NotNull String s) {
                            login = false;
                            init();
                        }
                    });
            txtLogNotify.setText("Bạn chưa có tài khoản?Đăng ký");
            LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
        } else {
            txtTitleLogin.setText("Sign up");
            btnLogin.setText("Sign up");
            Link log_Link = new Link("Đăng nhập")
                    .setTextColor(Color.parseColor("#FF0080"))                  // optional, defaults to holo blue
                    .setTextColorOfHighlightedLink(Color.parseColor("#0D3D0C")) // optional, defaults to holo blue
                    .setHighlightAlpha(.4f)                                     // optional, defaults to .15f
                    .setUnderlined(false)                                       // optional, defaults to true
                    .setBold(true)                                            // optional, defaults to false
                    .setOnClickListener(new Link.OnClickListener() {
                        @Override
                        public void onClick(@NotNull String s) {
                            login = true;
                            init();
                        }
                    });
            txtLogNotify.setText("Bạn đã có tài khoản?Đăng nhập");
            LinkBuilder.on(txtLogNotify).addLink(log_Link).build();
        }
    }

    private void addControls() {
        txtTitleLogin = findViewById(R.id.txtTitleLogin);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        txtLogNotify = findViewById(R.id.txtLogNotify);
        txtLogNotify.setText("");
    }
}