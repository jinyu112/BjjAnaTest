package example.org.bjjanatest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.org.bjjanatest.Train;

public class TrainDataSource {

    SQLiteOpenHelper dbhelper; //should this be private?? compared with tourndatasource
    SQLiteDatabase database; //should this be private?? compared with tourndatasource
    private static int trainLen = 0;
    private static double tdSucPerc=0.0;
    private static double passSucPerc=0.0;
    private static double sweepSucPerc=0.0;
    private static double subSucPerc=0.0;
    private static double avgMatchTime=0.0;
    private static int totalPts = 0;
    private static int totalPtsAllowed = 0;
    private static int totalPassAtt = 0;
    private static int totalPassSuc = 0;
    private static int totalSweepAtt = 0;
    private static int totalSweepSuc = 0;
    private static int totalTdAtt = 0;
    private static int totalTdSuc = 0;
    private static int totalSubAtt = 0;
    private static int totalSubSuc = 0;
    private static double totalMatchTime = 0.0;
    private static double avgTdScored = 0.0;
    private static double avgPassesScored = 0.0;
    private static double avgSweepsScored = 0.0;
    private static double avgSubsScored = 0.0;
    private static double avgTdAtt = 0.0;
    private static double avgPassesAtt = 0.0;
    private static double avgSweepsAtt = 0.0;
    private static double avgSubsAtt = 0.0;

    private static final String LOGTAG = "BJJTRAINING";
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID_TRAIN,
            trainingDBOpenHelper.COLUMN_TRAIN_NAME,
            trainingDBOpenHelper.COLUMN_BELT_TRAIN,
            trainingDBOpenHelper.COLUMN_BELT_TRAIN_OPP,
            trainingDBOpenHelper.COLUMN_PTS_ALLOWED_TRAIN,
            trainingDBOpenHelper.COLUMN_PTS_SCORED_TRAIN,
            trainingDBOpenHelper.COLUMN_SUB_ATTEMPT_TRAIN,
            trainingDBOpenHelper.COLUMN_SUB_SUCCESS_TRAIN,
            trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED_TRAIN,
            trainingDBOpenHelper.COLUMN_PASS_SUCCESS_TRAIN,
            trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED_TRAIN,
            trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS_TRAIN,
            trainingDBOpenHelper.COLUMN_TD_ATTEMPTED_TRAIN,
            trainingDBOpenHelper.COLUMN_TD_SUCCESS_TRAIN,
            trainingDBOpenHelper.COLUMN_MATCH_TIME_TRAIN};

    public TrainDataSource (Context context) {
        dbhelper = new trainingDBOpenHelper(context);
        database = dbhelper.getWritableDatabase(); //calls oncreate method, creates table structure and allows for database connection
    }

    public void open() {
        Log.i(LOGTAG, "Database opened.");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(LOGTAG,"Database closed.");
        dbhelper.close();
    }

    public Train create(Train train) {
        ContentValues values = new ContentValues();
        //values.put(trainingDBOpenHelper.COLUMN_ID,train.getId()); // if the id is set to autoincrement from the db generation string, remove this line
        values.put(trainingDBOpenHelper.COLUMN_TRAIN_NAME,train.getTrainName());
        values.put(trainingDBOpenHelper.COLUMN_BELT_TRAIN,train.getBelt());
        values.put(trainingDBOpenHelper.COLUMN_BELT_TRAIN_OPP,train.getOppBelt());
        values.put(trainingDBOpenHelper.COLUMN_PTS_ALLOWED_TRAIN, train.getPointsAllowed());
        values.put(trainingDBOpenHelper.COLUMN_PTS_SCORED_TRAIN,train.getPointsScored());
        values.put(trainingDBOpenHelper.COLUMN_SUB_ATTEMPT_TRAIN,train.getSubAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SUB_SUCCESS_TRAIN,train.getSubSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED_TRAIN,train.getPassAttempted());
        values.put(trainingDBOpenHelper.COLUMN_PASS_SUCCESS_TRAIN,train.getPassSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED_TRAIN,train.getSweepAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS_TRAIN,train.getSweepSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_TD_ATTEMPTED_TRAIN,train.getTdAttempted());
        values.put(trainingDBOpenHelper.COLUMN_TD_SUCCESS_TRAIN,train.getTdSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_MATCH_TIME_TRAIN,train.getMatchTime());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_TRAIN,null,values);
        Log.i(LOGTAG,"train ID: " + insertId);
        train.setId(insertId);
        return train;
    }

    public List<Train> findAll() {
        List<Train> trains = new ArrayList<Train>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_TRAIN,allColumns,null,null,null,null,null);
        Log.i(LOGTAG,"returned " + cursor.getCount() + " rows.");
        trainLen = cursor.getCount();
        if (trainLen>0 && cursor != null) {
            totalPts = 0;
            totalPtsAllowed = 0;
            totalPassAtt = 0;
            totalPassSuc = 0;
            totalSweepAtt = 0;
            totalSweepSuc = 0;
            totalTdAtt = 0;
            totalTdSuc = 0;
            totalSubAtt = 0;
            totalSubSuc = 0;
            totalMatchTime = 0;
            tdSucPerc = 0.0;
            passSucPerc = 0.0;
            sweepSucPerc = 0.0;
            subSucPerc = 0.0;
            avgMatchTime = 0.0;
            while (cursor.moveToNext()) {
                Train train = new Train();
                Log.i(LOGTAG, "findall: trainid " + cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_TRAIN)));
                train.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_TRAIN)));
                train.setTrainName(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TRAIN_NAME)));
                train.setMatchTime(cursor.getDouble(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_MATCH_TIME_TRAIN)));
                train.setBelt(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_BELT_TRAIN)));
                train.setOppBelt(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_BELT_TRAIN_OPP)));
                train.setPointsAllowed(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PTS_ALLOWED_TRAIN)));
                train.setPointsScored(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PTS_SCORED_TRAIN)));
                train.setSubAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SUB_ATTEMPT_TRAIN)));
                train.setSubSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SUB_SUCCESS_TRAIN)));
                train.setPassAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED_TRAIN)));
                train.setPassSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PASS_SUCCESS_TRAIN)));
                train.setSweepAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED_TRAIN)));
                train.setSweepSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS_TRAIN)));
                train.setTdAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TD_ATTEMPTED_TRAIN)));
                train.setTdSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TD_SUCCESS_TRAIN)));

                totalPts        = totalPts + train.getPointsScored();
                totalPtsAllowed = totalPtsAllowed + train.getPointsAllowed();
                totalPassAtt    = totalPassAtt + train.getPassAttempted();
                totalPassSuc    = totalPassSuc + train.getPassSuccessful();
                totalSweepAtt   = totalSweepAtt + train.getSweepAttempted();
                totalSweepSuc   = totalSweepSuc + train.getSweepSuccessful();
                totalTdAtt      = totalTdAtt + train.getTdAttempted();
                totalTdSuc      = totalTdSuc + train.getTdSuccessful();
                totalSubAtt     = totalSubAtt + train.getSubAttempted();
                totalSubSuc     = totalSubSuc + train.getSubSuccessful();
                totalMatchTime  = totalMatchTime + train.getMatchTime();

                trains.add(train);
            }
            if (totalTdAtt !=0) {
                tdSucPerc = (double) totalTdSuc/totalTdAtt;
            }
            if (totalPassAtt !=0) {
                passSucPerc = (double) totalPassSuc/totalPassAtt;
            }
            if (totalSweepAtt !=0) {
                sweepSucPerc = (double) totalSweepSuc/totalSweepAtt;
            }
            if (totalSubAtt !=0) {
                subSucPerc = (double) totalSubSuc/totalSubAtt;
            }
            if (trainLen !=0) {
                avgMatchTime = (double) totalMatchTime/trainLen;
            }


            //Calculate tournament averages
            if (trainLen != 0) {
                avgTdScored = (double) totalTdSuc/trainLen;
                avgPassesScored = (double) totalPassSuc/trainLen;
                avgSweepsScored = (double) totalSweepSuc/trainLen;
                avgSubsScored = (double) totalSubSuc/trainLen;

                avgTdAtt = (double) totalTdAtt/trainLen;
                avgPassesAtt = (double) totalPassAtt/trainLen;
                avgSweepsAtt = (double) totalSweepAtt/trainLen;
                avgSubsAtt = (double)totalSubAtt/trainLen;
            }
        }
        cursor.close();
        return trains;
    }

    public boolean removeFromTrains(Train train) {
        String where = trainingDBOpenHelper.COLUMN_ID_TRAIN + "=" + train.getId(); //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_TRAIN, where, null);
        return (result==1);

    }

    public int getTotalPts() {
        return totalPts;
    }

    public int getTotalPtsAllowed() {
        return totalPtsAllowed;
    }

    public int getTotalPassAtt() {
        return totalPassAtt;
    }

    public int getTotalPassSuc() {
        return totalPassSuc;
    }

    public int getTotalSweepAtt() {
        return totalSweepAtt;
    }

    public int getTotalSweepSuc() {
        return totalSweepSuc;
    }

    public int getTotalTdAtt() {
        return totalTdAtt;
    }

    public int getTotalTdSuc() {
        return totalTdSuc;
    }

    public int getTotalSubAtt() {
        return totalSubAtt;
    }

    public int getTotalSubSuc() {
        return totalSubSuc;
    }

    public double getTdSucPerc() {
        return tdSucPerc;
    }

    public double getPassSucPerc() {
        return passSucPerc;
    }

    public double getSweepSucPerc() {
        return sweepSucPerc;
    }

    public double getSubSucPerc() {
        return subSucPerc;
    }

    public double getAvgMatchTime() {
        return avgMatchTime;
    }

    public int getTrainLen() {
        return trainLen;
    }

    public double getAvgTdScored() {return avgTdScored;}

    public double getAvgPassesScored() {return avgPassesScored;}

    public double getAvgSweepsScored() {return avgSweepsScored;}

    public double getAvgSubsScored() {return avgSubsScored;}

    public double getAvgTdAtt() {return avgTdAtt;}

    public double getAvgPassesAtt() {return avgPassesAtt;}

    public double getAvgSweepsAtt() {return avgSweepsAtt;}

    public double getAvgSubsAtt() {return avgSubsAtt;}

}
