package example.org.bjjanatest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import example.org.bjjanatest.Tech;

public class TechDataSource {
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;
    private static int techLen = 0;
    private static final String LOGTAG = "BJJTRAINING";
    private static final String[] allColumns = {
            trainingDBOpenHelper.COLUMN_ID_TECH,
            trainingDBOpenHelper.COLUMN_TECH_NAME,
            trainingDBOpenHelper.COLUMN_TECH_TYPE,
            trainingDBOpenHelper.COLUMN_TECH_URL,
            trainingDBOpenHelper.COLUMN_TECH_NOTE};

    public TechDataSource (Context context) {
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

    public Tech create(Tech tech) {
        ContentValues values = new ContentValues();
        //values.put(trainingDBOpenHelper.COLUMN_ID,tech.getId()); // if the id is set to autoincrement from the db generation string, remove this line
        values.put(trainingDBOpenHelper.COLUMN_TECH_NAME,tech.getTechName());
        values.put(trainingDBOpenHelper.COLUMN_TECH_TYPE,tech.getTechType());
        values.put(trainingDBOpenHelper.COLUMN_TECH_URL,tech.getTechVidURL());
        values.put(trainingDBOpenHelper.COLUMN_TECH_NOTE,tech.getTechNote());
        long insertId = database.insert(trainingDBOpenHelper.TABLE_TECH, null, values);
        Log.i(LOGTAG,"tech ID: " + insertId);
        tech.setId(insertId);
        return tech;
    }

    public List<Tech> findAll() {
        List<Tech> techs = new ArrayList<Tech>();
        Cursor cursor = database.query(trainingDBOpenHelper.TABLE_TECH, allColumns, null, null, null, null, null);
        Log.i(LOGTAG, "returned " + cursor.getCount() + " rows.");
        techLen = cursor.getCount();
        if (techLen > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                Tech tech = new Tech();
                tech.setId(cursor.getLong(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_ID_TECH)));
                tech.setTechName(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TECH_NAME)));
                tech.setTechType(cursor.getInt(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TECH_TYPE)));
                tech.setTechNote(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TECH_NOTE)));
                tech.setTechVidURL(cursor.getString(cursor.getColumnIndex(trainingDBOpenHelper.COLUMN_TECH_URL)));
                techs.add(tech);
            }
        }
        return techs;
    }

    public boolean removeFromTechs(Tech tech) {
        String where = trainingDBOpenHelper.COLUMN_ID_TECH + "=" + tech.getId(); //this string must be very carefully crafted or all data can be deleted
        int result = database.delete(trainingDBOpenHelper.TABLE_TECH, where, null);
        return (result==1);
    }

    public void update(Tech tech) {
        ContentValues values = new ContentValues();
        //values.put(trainingDBOpenHelper.COLUMN_ID,tech.getId());
        values.put(trainingDBOpenHelper.COLUMN_TECH_NAME,tech.getTechName());
        values.put(trainingDBOpenHelper.COLUMN_TECH_TYPE,tech.getTechType());
        values.put(trainingDBOpenHelper.COLUMN_TECH_URL,tech.getTechVidURL());
        values.put(trainingDBOpenHelper.COLUMN_TECH_NOTE,tech.getTechNote());
        database.update(trainingDBOpenHelper.TABLE_TECH,values,trainingDBOpenHelper.COLUMN_ID_TECH + "=" + tech.getId(),null);

    }

}
