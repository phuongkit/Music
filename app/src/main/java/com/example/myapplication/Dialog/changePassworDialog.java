package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePassworDialog extends Dialog {
    Activity context;
    EditText passOld, passNew, passRetype;
    Button btnUpdate, btnCancel;
    FirebaseAuth mAuth;
    public changePassworDialog(Activity context){
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_changepassword);
        mapping();
        addEvents();
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.00);
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.5);
        getWindow().setLayout(width, height);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
    }

    public void mapping(){
        passOld = findViewById(R.id.passOld);
        passNew = findViewById(R.id.passNew);
        passRetype = findViewById(R.id.passReType);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);
    }

    private void addEvents(){
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }
    private void updatePassword(){
        String txtpassOld = passOld.getText().toString();
        String txtpassNew = passNew.getText().toString();
        String txtpassRetype = passRetype.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
        }
        else {
            if(txtpassOld.equals(""))
            {
                Toast.makeText(context, "Không bỏ trống", Toast.LENGTH_SHORT).show();
            }
            else if(txtpassNew.equals("")){
                Toast.makeText(context, "Không bỏ trống", Toast.LENGTH_SHORT).show();
            }
            else if(txtpassRetype.equals("")){
                Toast.makeText(context, "Không bỏ trống", Toast.LENGTH_SHORT).show();
            }
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),txtpassOld);
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //Toast.makeText(context,"ngu2", Toast.LENGTH_SHORT).show();
                            user.updatePassword(txtpassNew);
                            Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,"ngu", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}
