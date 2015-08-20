package example.org.bjjanatest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.org.bjjanatest.Weight;

public class WeightDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    private long weightLen;
    private int minDate = 19000101;
    private String minDateStr="19000101";
    private int maxDate = 19000101;
    private String maxDateStr="19000101";
    private int minDateIndex = 0;
    private static final String LOGTAG = "BJJTRAINING";
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID_WEIGHT,
            trainingDBOpenHelper.COLUMN_WEIGHT_MASS,
            trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,
            trainingDBOpenHelper.COLUMN_WEIGHT_DAY,
            trainingDBOpenHelper.COLUMN_WEIGHT_YEAR,
            trainingDBOpenHelper.COLUMN_WEIGHT_DATESTRING};
    
    public WeightDataSource(Context context) {
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
    
    public Weight create(Weight weight) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MASS,weight.getMass());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,weight.getMonth());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DAY, weight.getDay());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR, weight.getYear());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DATESTRING, weight.getDateStr());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_WEIGHT, null, values);
        Log.i(LOGTAG,"weight ID: " + insertId);
        weight.setId(insertId);
        return weight;
    }
    
    public List<Weight> findAll() {
        List<Weight> weights = new ArrayList<Weight>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_WEIGHT, allColumns, null, null, null, null,
                trainingDBOpenHelper.COLUMN_WEIGHT_DATESTRING + " ASC");
        Log.i(LOGTAG, "returned " + cursor.getCount() + " rows from weight db.");
        weightLen = cursor.getCount();
        boolean firstPass = true;
        int i=0;
        if (weightLen > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                Weight weight = new Weight();
                weight.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_WEIGHT)));
                weight.setMass(cursor.getDouble(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_MASS)));
                weight.setMonth(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH)));
                weight.setDay(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_DAY)));
                weight.setYear(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR)));
                weight.setDateStr(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_DATESTRING)));
                weights.add(weight);
                Log.i(LOGTAG, "weight mass: " + weight.getMass());

                //Determine the earliest date in the database
                if (firstPass) {
                    minDate = Integer.parseInt(weight.getDateStr());
                    maxDate = Integer.parseInt(weight.getDateStr());
                    firstPass = false;
                }
                else{
                    determineMinDate(Integer.parseInt(weight.getDateStr()),i);
                    determineMaxDate(Integer.parseInt(weight.getDateStr()));
                }
                minDateStr=Integer.toString(minDate);
                maxDateStr=Integer.toString(maxDate);
                i++;
            }
        }
        return weights;
        
    }

    public boolean removeFromWeights(Weight weight) {
        String where = trainingDBOpenHelper.COLUMN_ID_WEIGHT + "=" + weight.getId(); //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_WEIGHT, where, null);
        return (result==1);
    }

    public void update(Weight weight) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MASS,weight.getMass());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,weight.getMonth());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DAY, weight.getDay());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR, weight.getYear());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DATESTRING, weight.getDateStr());
        database.update(trainingDBOpenHelper.TABLE_WEIGHT,values,trainingDBOpenHelper.COLUMN_ID_WEIGHT + "=" + weight.getId(),null);
    }

    public int determineMinDate(int currDate, int index) {
        if (currDate < minDate) {
            minDate = currDate;
            minDateIndex=index;
        }
        return minDate;
    }

    public int determineMaxDate(int currDate) {
        if (currDate > maxDate) {
            maxDate = currDate;
        }
        return maxDate;
    }

    public String getMinDateStr() { return this.minDateStr;}
    public String getMaxDateStr() { return this.maxDateStr;}
    public int getMinDateIndex() { return this.minDateIndex;}
}
