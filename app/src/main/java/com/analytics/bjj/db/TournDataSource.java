package com.analytics.bjj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.analytics.bjj.Tourn;

public class TournDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    private static int tournLen = 0;
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
    private static int totalWins = 0;

    private static final String LOGTAG = "BJJTRAINING";
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID,
            trainingDBOpenHelper.COLUMN_WEIGHT,
            trainingDBOpenHelper.COLUMN_TOURN_NAME,
            trainingDBOpenHelper.COLUMN_BELT,
            trainingDBOpenHelper.COLUMN_DATE,
            trainingDBOpenHelper.COLUMN_PTS_ALLOWED,
            trainingDBOpenHelper.COLUMN_PTS_SCORED,
            trainingDBOpenHelper.COLUMN_SUB_ATTEMPT,
            trainingDBOpenHelper.COLUMN_SUB_SUCCESS,
            trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED,
            trainingDBOpenHelper.COLUMN_PASS_SUCCESS,
            trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED,
            trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS,
            trainingDBOpenHelper.COLUMN_TD_ATTEMPTED,
            trainingDBOpenHelper.COLUMN_TD_SUCCESS,
            trainingDBOpenHelper.COLUMN_MATCH_TIME,
            trainingDBOpenHelper.COLUMN_WIN};

    public TournDataSource (Context context) {
        dbhelper = new trainingDBOpenHelper(context);
        database = dbhelper.getWritableDatabase(); //calls oncreate method, creates table structure and allows for database connection
    }

    public void open() {
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        dbhelper.close();
    }

    public Tourn create(Tourn tourn) {
        ContentValues values = new ContentValues();
        //values.put(trainingDBOpenHelper.COLUMN_ID,tourn.getId()); // if the id is set to autoincrement from the db generation string, remove this line
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT,tourn.getWeightClass());
        values.put(trainingDBOpenHelper.COLUMN_TOURN_NAME,tourn.getTournName());
        values.put(trainingDBOpenHelper.COLUMN_BELT,tourn.getBelt());
        values.put(trainingDBOpenHelper.COLUMN_DATE,tourn.getDate());
        values.put(trainingDBOpenHelper.COLUMN_PTS_ALLOWED,tourn.getPointsAllowed());
        values.put(trainingDBOpenHelper.COLUMN_PTS_SCORED,tourn.getPointsScored());
        values.put(trainingDBOpenHelper.COLUMN_SUB_ATTEMPT,tourn.getSubAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SUB_SUCCESS,tourn.getSubSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED,tourn.getPassAttempted());
        values.put(trainingDBOpenHelper.COLUMN_PASS_SUCCESS,tourn.getPassSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED,tourn.getSweepAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS,tourn.getSweepSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_TD_ATTEMPTED,tourn.getTdAttempted());
        values.put(trainingDBOpenHelper.COLUMN_TD_SUCCESS,tourn.getTdSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_MATCH_TIME,tourn.getMatchTime());
        values.put(trainingDBOpenHelper.COLUMN_WIN,tourn.getWin());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_TOURN,null,values);
        tourn.setId(insertId);
        return tourn;
    }

    public List<Tourn> findAll() {
        List<Tourn> tourns = new ArrayList<Tourn>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_TOURN,allColumns,null,null,null,null,null);
        tournLen = cursor.getCount();
        if (tournLen>0 && cursor != null) {
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
            totalWins = 0;
            while (cursor.moveToNext()) {
                Tourn tourn = new Tourn();
                tourn.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID)));
                tourn.setTournName(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TOURN_NAME)));
                tourn.setMatchTime(cursor.getDouble(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_MATCH_TIME)));
                tourn.setWeightClass(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT)));
                tourn.setBelt(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_BELT)));
                tourn.setDate(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_DATE)));
                tourn.setPointsAllowed(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PTS_ALLOWED)));
                tourn.setPointsScored(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PTS_SCORED)));
                tourn.setSubAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SUB_ATTEMPT)));
                tourn.setSubSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SUB_SUCCESS)));
                tourn.setPassAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED)));
                tourn.setPassSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_PASS_SUCCESS)));
                tourn.setSweepAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED)));
                tourn.setSweepSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS)));
                tourn.setTdAttempted(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TD_ATTEMPTED)));
                tourn.setTdSuccessful(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TD_SUCCESS)));
                tourn.setWin(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WIN)));


                totalPts        = totalPts + tourn.getPointsScored();
                totalPtsAllowed = totalPtsAllowed + tourn.getPointsAllowed();
                totalPassAtt    = totalPassAtt + tourn.getPassAttempted();
                totalPassSuc    = totalPassSuc + tourn.getPassSuccessful();
                totalSweepAtt   = totalSweepAtt + tourn.getSweepAttempted();
                totalSweepSuc   = totalSweepSuc + tourn.getSweepSuccessful();
                totalTdAtt      = totalTdAtt + tourn.getTdAttempted();
                totalTdSuc      = totalTdSuc + tourn.getTdSuccessful();
                totalSubAtt     = totalSubAtt + tourn.getSubAttempted();
                totalSubSuc     = totalSubSuc + tourn.getSubSuccessful();
                totalMatchTime  = totalMatchTime + tourn.getMatchTime();
                totalWins       = totalWins + tourn.getWin();

                tourns.add(tourn);
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
            if (tournLen !=0) {
                avgMatchTime = (double) totalMatchTime/tournLen;
            }


            //Calculate tournament averages
            if (tournLen != 0) {
                avgTdScored = (double) totalTdSuc/tournLen;
                avgPassesScored = (double) totalPassSuc/tournLen;
                avgSweepsScored = (double) totalSweepSuc/tournLen;
                avgSubsScored = (double) totalSubSuc/tournLen;

                avgTdAtt = (double) totalTdAtt/tournLen;
                avgPassesAtt = (double) totalPassAtt/tournLen;
                avgSweepsAtt = (double) totalSweepAtt/tournLen;
                avgSubsAtt = (double)totalSubAtt/tournLen;
            }
        }
        cursor.close();
        return tourns;
    }

public boolean removeFromTourns(Tourn tourn) {
    String where = trainingDBOpenHelper.COLUMN_ID + "=" + tourn.getId(); //this string must be very carefully crafted or all data can be deleted
    int result = database.delete(trainingDBOpenHelper.TABLE_TOURN, where, null);
    return (result==1);
}


    public void removeAllTourns() {
        database.execSQL("delete from " + trainingDBOpenHelper.TABLE_TOURN);
    }

    public void update(Tourn tourn) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT,tourn.getWeightClass());
        values.put(trainingDBOpenHelper.COLUMN_TOURN_NAME,tourn.getTournName());
        values.put(trainingDBOpenHelper.COLUMN_BELT,tourn.getBelt());
        values.put(trainingDBOpenHelper.COLUMN_DATE,tourn.getDate());
        values.put(trainingDBOpenHelper.COLUMN_PTS_ALLOWED,tourn.getPointsAllowed());
        values.put(trainingDBOpenHelper.COLUMN_PTS_SCORED,tourn.getPointsScored());
        values.put(trainingDBOpenHelper.COLUMN_SUB_ATTEMPT,tourn.getSubAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SUB_SUCCESS,tourn.getSubSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_PASS_ATTEMPTED,tourn.getPassAttempted());
        values.put(trainingDBOpenHelper.COLUMN_PASS_SUCCESS,tourn.getPassSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_ATTEMPTED,tourn.getSweepAttempted());
        values.put(trainingDBOpenHelper.COLUMN_SWEEP_SUCCESS,tourn.getSweepSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_TD_ATTEMPTED,tourn.getTdAttempted());
        values.put(trainingDBOpenHelper.COLUMN_TD_SUCCESS,tourn.getTdSuccessful());
        values.put(trainingDBOpenHelper.COLUMN_MATCH_TIME,tourn.getMatchTime());
        values.put(trainingDBOpenHelper.COLUMN_WIN,tourn.getWin());
        database.update(trainingDBOpenHelper.TABLE_TOURN,values,trainingDBOpenHelper.COLUMN_ID + "=" + tourn.getId(),null);

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

    public int getTournLen() {
        return tournLen;
    }

    public double getAvgTdScored() {return avgTdScored;}

    public double getAvgPassesScored() {return avgPassesScored;}

    public double getAvgSweepsScored() {return avgSweepsScored;}

    public double getAvgSubsScored() {return avgSubsScored;}

    public double getAvgTdAtt() {return avgTdAtt;}

    public double getAvgPassesAtt() {return avgPassesAtt;}

    public double getAvgSweepsAtt() {return avgSweepsAtt;}

    public double getAvgSubsAtt() {return avgSubsAtt;}

    public int getWins() {return totalWins;}
}
