package example.org.bjjanatest;

import android.os.Parcel;
import android.os.Parcelable;

public class Time implements Parcelable {

    private double hours;
    private int month;
    private int day;
    private int year;
    private long id;
    private int date;

    public Time() {
        id = 0;
        hours = 0.0;
        month = 1;
        day = 1;
        year = 1900;
        date = 19000101;
    }

    public void setId(long in ) {this.id = in;}
    public long getId() {return id;}
    public void setHours(double in) {this.hours = in;}
    public double getHours() {return this.hours;}
    public void setMonth(int in) {this.month = in;}
    public int getMonth() {return this.month;}
    public void setDay(int in) {this.day = in;}
    public int getDay() {return this.day;}
    public void setYear(int in) {this.year = in;}
    public int getYear() {return this.year;}
    public void setDate(int in) {this.date = in;}
    public int getDate() {return this.date;}

    protected Time(Parcel in) {
        hours = in.readDouble();
        month = in.readInt();
        day = in.readInt();
        year = in.readInt();
        id = in.readLong();
        date = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(hours);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(year);
        dest.writeLong(id);
        dest.writeInt(date);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Time> CREATOR = new Parcelable.Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };
}