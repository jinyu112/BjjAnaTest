package com.analytics.bjj.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import com.analytics.bjj.Weight;

public class WeightDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    private static long weightLen;
    private static double minWeightDB = 0.0;
    private static double maxWeightDB = 0.0;
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID_WEIGHT,
            trainingDBOpenHelper.COLUMN_WEIGHT_MASS,
            trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,
            trainingDBOpenHelper.COLUMN_WEIGHT_DAY,
            trainingDBOpenHelper.COLUMN_WEIGHT_YEAR,
            trainingDBOpenHelper.COLUMN_WEIGHT_DATE};
    
    public WeightDataSource(Context context) {
        dbhelper = new trainingDBOpenHelper(context);
        database = dbhelper.getWritableDatabase(); //calls oncreate method, creates table structure and allows for database connection
    }
    
    public void open() {
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        dbhelper.close();
    }
    
    public Weight create(Weight weight) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MASS,weight.getMass());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,weight.getMonth());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DAY, weight.getDay());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR, weight.getYear());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DATE, weight.getDate());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_WEIGHT, null, values);
        weight.setId(insertId);
        return weight;
    }
    
    public List<Weight> findAll() {
        boolean firstPass=true;
        minWeightDB = 0.0;
        maxWeightDB = 0.0;
        List<Weight> weights = new ArrayList<Weight>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_WEIGHT, allColumns, null, null, null, null,
                trainingDBOpenHelper.COLUMN_WEIGHT_DATE + " ASC");
        weightLen = cursor.getCount();
        if (weightLen > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                Weight weight = new Weight();
                weight.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_WEIGHT)));
                weight.setMass(cursor.getDouble(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_MASS)));
                weight.setMonth(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH)));
                weight.setDay(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_DAY)));
                weight.setYear(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR)));
                weight.setDate(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_WEIGHT_DATE)));
                weights.add(weight);
                if (firstPass) {
                    minWeightDB=weight.getMass();
                    maxWeightDB=weight.getMass();
                    firstPass=false;
                }
                else {
                    if (weight.getMass() < minWeightDB) {
                        minWeightDB = weight.getMass(); //determine the minimum weight for y axis range
                    }

                    if (weight.getMass() > maxWeightDB) {
                        maxWeightDB = weight.getMass(); //determine the max weight for y axis range
                    }
                }
            }
        }
        cursor.close();
        return weights;
    }


    public boolean removeFromWeights(Weight weight) {
        String where = trainingDBOpenHelper.COLUMN_ID_WEIGHT + "=" + weight.getId(); //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_WEIGHT, where, null);
        return (result==1);
    }

    public void removeAllWeights() {
        database.execSQL("delete from " + trainingDBOpenHelper.TABLE_WEIGHT);
        weightLen=0;
    }

    //this function serves to delete the latest OR earliest date in the database if the number of data points gets too large
    public boolean removeBoundaryDate(int inputDate) { //inputDate is the current date the user is adding
        int result = -1;
        int minDate = 19000101;
        int maxDate = minDate;
        int dateToRemove = minDate;

        //check if inputDate exists
        if (!determineIfDateExistsInDB(inputDate)) { //if input date does NOT exits in database, remove a boundary date
            //determine the minimum date
            Cursor c = database.query(trainingDBOpenHelper.TABLE_WEIGHT, new String[]{"min(" + trainingDBOpenHelper.COLUMN_WEIGHT_DATE + ")"}, null, null,
                    null, null, null);
            if (c.getCount() > 0 && c != null) {
                c.moveToFirst();
                minDate = c.getInt(0);
            }

            //determine the max date (latest date)
            c = database.query(trainingDBOpenHelper.TABLE_WEIGHT, new String[]{"max(" + trainingDBOpenHelper.COLUMN_WEIGHT_DATE + ")"}, null, null,
                    null, null, null);
            if (c.getCount() > 0 && c != null) {
                c.moveToFirst();
                maxDate = c.getInt(0);
            }

            //determine which boundary date to remove
            if (inputDate < minDate) {
                dateToRemove = maxDate;
            } else dateToRemove = minDate;

            //Actually remove the date from the database
            if (removeFromWeightsByDate(dateToRemove)) {
                result = 1;
            } else result = -1;
            c.close();
        }
        return (result == 1);
    }

    public boolean removeFromWeightsByDate(int in) {
        String where = trainingDBOpenHelper.COLUMN_WEIGHT_DATE + "=" + in; //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_WEIGHT, where, null);
        return (result==1);
    }

    public void update(Weight weight) {
        ContentValues values = new ContentValues();
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MASS,weight.getMass());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_MONTH,weight.getMonth());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DAY, weight.getDay());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_YEAR, weight.getYear());
        values.put(trainingDBOpenHelper.COLUMN_WEIGHT_DATE, weight.getDate());
        database.update(trainingDBOpenHelper.TABLE_WEIGHT,values,trainingDBOpenHelper.COLUMN_ID_WEIGHT + "=" + weight.getId(),null);
    }

    //Check to see if date already exists in the database, returns true if date already exists
    public boolean determineIfDateExistsInDB(int inputDate) {
        int count = -1;
        Cursor c = null;

        String query = "SELECT COUNT(*) FROM "
                + trainingDBOpenHelper.TABLE_WEIGHT + " WHERE " + trainingDBOpenHelper.COLUMN_WEIGHT_DATE + " = ?";
        c = database.rawQuery(query, new String[]{Integer.toString(inputDate)});
        if (c.getCount() > 0 && c != null) {
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            c.close();
        }
        return count > 0;
    }

    public long getWeightLen() {return this.weightLen;}
    public double getMinWeightDB() {return this.minWeightDB;}
    public double getMaxWeightDB() {return this.maxWeightDB;}
}
