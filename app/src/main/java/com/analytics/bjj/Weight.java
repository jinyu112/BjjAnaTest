package com.analytics.bjj;


import android.os.Parcel;
import android.os.Parcelable;

public class Weight implements Parcelable {
    private double mass;
    private int month;
    private int day;
    private int year;
    private long id;
    private int date;

    public Weight() {
        id = 0;
        mass = 0.0;
        month = 1;
        day = 1;
        year = 1900;
        date = 19000101;
    }

    public void setId(long in ) {this.id = in;}
    public long getId() {return id;}
    public void setMass(double in) {this.mass = in;}
    public double getMass() {return this.mass;}
    public void setMonth(int in) {this.month = in;}
    public int getMonth() {return this.month;}
    public void setDay(int in) {this.day = in;}
    public int getDay() {return this.day;}
    public void setYear(int in) {this.year = in;}
    public int getYear() {return this.year;}
    public void setDate(int in) {this.date = in;}
    public int getDate() {return this.date;}


    protected Weight(Parcel in) {
        id = in.readLong();
        mass = in.readDouble();
        month = in.readInt();
        day = in.readInt();
        year = in.readInt();
        date = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeDouble(mass);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(year);
        dest.writeInt(date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Weight> CREATOR = new Parcelable.Creator<Weight>() {
        @Override
        public Weight createFromParcel(Parcel in) {
            return new Weight(in);
        }

        @Override
        public Weight[] newArray(int size) {
            return new Weight[size];
        }
    };
}
