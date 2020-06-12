package com.example.dicodingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.dicodingapp.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDatabase extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DB_NAME = "githubDB";

    private static String TABLE_NAME = "favorite";
    private static String KEY_ID = "id";
    private static String ITEM_TITLE = "title";
    private static String ITEM_IMAGE = "image";
    private static String FAVORITE_STATUS = "status";
    private static final String[] COLUMNS = {KEY_ID, ITEM_TITLE, ITEM_IMAGE, FAVORITE_STATUS};


    private static String QUERY_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " INT PRIMARY KEY, "
                    + ITEM_TITLE + " TEXT, "
                    + ITEM_IMAGE + " TEXT, "
                    + FAVORITE_STATUS + " TEXT"
                    + ")";

    public FavoriteDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addFavorite(Favorite favorite){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, favorite.getKey_id());
        contentValues.put(ITEM_TITLE, favorite.getTitle());
        contentValues.put(ITEM_IMAGE, favorite.getImage());
        contentValues.put(FAVORITE_STATUS, favorite.getStatus());

        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public List<Favorite> allFavorites(){
        List<Favorite> favorites = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Favorite favorite = new Favorite();
                favorite.setKey_id(cursor.getInt(0));
                favorite.setTitle(cursor.getString(1));
                favorite.setImage(cursor.getString(2));
                favorite.setStatus(cursor.getString(3));
                favorites.add(favorite);
            } while (cursor.moveToNext());

        }
        database.close();
        return favorites;
    }


    public void deleteData(Favorite favorite){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ID + " = ?" , new String[] {String.valueOf(favorite.getKey_id())});
        database.close();
    }

}
