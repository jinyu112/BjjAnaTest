package example.org.bjjanatest;

import android.os.Parcel;
import android.os.Parcelable;

public class Tourn implements Parcelable{
    private long id;
    private String tournName;
    private String belt;
    private int weightClass;
    private int date;
    private int pointsAllowed;
    private int pointsScored;
    private int subAttempted;
    private int subSuccessful;
    private int passAttempted;
    private int passSuccessful;
    private int sweepAttempted;
    private int sweepSuccessful;
    private int tdAttempted;
    private int tdSuccessful;
    private double matchTime;
    private int win; //1 == win, 0 == loss

    public Tourn() {
        id = 0;
        tournName = "IBJJF";
        belt = "White";
        weightClass = 0;
        date = 0;
        pointsAllowed = 0;
        pointsScored = 0;
        subAttempted = 0;
        subSuccessful = 0;
        passAttempted = 0;
        passSuccessful = 0;
        sweepAttempted = 0;
        sweepSuccessful = 0;
        tdAttempted = 0;
        tdSuccessful = 0;
        matchTime = 0.0;
        win = 0;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long num) {
        id = num;
    }

    public String getTournName() {
        return this.tournName;
    }

    public void setTournName(String str) {
        tournName = str;
    }

    public String getBelt() {
        return this.belt;
    }

    public void setBelt(String str) {
        belt = str;
    }

    public int getWeightClass() {
        return this.weightClass;
    }

    public void setWeightClass(int num) {
        weightClass = num;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int num) {
        date = num;
    }

    public int getPointsAllowed() {
        return this.pointsAllowed;
    }

    public void setPointsAllowed(int num) {
        pointsAllowed = num;
    }

    public int getPointsScored() {
        return this.pointsScored;
    }

    public void setPointsScored(int num) {
        pointsScored = num;
    }

    public int getSubAttempted() {
        return this.subAttempted;
    }

    public void setSubAttempted(int num) {
        subAttempted = num;
    }

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

    public double getMatchTime() {
        return this.matchTime;
    }

    public void setMatchTime(double num) {
        matchTime = num;
    }

    public int getWin() {
        return this.win;
    }

    public void setWin(int flag) {
        win = flag;
    }


    // Parcelling part
    protected Tourn(Parcel in) {
        id = in.readLong();
        tournName = in.readString();
        belt = in.readString();
        weightClass = in.readInt();
        date = in.readInt();
        pointsAllowed = in.readInt();
        pointsScored = in.readInt();
        subAttempted = in.readInt();
        subSuccessful = in.readInt();
        passAttempted = in.readInt();
        passSuccessful = in.readInt();
        sweepAttempted = in.readInt();
        sweepSuccessful = in.readInt();
        tdAttempted = in.readInt();
        tdSuccessful = in.readInt();
        matchTime = in.readDouble();
        win = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(tournName);
        dest.writeString(belt);
        dest.writeInt(weightClass);
        dest.writeInt(date);
        dest.writeInt(pointsAllowed);
        dest.writeInt(pointsScored);
        dest.writeInt(subAttempted);
        dest.writeInt(subSuccessful);
        dest.writeInt(passAttempted);
        dest.writeInt(passSuccessful);
        dest.writeInt(sweepAttempted);
        dest.writeInt(sweepSuccessful);
        dest.writeInt(tdAttempted);
        dest.writeInt(tdSuccessful);
        dest.writeDouble(matchTime);
        dest.writeInt(win);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tourn> CREATOR = new Parcelable.Creator<Tourn>() {
        @Override
        public Tourn createFromParcel(Parcel in) {
            return new Tourn(in);
        }

        @Override
        public Tourn[] newArray(int size) {
            return new Tourn[size];
        }
    };


}

