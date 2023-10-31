package com.example.allergicateversion2;

import android.os.Parcel;
        import android.os.Parcelable;

public class ParcelableJsonObject implements Parcelable {
    private String key1;
    private int key2;

    // Constructor to initialize the fields
    public ParcelableJsonObject(String key1, int key2) {
        this.key1 = key1;
        this.key2 = key2;
    }

    // Parcelable creator
    public static final Parcelable.Creator<ParcelableJsonObject> CREATOR = new Parcelable.Creator<ParcelableJsonObject>() {
        @Override
        public ParcelableJsonObject createFromParcel(Parcel in) {
            return new ParcelableJsonObject(in);
        }

        @Override
        public ParcelableJsonObject[] newArray(int size) {
            return new ParcelableJsonObject[size];
        }
    };

    // Constructor to create a ParcelableJson object from a Parcel
    private ParcelableJsonObject(Parcel in) {
        key1 = in.readString();
        key2 = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key1);
        dest.writeInt(key2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters for the data
    public String getKey1() {
        return key1;
    }

    public int getKey2() {
        return key2;
    }
}

