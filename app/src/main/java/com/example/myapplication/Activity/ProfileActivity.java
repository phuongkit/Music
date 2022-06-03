package com.example.myapplication.Activity;

import static com.example.myapplication.Activity.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.myapplication.Dao.Listeners.RetrieValEventListener;
import com.example.myapplication.Dao.Listeners.TaskListener;
import com.example.myapplication.Dao.UserDao;
import com.example.myapplication.Generic.GeneralHandling;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    GeneralHandling generalHandling = new GeneralHandling();
    
    Toolbar toolbar;
    ImageView imageView;
    EditText tvName, tvEmail;
    Button btnUpdateProfile;
    Uri uri;
    User mCurrentUserDb;
    FirebaseUser mCurrentFirebaseUser;

    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) {
                            return;
                        }
                        uri = intent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                            //MainActivity.imgAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        setUserInformation();
        initListener();
    }

    private void initListener() {
        toolbar.setNavigationOnClickListener(v -> finish());

        imageView.setOnClickListener(view -> onClickRequestPermission());
        btnUpdateProfile.setOnClickListener(view -> {
            onClickUpdateProfile();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });
        
        tvName.setOnFocusChangeListener((view, b) -> {
            // your code here
            if (!generalHandling.isUserNameValid(tvName.getText().toString().trim())) {
                tvName.setError(getString(R.string.strRequiredUsername));
            } else {
                // your code here
                tvName.setError(null);
            }
        });
        
        tvEmail.setOnFocusChangeListener((view, b) -> {
            // your code here
            if (!generalHandling.isEmailValid(tvEmail.getText().toString().trim())) {
                tvEmail.setError(getString(R.string.strRequiredEmail));
            } else {
                // your code here
                tvEmail.setError(null);
            }
        });
    }

    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null || mCurrentFirebaseUser != user) {
            Toast.makeText(this.getApplication(), getString(R.string.strNotifyActionError), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        }

        String fullName = tvName.getText().toString().trim();

        try {

            assert user != null;
            String fileName = "Avatar_" + user.getDisplayName();

            StorageReference storageRef = FirebaseStorage.getInstance().getReference("Images/" + fileName);
            UploadTask uploadTask = storageRef.putFile(uri);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return storageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    mCurrentUserDb.setDisplayName(fullName);
                    mCurrentUserDb.setAvatar(downloadUri.toString());
                    UserDao userDao = new UserDao();
                    userDao.save(mCurrentUserDb, mCurrentUserDb.key, new TaskListener() {
                        @Override
                        public void OnSuccess() {
                            Toast.makeText(ProfileActivity.this, "Profile update successful", Toast.LENGTH_SHORT).show();
                            Log.i("Account", "Profile update successful!");
                        }

                        @Override
                        public void OnFail() {
                            Toast.makeText(ProfileActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                            Log.i("Account", "Profile update failed!");
                        }
                    });
                }  // Handle failures
                // ...

            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            mCurrentUserDb.setDisplayName(fullName);
            UserDao userDao = new UserDao();
            userDao.save(mCurrentUserDb, mCurrentUserDb.key, new TaskListener() {
                @Override
                public void OnSuccess() {
                    Toast.makeText(ProfileActivity.this, "Profile update successful", Toast.LENGTH_SHORT).show();
                    Log.i("Account", "Profile update successful!");
                }

                @Override
                public void OnFail() {
                    Toast.makeText(ProfileActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                    Log.i("Account", "Profile update failed!");
                }
            });
        }
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permissions, MY_REQUEST_CODE);
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void init() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Toobar đã như ActionBar
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.strHeaderProfile);

        imageView = findViewById(R.id.imageView);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
    }

    public void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this.getApplication(), getString(R.string.strNotifyActionError), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        }

        mCurrentFirebaseUser = user;

        UserDao userDao = new UserDao();
        userDao.getOneById(user.getUid(), new RetrieValEventListener<User>() {
            @Override
            public void OnDataRetrieved(User user) {
                mCurrentUserDb = user;
                tvName.setText(mCurrentUserDb.getDisplayName());
                tvEmail.setText(mCurrentUserDb.getEmail());
                Glide.with(ProfileActivity.this).load(mCurrentUserDb.getAvatar()).error(R.drawable.user).into(imageView);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }
}