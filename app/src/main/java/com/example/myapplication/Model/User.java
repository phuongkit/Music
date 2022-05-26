package com.example.myapplication.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User extends MusicObject implements Serializable {

    private String displayName;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private Integer role = 1;

    public User() {
    }

    public User(String id, String displayName, String password, String email, String phone, String avatar) {
        super(id);
        this.displayName = displayName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public void update(String displayName, String password, String email, String phone, String avatar) {
        this.displayName = displayName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "key='" + key + '\'' +
                ", id='" + super.getId() + '\'' +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role=" + role +
                "}\n";
    }
}
