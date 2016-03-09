package com.analytics.bjj;

import android.os.Parcel;
import android.os.Parcelable;

public class Tech implements  Parcelable{
    private String techName;
    private int techType;
    private String techNote;
    private String techVidURL;
    private long id;

    public Tech() {
        techName = "armbar";
        techType = 1;
        id=0;
        techNote="Submission";
        techVidURL="http://youtube.com/";
    }

    public void setTechName(String str) {this.techName = str;}
    public String getTechName() {return techName;}
    public void setTechType(int in) {this.techType = in;}
    public int getTechType() {return techType;}
    public void setTechNote(String str) {this.techNote = str;}
    public String getTechNote() {return techNote;}
    public void setTechVidURL(String str) {this.techVidURL = str;}
    public String getTechVidURL() {return techVidURL;}
    public void setId(long in) {this.id = in;}
    public long getId() {return id;}


    // Parcelling part
    protected Tech(Parcel in) {
        id = in.readLong();
        techName = in.readString();
        techType = in.readInt();
        techVidURL = in.readString();
        techNote = in.readString();    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(techName);
        dest.writeInt(techType);
        dest.writeString(techVidURL);
        dest.writeString(techNote);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tech> CREATOR = new Parcelable.Creator<Tech>() {
        @Override
        public Tech createFromParcel(Parcel in) {
            return new Tech(in);
        }

        @Override
        public Tech[] newArray(int size) {
            return new Tech[size];
        }
    };



}
