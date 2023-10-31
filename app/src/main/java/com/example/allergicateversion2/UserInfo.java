//package com.example.allergicateversion2;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.text.Editable;
//
//import androidx.annotation.NonNull;
//
//import java.util.ArrayList;
//
//public class UserInfo implements Parcelable {
//    private String userId;
//    private String userName;
//    private String emailId;
//    private String passwordId;
//
//    private ArrayList <String> allergyList = new ArrayList<>();
//
//    public void addAllergyList(ArrayList<String> allergyList){
//        this.allergyList = allergyList;
//    }
//
//    public ArrayList<String> getAllergyList(){
//        return this.allergyList;
//    }
//
//    public UserInfo() {
//    }
//
//    public UserInfo(String userId, Editable text, Editable editTextEmailText, Editable editTextPasswordText){
//
//    }
//    public UserInfo (String userId, String userName, String emailId, String passwordId){
//        this.userId = userId;
//        this.userName = userName;
//        this.emailId = emailId;
//        this.passwordId = passwordId;
//    }
//
//    public String getUserId(){
//        return this.userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getEmailId() {
//        return emailId;
//    }
//
//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(@NonNull Parcel parcel, int i) {
//        parcel.writeString(userName);
//        parcel.writeString(emailId);
//        parcel.writeString(passwordId);
//    }
//
//    protected UserInfo(Parcel in) {
//        this.userName = in.readString();
//        this.emailId = in.readString();
//        this.passwordId = in.readString();
//        this.allergyList = in.createStringArrayList();
//    }
//
//    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
//        @Override
//        public UserInfo createFromParcel(Parcel in) {
//            return new UserInfo(in);
//        }
//
//        @Override
//        public UserInfo[] newArray(int size) {
//            return new UserInfo[size];
//        }
//    };
//}
//

package com.example.allergicateversion2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class UserInfo implements Parcelable {
    private String userId;
    private String userName;
    private String emailId;
    private String passwordId;

    private ArrayList<String> allergyList = new ArrayList<>();

    public UserInfo() {
        // Default constructor
    }

    public UserInfo(String userId, String userName, String emailId, String passwordId) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.passwordId = passwordId;
    }

    public UserInfo(String userId, String userName, String emailId, String passwordId, ArrayList<String> allergyList) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.passwordId = passwordId;
        this.allergyList = allergyList;
    }

    public String getUserId() {
        return userId;
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

    public ArrayList<String> getAllergyList() {
        return allergyList;
    }

    public void addAllergy(String allergy) {
        allergyList.add(allergy);
    }

    // Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(emailId);
        dest.writeString(passwordId);
        dest.writeStringList(allergyList);
    }

    protected UserInfo(Parcel in) {
        userId = in.readString();
        userName = in.readString();
        emailId = in.readString();
        passwordId = in.readString();
        allergyList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
