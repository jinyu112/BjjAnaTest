package com.analytics.bjj;

import android.os.Parcel;
import android.os.Parcelable;

public class Train implements Parcelable{
    private long id;
    private String trainName;
    private String belt;
    private String oppBelt;
    private int subAttempted;
    private int subSuccessful;
    private int passAttempted;
    private int passSuccessful;
    private int sweepAttempted;
    private int sweepSuccessful;
    private int tdAttempted;
    private int tdSuccessful;
    private double matchTime;

    public Train() {
        id = 0;
        trainName = "Sparring session";
        belt = "White";
        oppBelt = "White";
        subAttempted = 0;
        subSuccessful = 0;
        passAttempted = 0;
        passSuccessful = 0;
        sweepAttempted = 0;
        sweepSuccessful = 0;
        tdAttempted = 0;
        tdSuccessful = 0;
        matchTime = 0.0;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long num) {
        id = num;
    }

    public String getTrainName() {
        return this.trainName;
    }

    public void setTrainName(String str) {trainName = str;}

    public String getBelt() {return this.belt;}

    public void setBelt(String str) {belt = str;}

    public String getOppBelt() {return this.oppBelt;}

    public void setOppBelt(String str) {oppBelt = str;}

    public int getSubAttempted() {return this.subAttempted;}

    public void setSubAttempted(int num) {subAttempted = num;}

    public int getSubSuccessful() {
        return this.subSuccessful;
    }

    public void setSubSuccessful(int num) {
        subSuccessful = num;
    }

    public int getPassAttempted() {
        return this.passAttempted;
    }

    public void setPassAttempted(int num) {
        passAttempted = num;
    }

    public int getPassSuccessful() {
        return this.passSuccessful;
    }

    public void setPassSuccessful(int num) {
        passSuccessful = num;
    }

    public int getSweepAttempted() {
        return this.sweepAttempted;
    }

    public void setSweepAttempted(int num) {
        sweepAttempted = num;
    }

    public int getSweepSuccessful() {
        return this.sweepSuccessful;
    }

    public void setSweepSuccessful(int num) {
        sweepSuccessful = num;
    }

    public int getTdAttempted() {
        return this.tdAttempted;
    }

    public void setTdAttempted(int num) {
        tdAttempted = num;
    }

    public int getTdSuccessful() {
        return this.tdSuccessful;
    }

    public void setTdSuccessful(int num) {
        tdSuccessful = num;
    }

    public double getMatchTime() {return this.matchTime;}

    public void setMatchTime(double num) {matchTime = num;}


    // Parcelling part
    protected Train(Parcel in) {
        id = in.readLong();
        trainName = in.readString();
        belt = in.readString();
        oppBelt = in.readString();
        subAttempted = in.readInt();
        subSuccessful = in.readInt();
        passAttempted = in.readInt();
        passSuccessful = in.readInt();
        sweepAttempted = in.readInt();
        sweepSuccessful = in.readInt();
        tdAttempted = in.readInt();
        tdSuccessful = in.readInt();
        matchTime = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(trainName);
        dest.writeString(belt);
        dest.writeString(oppBelt);
        dest.writeInt(subAttempted);
        dest.writeInt(subSuccessful);
        dest.writeInt(passAttempted);
        dest.writeInt(passSuccessful);
        dest.writeInt(sweepAttempted);
        dest.writeInt(sweepSuccessful);
        dest.writeInt(tdAttempted);
        dest.writeInt(tdSuccessful);
        dest.writeDouble(matchTime);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Train> CREATOR = new Parcelable.Creator<Train>() {
        @Override
        public Train createFromParcel(Parcel in) {
            return new Train(in);
        }

        @Override
        public Train[] newArray(int size) {
            return new Train[size];
        }
    };


}
