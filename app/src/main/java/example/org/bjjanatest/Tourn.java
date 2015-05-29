package example.org.bjjanatest;

public class Tourn {
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
    private int numBackTakes;
    private int numMounts;
    private double matchTime;

    public Tourn() {
        id = 0;
        tournName = "";
        belt = "white";
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
}

