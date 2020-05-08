package com.example.sqliteexample;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "LeakFixDB";
    private static final String TABLE_Networks = "network_details";
    private static final String KEY_ID = "id";
    private static final String KEY_SSID = "ssid";
    private static final String KEY_BSSID = "bssid";
    private static final String KEY_FAKE = "is_fake";
    private static final String KEY_ACTIVE = "active";
    private static final String KEY_PREV = "previous";
    public DbHandler(Context context){
        super(context,DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Networks + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SSID + " TEXT,"
                + KEY_BSSID + " TEXT,"
                + KEY_FAKE + " INTEGER,"
                + KEY_ACTIVE + " INTEGER,"
                + KEY_PREV + " INTEGER"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Networks);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new Network Details
    void insertNetworkDetails(String ssid, String bssid, int is_fake, int active, int previous){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //fix to delete DB when making mistakes
        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_Networks);
        db.close();*/

        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_SSID, ssid);
        cValues.put(KEY_BSSID, bssid);
        cValues.put(KEY_FAKE, is_fake);
        cValues.put(KEY_ACTIVE, active);
        cValues.put(KEY_PREV, previous);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Networks,null, cValues);
        db.close();
    }
    // Get Network Details
    public ArrayList<HashMap<String, String>> GetNetworks(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> netList = new ArrayList<>();
        String query = "SELECT ssid, bssid, is_fake, active, previous FROM "+ TABLE_Networks;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> net = new HashMap<>();
            net.put("ssid",cursor.getString(cursor.getColumnIndex(KEY_SSID)));
            net.put("bssid",cursor.getString(cursor.getColumnIndex(KEY_BSSID)));
            net.put("is_fake",cursor.getString(cursor.getColumnIndex(KEY_FAKE)));
            net.put("active",cursor.getString(cursor.getColumnIndex(KEY_ACTIVE)));
            net.put("previous",cursor.getString(cursor.getColumnIndex(KEY_PREV)));
            netList.add(net);
        }
        return  netList;
    }
    // Get Network Details based on Netkid
    public ArrayList<HashMap<String, String>> GetetworkByNetId(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> netList = new ArrayList<>();
        String query = "SELECT ssid, bssid, is_fake, active, previous FROM "+ TABLE_Networks;
        Cursor cursor = db.query(TABLE_Networks, new String[]{KEY_SSID, KEY_BSSID, KEY_FAKE, KEY_ACTIVE, KEY_PREV}, KEY_ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> net = new HashMap<>();
            net.put("ssid",cursor.getString(cursor.getColumnIndex(KEY_SSID)));
            net.put("bssid",cursor.getString(cursor.getColumnIndex(KEY_BSSID)));
            net.put("is_fake",cursor.getString(cursor.getColumnIndex(KEY_FAKE)));
            net.put("active",cursor.getString(cursor.getColumnIndex(KEY_ACTIVE)));
            net.put("previous",cursor.getString(cursor.getColumnIndex(KEY_PREV)));
            netList.add(net);
        }
        return  netList;
    }
    // Delete Network Details
    public void DeleteNetwork(int netId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Networks, KEY_ID+" = ?",new String[]{String.valueOf(netId)});
        db.close();
    }
    // Update Network Details
    public int UpdateNetworkDetails(String ssid, String bssid, int is_fake, int active, int previous, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(KEY_SSID, ssid);
        cVals.put(KEY_BSSID, bssid);
        cVals.put(KEY_FAKE, is_fake);
        cVals.put(KEY_ACTIVE, active);
        cVals.put(KEY_PREV, previous);
        int count = db.update(TABLE_Networks, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}
