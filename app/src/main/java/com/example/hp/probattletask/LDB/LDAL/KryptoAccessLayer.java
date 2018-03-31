package com.example.hp.probattletask.LDB.LDAL;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.hp.probattletask.LDB.DBHelper;
import com.example.hp.probattletask.Wrappers.AllCryptosEnt;
import com.example.hp.probattletask.Wrappers.CryptoList;

import java.util.ArrayList;
import java.util.List;

public class KryptoAccessLayer {
    Context context;

    DBHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public KryptoAccessLayer(Context context) {
        this.context = context;

        helper = new DBHelper(context);
    }

    public void open() {
        sqLiteDatabase = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void markFavourite(CryptoList allCryptosEnt){
            ContentValues contentValues = new ContentValues();
            //contentValues.put(DBHelper.COLUMN_ID, allCryptosEnt.getCryptoList().get(i).getId());
            contentValues.put(DBHelper.COLUMN_NAME, allCryptosEnt.getName());
            contentValues.put(DBHelper.COLUMN_SYMBOL, allCryptosEnt.getSymbol());
            contentValues.put(DBHelper.COLUMN_RANK, allCryptosEnt.getRank());
            contentValues.put(DBHelper.COLUMN_PRICE_USD, allCryptosEnt.getPriceUsd());
            contentValues.put(DBHelper.COLUMN_PRICE_BTC, allCryptosEnt.getPriceBtc());
            contentValues.put(DBHelper.COLUMN_24H_VOl, allCryptosEnt.get24hVolumeUsd());
            contentValues.put(DBHelper.COLUMN_MARKET_CAP, allCryptosEnt.getMarketCapUsd());
            contentValues.put(DBHelper.COLUMN_AVAILABLE_SUPPLY, allCryptosEnt.getAvailableSupply());
            contentValues.put(DBHelper.COLUMN_TOTAL_SUPPLY, allCryptosEnt.getTotalSupply());
            contentValues.put(DBHelper.COLUMN_MAX_SUPPLY, allCryptosEnt.getMaxSupply());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_1H, allCryptosEnt.getPercentChange1h());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_7D, allCryptosEnt.getPercentChange7d());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_24H, allCryptosEnt.getPercentChange24h());
            contentValues.put(DBHelper.COLUMN_LAST_UPDATED, allCryptosEnt.getLastUpdated());
            contentValues.put(DBHelper.COLUMN_IS_FAV, "1");
            long l = sqLiteDatabase.insert(DBHelper.TABLE_NAME_FAVOURITE, null, contentValues);
            Log.d("inserted : " , " L= " + l);

    }

    public void insert(AllCryptosEnt allCryptosEnt) {
        for (int i = 0; i < allCryptosEnt.getCryptoList().size(); i++) {
            ContentValues contentValues = new ContentValues();
            //contentValues.put(DBHelper.COLUMN_ID, allCryptosEnt.getCryptoList().get(i).getId());
            contentValues.put(DBHelper.COLUMN_NAME, allCryptosEnt.getCryptoList().get(i).getName());
            contentValues.put(DBHelper.COLUMN_SYMBOL, allCryptosEnt.getCryptoList().get(i).getSymbol());
            contentValues.put(DBHelper.COLUMN_RANK, allCryptosEnt.getCryptoList().get(i).getRank());
            contentValues.put(DBHelper.COLUMN_PRICE_USD, allCryptosEnt.getCryptoList().get(i).getPriceUsd());
            contentValues.put(DBHelper.COLUMN_PRICE_BTC, allCryptosEnt.getCryptoList().get(i).getPriceBtc());
            contentValues.put(DBHelper.COLUMN_24H_VOl, allCryptosEnt.getCryptoList().get(i).get24hVolumeUsd());
            contentValues.put(DBHelper.COLUMN_MARKET_CAP, allCryptosEnt.getCryptoList().get(i).getMarketCapUsd());
            contentValues.put(DBHelper.COLUMN_AVAILABLE_SUPPLY, allCryptosEnt.getCryptoList().get(i).getAvailableSupply());
            contentValues.put(DBHelper.COLUMN_TOTAL_SUPPLY, allCryptosEnt.getCryptoList().get(i).getTotalSupply());
            contentValues.put(DBHelper.COLUMN_MAX_SUPPLY, allCryptosEnt.getCryptoList().get(i).getMaxSupply());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_1H, allCryptosEnt.getCryptoList().get(i).getPercentChange1h());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_7D, allCryptosEnt.getCryptoList().get(i).getPercentChange7d());
            contentValues.put(DBHelper.COLUMN_PERCENT_CHANGE_24H, allCryptosEnt.getCryptoList().get(i).getPercentChange24h());
            contentValues.put(DBHelper.COLUMN_LAST_UPDATED, allCryptosEnt.getCryptoList().get(i).getLastUpdated());
            contentValues.put(DBHelper.COLUMN_IS_FAV, "0");
            long l = sqLiteDatabase.insert(DBHelper.TABLE_NAME, null, contentValues);
            Log.d("inserted : " , " L= " + l);
        }
    }


    public ArrayList<CryptoList> getAllCryptos() {
        String select_query = "select * from " + DBHelper.TABLE_NAME;
        Cursor cursor = this.sqLiteDatabase.rawQuery(select_query, null);
        ArrayList<CryptoList> cryptoLists = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                cryptoLists.add(SetUpList(cursor));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("RetreiveException", e.getMessage());
            }
        }
        cursor.close();
        return cryptoLists;

    }
    public ArrayList<CryptoList> getFavouriteCryptos() {
        String select_query = "select * from " + DBHelper.TABLE_NAME_FAVOURITE;
        Cursor cursor = this.sqLiteDatabase.rawQuery(select_query, null);
        ArrayList<CryptoList> cryptoLists = new ArrayList<>();
        while (cursor.moveToNext()) {
            try {
                cryptoLists.add(SetUpList(cursor));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("RetreiveException", e.getMessage());
            }
        }
        cursor.close();
        return cryptoLists;

    }
    private CryptoList SetUpList(Cursor cursor) {
        CryptoList cryptoList = new CryptoList();
        cryptoList.setId(cursor.getString(0));
        cryptoList.setName(cursor.getString(1));
        cryptoList.setSymbol(cursor.getString(2));
        cryptoList.setRank(cursor.getString(3));
        cryptoList.setPriceUsd(cursor.getString(4));
        cryptoList.setPriceBtc(cursor.getString(5));
        cryptoList.set24hVolumeUsd(cursor.getString(6));
        cryptoList.setMarketCapUsd(cursor.getString(7));
        cryptoList.setAvailableSupply(cursor.getString(8));
        cryptoList.setTotalSupply(cursor.getString(9));
        cryptoList.setPercentChange1h(cursor.getString(10));
        cryptoList.setPercentChange24h(cursor.getString(11));
        cryptoList.setPercentChange7d(cursor.getString(12));
        cryptoList.setMaxSupply(cursor.getString(13));
        cryptoList.setLastUpdated(cursor.getString(14));
        cryptoList.setIsFavourite(cursor.getString(15));
        return cryptoList;
    }


}
