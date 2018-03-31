package com.example.hp.probattletask.LDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static String name = "myDb.db";
    static int version = 1;

    public static final String TABLE_NAME_FAVOURITE = "favorite";
    public static final String TABLE_NAME = "krypto";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_RANK = "rank";
    public static final String COLUMN_PRICE_USD = "price_usd";
    public static final String COLUMN_PRICE_BTC = "price_btc";
    public static final String COLUMN_24H_VOl = "_24h_vol";
    public static final String COLUMN_MARKET_CAP = "market_cap";
    public static final String COLUMN_AVAILABLE_SUPPLY = "available_supply";
    public static final String COLUMN_TOTAL_SUPPLY = "total_supply";
    public static final String COLUMN_PERCENT_CHANGE_1H = "change_1h";
    public static final String COLUMN_PERCENT_CHANGE_24H = "change_24h";
    public static final String COLUMN_PERCENT_CHANGE_7D = "change_7d";
    public static final String COLUMN_MAX_SUPPLY = "max_supply";
    public static final String COLUMN_LAST_UPDATED = "last_updated";
    public static final String COLUMN_IS_FAV = "is_fav";


    private static final String TABLE_CREATE_USER = "Create table " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " text," + COLUMN_SYMBOL + " text," + COLUMN_RANK + " text," + COLUMN_PRICE_USD + " text," + "" +
            COLUMN_PRICE_BTC + " text," + COLUMN_24H_VOl + " text," + COLUMN_AVAILABLE_SUPPLY + " text," +
            COLUMN_TOTAL_SUPPLY + " text," + COLUMN_PERCENT_CHANGE_1H + " text," + COLUMN_PERCENT_CHANGE_24H + " text," +
            COLUMN_PERCENT_CHANGE_7D + " text," + COLUMN_MAX_SUPPLY + " text," + COLUMN_MARKET_CAP + " text," +
            COLUMN_LAST_UPDATED + " text," + COLUMN_IS_FAV + " text" + " )";


    private static final String TABLE_CREATE_FAVOURITE = "Create table " + TABLE_NAME_FAVOURITE +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " text," + COLUMN_SYMBOL + " text," + COLUMN_RANK + " text," + COLUMN_PRICE_USD + " text," + "" +
            COLUMN_PRICE_BTC + " text," + COLUMN_24H_VOl + " text," + COLUMN_AVAILABLE_SUPPLY + " text," +
            COLUMN_TOTAL_SUPPLY + " text," + COLUMN_PERCENT_CHANGE_1H + " text," + COLUMN_PERCENT_CHANGE_24H + " text," +
            COLUMN_PERCENT_CHANGE_7D + " text," + COLUMN_MAX_SUPPLY + " text," + COLUMN_MARKET_CAP + " text," +
            COLUMN_LAST_UPDATED + " text," + COLUMN_IS_FAV + " text" + " )";


    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE_USER);
        sqLiteDatabase.execSQL(TABLE_CREATE_FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
