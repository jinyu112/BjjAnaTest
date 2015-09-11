package example.org.bjjanatest.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.org.bjjanatest.Drill;

public class DrillDataSource {
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbhelper;
    private static final String LOGTAG = "BJJTRAINING";
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID_DRILL,
            trainingDBOpenHelper.COLUMN_DRILL_NAME,
            trainingDBOpenHelper.COLUMN_DRILL_TOTAL_REPS};

    private static int drillLen = 0;
    private static int maxReps = 0;

    public DrillDataSource (Context context) {
        dbhelper = new trainingDBOpenHelper(context);
        database = dbhelper.getWritableDatabase(); //calls oncreate method, creates table structure and allows for database connection
    }

    public void open() {
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        dbhelper.close();
    }

    public Drill create(Drill drill) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_DRILL_NAME,drill.getDrillName());
        values.put(trainingDBOpenHelper.COLUMN_DRILL_TOTAL_REPS,drill.getDrillRepTotal());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_DRILLS, null, values);
        Log.i(LOGTAG,"drill ID: " + insertId);
        drill.setId(insertId);
        return drill;
    }

    public List<Drill> findAll() {
        List<Drill> drills = new ArrayList<Drill>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_DRILLS, allColumns, null, null, null, null, null);
        drillLen = cursor.getCount();
        maxReps = 0;
        if (drillLen > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                Drill drill = new Drill();
                drill.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_DRILL)));
                drill.setDrillName(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_DRILL_NAME)));
                drill.setDrillRepTotal(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_DRILL_TOTAL_REPS)));
                if (drill.getDrillRepTotal()>maxReps) {
                    maxReps = drill.getDrillRepTotal();
                }
                drills.add(drill);
            }
        }
        cursor.close();
        return drills;
    }

    public boolean removeFromDrills(Drill drill) {
        String where = trainingDBOpenHelper.COLUMN_ID_DRILL + "=" + drill.getId(); //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_DRILLS, where, null);
        return (result==1);
    }

    public void update(Drill drill) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_DRILL_NAME,drill.getDrillName());
        values.put(trainingDBOpenHelper.COLUMN_DRILL_TOTAL_REPS,drill.getDrillRepTotal());
        database.update(trainingDBOpenHelper.TABLE_DRILLS,values,trainingDBOpenHelper.COLUMN_ID_DRILL + "=" + drill.getId(),null);

    }

    public int getMaxReps() {
        return this.maxReps;
    }
}
