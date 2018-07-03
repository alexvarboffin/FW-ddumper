package com.walhalla.domen.item;

import android.os.Parcel;
import android.os.Parcelable;

public class Obj implements Parcelable {

    private final String name;
    public Obj(String name) {
        this.name = name;
    }

    protected Obj(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Obj> CREATOR = new Creator<Obj>() {
        @Override
        public Obj createFromParcel(Parcel in) {
            return new Obj(in);
        }

        @Override
        public Obj[] newArray(int size) {
            return new Obj[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
