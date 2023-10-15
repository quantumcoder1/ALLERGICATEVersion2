package com.example.allergicateversion2;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class UserInfo implements Parcelable {
    private String userId;
    private String userName;
    private String emailId;
    private String passwordId;

    private ArrayList <String> allergyList = new ArrayList<>();

    public void addAllergyList(ArrayList<String> allergyList){
        this.allergyList = allergyList;
    }

    public ArrayList<String> getAllergyList(){
        return this.allergyList;
    }

    public UserInfo() {
    }

    public UserInfo(String userId, Editable text, Editable editTextEmailText, Editable editTextPasswordText){

    }
    public UserInfo (String userId, String userName, String emailId, String passwordId){
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.passwordId = passwordId;
    }

    public String getUserId(){
        return this.userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(emailId);
        parcel.writeString(passwordId);
    }

    protected UserInfo(Parcel in) {
        this.userName = in.readString();
        this.emailId = in.readString();
        this.passwordId = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}

