package example.org.bjjanatest.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class trainingDBOpenHelper extends SQLiteOpenHelper{

    private static final String LOGTAG = "BJJTRAINING";
    private static final String DATABASE_NAME = "training_database.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TOURN            = "tourn";
    public static final String COLUMN_ID                = "tournId";
    public static final String COLUMN_WEIGHT                = "weightClass";
    public static final String COLUMN_TOURN_NAME             = "tournName";
    public static final String COLUMN_BELT              = "belt";
    public static final String COLUMN_DATE              = "date";
    public static final String COLUMN_PTS_ALLOWED           = "pointsAllowed";
    public static final String COLUMN_PTS_SCORED        = "pointsScored";
    public static final String COLUMN_SUB_ATTEMPT        = "subAttempted";
    public static final String COLUMN_SUB_SUCCESS       = "subSuccess";
    public static final String COLUMN_PASS_ATTEMPTED    = "passAttempted";
    public static final String COLUMN_PASS_SUCCESS      = "passSuccess";
    public static final String COLUMN_SWEEP_ATTEMPTED    = "sweepAttempted";
    public static final String COLUMN_SWEEP_SUCCESS         = "sweepSuccess";
    public static final String COLUMN_TD_ATTEMPTED      = "tdAttempted";
    public static final String COLUMN_TD_SUCCESS        = "tdSuccess";
    public static final String COLUMN_BACK_TAKES        = "numBackTakes";
    public static final String COLUMN_MOUNTS           = "numMounts";
    public static final String COLUMN_MATCH_TIME        = "matchTime";
    public static final String COLUMN_WIN               = "win";


    public static final String TABLE_TRAIN            = "train";
    public static final String COLUMN_ID_TRAIN                = "trainId";
    public static final String COLUMN_TRAIN_NAME             = "trainName";
    public static final String COLUMN_BELT_TRAIN              = "belt";
    public static final String COLUMN_BELT_TRAIN_OPP              = "oppBelt";
    public static final String COLUMN_PTS_ALLOWED_TRAIN           = "pointsAllowed";
    public static final String COLUMN_PTS_SCORED_TRAIN        = "pointsScored";
    public static final String COLUMN_SUB_ATTEMPT_TRAIN        = "subAttempted";
    public static final String COLUMN_SUB_SUCCESS_TRAIN       = "subSuccess";
    public static final String COLUMN_PASS_ATTEMPTED_TRAIN    = "passAttempted";
    public static final String COLUMN_PASS_SUCCESS_TRAIN      = "passSuccess";
    public static final String COLUMN_SWEEP_ATTEMPTED_TRAIN    = "sweepAttempted";
    public static final String COLUMN_SWEEP_SUCCESS_TRAIN         = "sweepSuccess";
    public static final String COLUMN_TD_ATTEMPTED_TRAIN      = "tdAttempted";
    public static final String COLUMN_TD_SUCCESS_TRAIN        = "tdSuccess";
    public static final String COLUMN_BACK_TAKES_TRAIN        = "numBackTakes";
    public static final String COLUMN_MOUNTS_TRAIN           = "numMounts";
    public static final String COLUMN_MATCH_TIME_TRAIN        = "matchTime";


    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_TOURN + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_WEIGHT + " NUMERIC, " +
                    COLUMN_TOURN_NAME + " TEXT NOT NULL, " +
                    COLUMN_BELT + " TEXT NOT NULL, " +
                    COLUMN_DATE + " NUMERIC, " +
                    COLUMN_PTS_ALLOWED + " NUMERIC, " +
                    COLUMN_PTS_SCORED + " NUMERIC, " +
                    COLUMN_SUB_ATTEMPT + " NUMERIC, " +
                    COLUMN_SUB_SUCCESS + " NUMERIC, " +
                    COLUMN_PASS_ATTEMPTED + " NUMERIC, " +
                    COLUMN_PASS_SUCCESS + " NUMERIC, " +
                    COLUMN_SWEEP_ATTEMPTED + " NUMERIC, " +
                    COLUMN_SWEEP_SUCCESS + " NUMERIC, " +
                    COLUMN_TD_ATTEMPTED + " NUMERIC, " +
                    COLUMN_TD_SUCCESS + " NUMERIC, " +
                    COLUMN_BACK_TAKES + " NUMERIC, " +
                    COLUMN_MOUNTS + " NUMERIC, " +
                    COLUMN_MATCH_TIME + " NUMERIC, " +
                    COLUMN_WIN + " NUMERIC " +
                    ")"; //this string is VERY important. the individual strings must have exactly one space in between them in the sql statement

    private static final String TABLE_CREATE_TRAIN =
            "CREATE TABLE " + TABLE_TRAIN + " (" +
                    COLUMN_ID_TRAIN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TRAIN_NAME + " TEXT NOT NULL, " +
                    COLUMN_BELT_TRAIN + " TEXT NOT NULL, " +
                    COLUMN_BELT_TRAIN_OPP + " TEXT NOT NULL, " +
                    COLUMN_PTS_ALLOWED_TRAIN + " NUMERIC, " +
                    COLUMN_PTS_SCORED_TRAIN + " NUMERIC, " +
                    COLUMN_SUB_ATTEMPT_TRAIN + " NUMERIC, " +
                    COLUMN_SUB_SUCCESS_TRAIN + " NUMERIC, " +
                    COLUMN_PASS_ATTEMPTED_TRAIN + " NUMERIC, " +
                    COLUMN_PASS_SUCCESS_TRAIN + " NUMERIC, " +
                    COLUMN_SWEEP_ATTEMPTED_TRAIN + " NUMERIC, " +
                    COLUMN_SWEEP_SUCCESS_TRAIN + " NUMERIC, " +
                    COLUMN_TD_ATTEMPTED_TRAIN + " NUMERIC, " +
                    COLUMN_TD_SUCCESS_TRAIN + " NUMERIC, " +
                    COLUMN_BACK_TAKES_TRAIN + " NUMERIC, " +
                    COLUMN_MOUNTS_TRAIN + " NUMERIC, " +
                    COLUMN_MATCH_TIME_TRAIN + " NUMERIC " +
                    ")"; //this string is VERY important. the individual strings must have exactly one space in between them in the sql statement


    public trainingDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //if database doesn't exist, this method is called by the sdk ONLY
        db.execSQL(TABLE_CREATE);
        Log.i(LOGTAG, "Table tourn has been created.");
        db.execSQL(TABLE_CREATE_TRAIN);
        Log.i(LOGTAG, "Table train has been created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOURN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAIN);
        onCreate(db);
    }
}
