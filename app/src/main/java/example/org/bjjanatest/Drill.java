package example.org.bjjanatest;

import android.os.Parcel;
import android.os.Parcelable;

public class Drill implements Parcelable {
    private String drillName;
    private int drillRepTotal;
    private long id;

    public Drill() {
        id = 0;
        drillName = "default";
        drillRepTotal = 0;
    }

    public void setDrillName(String str) {
        this.drillName = str;
    }
    public String getDrillName() {
        return this.drillName;
    }
    public void setId(long in) {
        this.id = in;
    }
    public long getId() {
        return this.id;
    }
    public void setDrillRepTotal(int in) {
        this.drillRepTotal = in;
    }
    public int getDrillRepTotal() {
        return this.drillRepTotal;
    }

    protected Drill(Parcel in) {
        drillName = in.readString();
        drillRepTotal = in.readInt();
        id = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(drillName);
        dest.writeInt(drillRepTotal);
        dest.writeLong(id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Drill> CREATOR = new Parcelable.Creator<Drill>() {
        @Override
        public Drill createFromParcel(Parcel in) {
            return new Drill(in);
        }

        @Override
        public Drill[] newArray(int size) {
            return new Drill[size];
        }
    };
}