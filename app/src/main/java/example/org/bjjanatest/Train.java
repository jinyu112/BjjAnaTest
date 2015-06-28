package example.org.bjjanatest;

import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class Train implements Parcelable {

    private long id;
    private String trainName;
    private String belt;
    private String beltOpp;
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
    private int numBackTakes;
    private int numMounts;
    private double matchTime;

    public Train() {
        id = 0;
        trainName = "match";
        belt = "white";
        beltOpp="white";
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
        numBackTakes = 0;
        numMounts = 0;
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

    public void setTrainName(String str) {
        trainName = str;
    }

    public String getOppBelt() {
        return this.beltOpp;
    }

    public void setOppBelt(String str) {
        beltOpp = str;
    }

    public String getBelt() {
        return this.belt;
    }

    public void setBelt(String str) {
        belt = str;
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

    public int getNumBackTakes() {
        return this.numBackTakes;
    }

    public void setNumBackTakes(int num) {
        numBackTakes = num;
    }

    public int getNumMounts() {
        return this.numMounts;
    }

    public void setNumMounts(int num) {
        numMounts = num;
    }

    public double getMatchTime() {
        return this.matchTime;
    }

    public void setMatchTime(double num) {
        matchTime = num;
    }


    // Parcelling part
    protected Train(Parcel in) {
        id = in.readLong();
        belt = in.readString();
        beltOpp = in.readString();
        trainName = in.readString();
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
        numBackTakes = in.readInt();
        numMounts = in.readInt();
        matchTime = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(belt);
        dest.writeString(beltOpp);
        dest.writeString(trainName);
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
        dest.writeInt(numBackTakes);
        dest.writeInt(numMounts);
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
