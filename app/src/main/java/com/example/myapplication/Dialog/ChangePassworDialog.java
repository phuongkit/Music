package com.example.myapplication.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChangePassworDialog extends Dialog {
    Activity context;
    EditText passOld, passNew, passRetype;
    Button btnUpdate, btnCancel;
    FirebaseAuth mAuth;
    public ChangePassworDialog(Activity context){
        super(context);
        this.context=context;
        setContentView(R.layout.dialog_changepassword);
        mapping();
        addEvents();
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 1.00);
        int paddingDp = 450;
        float density = context.getResources().getDisplayMetrics().density;
        int height = (int) (paddingDp * density);
//        int height = (int) (context.getResources().getDisplayMetrics().densityDpi);
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
        if (user == null) return;
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
            if(txtpassNew.equals(txtpassRetype)){
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),txtpassOld);
                user.reauthenticate(credential)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //Toast.makeText(context,"ngu2", Toast.LENGTH_SHORT).show();
                                user.updatePassword(txtpassNew);
                                UserDao userDao = new UserDao();
                                userDao.getAll(new RetrieValEventListener<List<User>>() {
                                    @Override
                                    public void OnDataRetrieved(List<User> users) {
                                        for (User user1 : users) {
                                            if (user1.getId().equals(user.getUid())) {
                                                user1.setPassword(txtpassNew);
                                                userDao.save(user1, user1.key, new TaskListener() {
                                                    @Override
                                                    public void OnSuccess() {
                                                    }

                                                    @Override
                                                    public void OnFail() {

                                                    }
                                                });
                                            }
                                            break;
                                        }
                                    }
                                });
                                Toast.makeText(context, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                auth.signOut();
                                dismiss();
                                MainActivity mainActivity = (MainActivity) context;
                                mainActivity.onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else {
                Toast.makeText(context, "Mật khẩu mới không trùng nhau vui lòng nhập lại", Toast.LENGTH_SHORT).show();
            }


        }
    }
}