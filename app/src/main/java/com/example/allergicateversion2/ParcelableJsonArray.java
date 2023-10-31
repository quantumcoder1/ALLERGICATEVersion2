package com.example.allergicateversion2;

import android.os.Parcel;
        import android.os.Parcelable;
        import org.json.JSONArray;
        import org.json.JSONException;

public class ParcelableJsonArray implements Parcelable {
    private JSONArray jsonArray;

    // Constructor to initialize the fields
    public ParcelableJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    // Parcelable creator
    public static final Parcelable.Creator<ParcelableJsonArray> CREATOR = new Parcelable.Creator<ParcelableJsonArray>() {
        @Override
        public ParcelableJsonArray createFromParcel(Parcel in) {
            return new ParcelableJsonArray(in);
        }

        @Override
        public ParcelableJsonArray[] newArray(int size) {
            return new ParcelableJsonArray[size];
        }
    };

    // Constructor to create a ParcelableJson object from a Parcel
    private ParcelableJsonArray(Parcel in) {
        try {
            jsonArray = new JSONArray(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jsonArray.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getter for the JSONArray
    public JSONArray getJsonArray() {
        return jsonArray;
    }
}

